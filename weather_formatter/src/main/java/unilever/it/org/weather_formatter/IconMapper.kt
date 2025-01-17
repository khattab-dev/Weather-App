package unilever.it.org.weather_formatter

private const val BASE_IMAGE_URL = "https://openweathermap.org/img/wn/"

fun getIconUrl(weatherCode: Int, isDay: Boolean): String {
    val iconSuffix = when (weatherCode) {
        in 200..232 -> "11${if (isDay) "d" else "n"}" // Thunderstorm
        in 300..321 -> "09${if (isDay) "d" else "n"}" // Drizzle
        in 500..504 -> "10${if (isDay) "d" else "n"}" // Rain
        511 -> "13${if (isDay) "d" else "n"}"         // Freezing Rain
        in 520..531 -> "09${if (isDay) "d" else "n"}" // Shower Rain
        in 600..622 -> "13${if (isDay) "d" else "n"}" // Snow
        in 701..781 -> "50${if (isDay) "d" else "n"}" // Atmosphere
        800 -> "01${if (isDay) "d" else "n"}"         // Clear Sky
        801 -> "02${if (isDay) "d" else "n"}"         // Few Clouds
        802 -> "03${if (isDay) "d" else "n"}"         // Scattered Clouds
        in 803..804 -> "04${if (isDay) "d" else "n"}" // Broken/Overcast Clouds
        else -> "01${if (isDay) "d" else "n"}"        // Default (Clear Sky)
    }
    return "${BASE_IMAGE_URL}$iconSuffix@2x.png"
}