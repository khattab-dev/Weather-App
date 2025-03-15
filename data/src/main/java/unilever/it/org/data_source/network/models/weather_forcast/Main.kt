package unilever.it.org.data_source.network.models.weather_forcast


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable()
data class Main(
    @SerialName(value = "feels_like")
    val feelsLike: Double?,
    @SerialName(value = "grnd_level")
    val grndLevel: Int?,
    val humidity: Int?,
    val pressure: Int?,
    @SerialName(value = "sea_level")
    val seaLevel: Int?,
    val temp: Double?,
    @SerialName(value = "temp_kf")
    val tempKf: Double?,
    @SerialName(value = "temp_max")
    val tempMax: Double?,
    @SerialName(value = "temp_min")
    val tempMin: Double?
)