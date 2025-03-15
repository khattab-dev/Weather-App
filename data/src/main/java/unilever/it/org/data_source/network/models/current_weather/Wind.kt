package unilever.it.org.data_source.network.models.current_weather


import kotlinx.serialization.Serializable

@Serializable
data class Wind(
    val deg: Double,
    val gust: Double,
    val speed: Double
)