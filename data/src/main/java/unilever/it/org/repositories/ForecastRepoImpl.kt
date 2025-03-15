package unilever.it.org.repositories

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import unilever.it.org.data_source.network.models.weather_forcast.WeatherForecastResponse
import unilever.it.org.data_source.network.safeCall
import unilever.it.org.domain.models.Forecast
import unilever.it.org.domain.models.NetworkError
import unilever.it.org.domain.models.Result
import unilever.it.org.domain.models.map
import unilever.it.org.domain.repositories.ForecastRepository
import unilever.it.org.mappers.toForecastList
import javax.inject.Inject

class ForecastRepoImpl @Inject constructor(
    private val client: HttpClient
) : ForecastRepository {
    override suspend fun getForecastData(
        lat: Double?,
        lon: Double?,
        name: String?
    ): Result<List<Forecast>, NetworkError> {
        return safeCall<WeatherForecastResponse> {
            when (lat == null || lon == null) {
                true -> client.get("forecast") {
                    url {
                        parameters.append("q", name ?: "London")
                        parameters.append("units", "metric")
                    }
                }
                false -> client.get("forecast") {
                    url {
                        parameters.append("units", "metric")
                        parameters.append("lat", lat.toString())
                        parameters.append("lon", lon.toString())
                    }
                }
            }
        }.map {
            it.toForecastList()
        }
    }
}