package unilever.it.org.data_source.network.models.weather_forcast

import kotlinx.serialization.Serializable

@Serializable
data class Wind(
    val deg: Int?,
    val gust: Double?,
    val speed: Double?
)