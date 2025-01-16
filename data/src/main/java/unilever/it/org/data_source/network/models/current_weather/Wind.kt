package unilever.it.org.data_source.network.models.current_weather


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Wind(
    @Json(name = "deg")
    val deg: Double?,
    @Json(name = "speed")
    val speed: Double?
)