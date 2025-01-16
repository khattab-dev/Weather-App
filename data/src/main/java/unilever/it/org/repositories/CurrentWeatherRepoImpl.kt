package unilever.it.org.repositories

import unilever.it.org.data_source.network.ApiService
import unilever.it.org.domain.models.CurrentWeather
import unilever.it.org.domain.repositories.CurrentWeatherRepository
import unilever.it.org.data_source.network.models.current_weather.CurrentWeatherResponse
import unilever.it.org.data_source.network.safeCall
import unilever.it.org.domain.models.NetworkError
import unilever.it.org.domain.models.Result
import unilever.it.org.domain.models.map
import unilever.it.org.mappers.toCurrentWeather
import javax.inject.Inject

class CurrentWeatherRepoImpl @Inject constructor(private val apiService: ApiService) : CurrentWeatherRepository {
    override suspend fun getCurrentWeather(lat: Double, lon: Double): Result<CurrentWeather?, NetworkError> {
        return safeCall<CurrentWeatherResponse> {
            apiService.getCurrentWeather(lat,lon)
        }.map {
            it?.toCurrentWeather()
        }
    }
}