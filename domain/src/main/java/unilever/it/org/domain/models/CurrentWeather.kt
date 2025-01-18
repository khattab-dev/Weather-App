package unilever.it.org.domain.models

data class CurrentWeather(
    val cityName: String,
    val temp: Double,
    val iconUrl: String,
    val weatherStatus: String,
    val isDay: Boolean,
    val minTemp: Double,
    val maxTemp: Double,
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    val windSpeed: Double,
    val windDegree: Double,
)
