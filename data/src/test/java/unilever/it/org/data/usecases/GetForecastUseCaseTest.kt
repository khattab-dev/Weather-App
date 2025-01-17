package unilever.it.org.data.usecases

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import unilever.it.org.domain.models.CurrentWeather
import unilever.it.org.domain.models.Forecast
import unilever.it.org.domain.models.NetworkError
import unilever.it.org.domain.models.Result
import unilever.it.org.domain.usecases.GetCurrentWeatherUseCase
import unilever.it.org.domain.usecases.GetWeatherForecastUseCase
import unilever.it.org.repositories.CurrentWeatherRepoImpl

@RunWith(JUnit4::class)
class GetForecastUseCaseTest {
    private val useCase = mock<GetWeatherForecastUseCase>()
    private val fakeResult =  mock<List<Forecast>>()

    @Test
    fun `test getCurrentWeather with network error`() = runBlocking {
        val lat = 40.7128
        val lon = -74.0060
        val networkError = NetworkError.SERVER_ERROR

        `when`(useCase.invoke(lat, lon, null)).thenReturn(Result.Error(networkError))

        val result = useCase.invoke(lat = lat, lon = lon, name = null)

        assertTrue(result is Result.Error)
        val error = (result as Result.Error).error
        assertEquals(networkError, error)
    }

    @Test
    fun `test getCurrentWeather with success`() = runBlocking {
        val lat = 40.7128
        val lon = -74.0060

        `when`(useCase.invoke(lat, lon, null)).thenReturn(Result.Success(fakeResult))

        val result = useCase.invoke(lat = lat, lon = lon, name = null)

        assertTrue(result is Result.Success)
    }
}