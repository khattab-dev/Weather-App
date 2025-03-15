package unilever.it.org.data_source.network.models.weather_forcast


import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    val description: String?,
    val icon: String?,
    val id: Int?,
    val main: String?
)