package unilever.it.org.data_source.network.models.weather_forcast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Sys(
    @Json(name = "pod")
    val pod: String?
)