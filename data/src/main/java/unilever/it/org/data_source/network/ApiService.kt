package unilever.it.org.data_source.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import unilever.it.org.data_source.network.models.current_weather.CurrentWeatherResponse
import unilever.it.org.data_source.network.models.weather_forcast.WeatherForecastResponse

interface ApiService {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double? = null,
        @Query("lon") lon: Double? = null,
        @Query("q") name: String? = null,
        @Query("units") units: String = "metric",
    ): Response<CurrentWeatherResponse>

    @GET("forecast")
    suspend fun getWeatherForecast(
        @Query("lat") lat: Double? = null,
        @Query("lon") lon: Double? = null,
        @Query("q") name: String? = null,
        @Query("units") units: String = "metric",
    ): Response<WeatherForecastResponse>

}