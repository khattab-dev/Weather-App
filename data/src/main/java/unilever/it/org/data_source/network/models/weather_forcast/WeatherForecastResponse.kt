package unilever.it.org.data_source.network.models.weather_forcast

import kotlinx.serialization.Serializable

@Serializable
data class WeatherForecastResponse(
    val city: City?,
    val cnt: Int?,
    val cod: String?,
    val list: List<WeatherItem>,
    val message: Int?
)