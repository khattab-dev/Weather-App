package unilever.it.org.data_source.network.models.weather_forcast

import kotlinx.serialization.Serializable

@Serializable
data class Clouds(
    val all: Int?
)