package unilever.it.org.repositories

import android.util.Log
import unilever.it.org.data_source.network.ApiService
import unilever.it.org.data_source.network.models.current_weather.CurrentWeatherResponse
import unilever.it.org.data_source.network.models.weather_forcast.WeatherForecastResponse
import unilever.it.org.data_source.network.safeCall
import unilever.it.org.domain.models.Forecast
import unilever.it.org.domain.models.NetworkError
import unilever.it.org.domain.models.Result
import unilever.it.org.domain.models.map
import unilever.it.org.domain.repositories.ForecastRepository
import unilever.it.org.mappers.toCurrentWeather
import unilever.it.org.mappers.toForecastList
import javax.inject.Inject

class ForecastRepoImpl @Inject constructor(private val apiService: ApiService): ForecastRepository {
    override suspend fun getForecastData(
        lat: Double?,
        lon: Double?,
        name: String?
    ): Result<List<Forecast>, NetworkError> {
        return safeCall<WeatherForecastResponse> {
            when (lat == null || lon == null) {
                true -> apiService.getWeatherForecast(name = name ?: "London")
                false -> apiService.getWeatherForecast(lat,lon)
            }
        }.map {
            it?.toForecastList() ?: emptyList()
        }
    }
}