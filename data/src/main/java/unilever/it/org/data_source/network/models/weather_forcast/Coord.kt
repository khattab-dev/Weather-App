package unilever.it.org.data_source.network.models.weather_forcast


import kotlinx.serialization.Serializable

@Serializable()
data class Coord(
    val lat: Double?,
    val lon: Double?
)