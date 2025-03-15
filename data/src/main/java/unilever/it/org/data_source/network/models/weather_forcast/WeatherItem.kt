package unilever.it.org.data_source.network.models.weather_forcast

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherItem(
    val clouds: Clouds?,
    val dt: Long?,
    @SerialName(value = "dt_txt")
    val dtTxt: String,
    val main: Main?,
    val pop: Double?,
    val sys: Sys?,
    val visibility: Int?,
    val weather: List<Weather?>?,
    val wind: Wind?
)