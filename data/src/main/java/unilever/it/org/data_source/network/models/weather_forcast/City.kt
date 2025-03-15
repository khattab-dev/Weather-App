package unilever.it.org.data_source.network.models.weather_forcast


import kotlinx.serialization.Serializable

@Serializable
data class City(
    val coord: Coord?,
    val country: String?,
    val id: Int?,
    val name: String?,
    val population: Int?,
    val sunrise: Int?,
    val sunset: Int?,
    val timezone: Int?
)