package unilever.it.org.data_source.network

import android.util.Log
import kotlinx.coroutines.ensureActive
import retrofit2.Response
import unilever.it.org.domain.models.NetworkError
import unilever.it.org.domain.models.Result
import java.nio.channels.UnresolvedAddressException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> Response<T>
): Result<T?, NetworkError> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        return Result.Error(NetworkError.NO_INTERNET)
    }  catch (e: Exception) {
        Log.d("rabbit", "safeCall: ${e.stackTraceToString()}")
        coroutineContext.ensureActive()
        return Result.Error(NetworkError.UNKNOWN)
    }

    return responseToResult(response)
}

inline fun <reified T> responseToResult(
    response: Response<T>
): Result<T?, NetworkError> {
    return when(response.code()) {
        in 200..299 -> {
            try {
                Result.Success(response.body())
            } catch(e: Exception) {
                Result.Error(NetworkError.SERIALIZATION)
            }
        }
        404 -> Result.Error(NetworkError.NOT_FOUND)
        408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
        429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
        else -> Result.Error(NetworkError.UNKNOWN)
    }
}

