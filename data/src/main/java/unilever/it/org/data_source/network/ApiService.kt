package unilever.it.org.data_source.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import unilever.it.org.data_source.network.models.current_weather.CurrentWeatherResponse

interface ApiService {
    @GET("2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric",
    ): Response<CurrentWeatherResponse>

}