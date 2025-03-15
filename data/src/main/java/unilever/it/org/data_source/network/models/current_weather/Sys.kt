package unilever.it.org.data_source.network.models.current_weather


import kotlinx.serialization.Serializable

@Serializable
data class Sys(
    val country: String,
    val sunrise: Long,
    val sunset: Long
)