package unilever.it.org.data_source.network.models.current_weather


import kotlinx.serialization.Serializable

@Serializable
data class Coord(
    val lat: Double,
    val lon: Double
)