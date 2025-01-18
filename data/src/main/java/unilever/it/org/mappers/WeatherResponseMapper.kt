package unilever.it.org.mappers

import unilever.it.org.data_source.network.models.current_weather.CurrentWeatherResponse
import unilever.it.org.domain.models.CurrentWeather
import unilever.it.org.weather_formatter.DateUtils.isDayTime
import unilever.it.org.weather_formatter.getIconUrl
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun CurrentWeatherResponse.toCurrentWeather(): CurrentWeather {
    val isDay = isDayTime(
        currentTime = dt ?: 0L,
        sunriseTime = sys?.sunrise ?: 0L,
        sunsetTime = sys?.sunset ?: 0L
    )

    return CurrentWeather(
        isDay = isDay,
        iconUrl = getIconUrl(isDay = isDay, weatherCode = weather?.first()?.id ?: 0),
        temp = main?.temp ?: 0.0,
        weatherStatus = weather?.first()?.main ?: "",
        feelsLike = main?.feelsLike ?: 0.0,
        maxTemp = main?.tempMax ?: 0.0,
        minTemp = main?.tempMin ?: 0.0,
        pressure = main?.pressure ?: 0,
        humidity = main?.humidity ?: 0,
        windSpeed = wind?.speed ?: 0.0,
        windDegree = wind?.deg ?: 0.0,
        cityName = name ?: "",
        date = formatTimestamp(dt ?: 0L),

    )
}

fun formatTimestamp(timestamp: Long): String {
    val date = Date(timestamp * 1000)  // Convert seconds to milliseconds

    // Define the desired format
    val formatter = SimpleDateFormat("EEEE, dd MMM", Locale.getDefault())

    // Format the date
    return formatter.format(date)
}