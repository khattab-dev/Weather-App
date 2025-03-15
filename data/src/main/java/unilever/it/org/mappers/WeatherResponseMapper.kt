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
        currentTime = dt,
        sunriseTime = sys.sunrise,
        sunsetTime = sys.sunset
    )

    return CurrentWeather(
        isDay = isDay,
        iconUrl = getIconUrl(isDay = isDay, weatherCode = weather.first().id),
        temp = main.temp,
        weatherStatus = weather.first().main,
        feelsLike = main.feelsLike,
        maxTemp = main.tempMax,
        minTemp = main.tempMin,
        pressure = main.pressure,
        humidity = main.humidity,
        windSpeed = wind.speed,
        windDegree = wind.deg,
        cityName = name,
        date = formatTimestamp(dt),

    )
}

fun formatTimestamp(timestamp: Long): String {
    val date = Date(timestamp * 1000)  // Convert seconds to milliseconds

    // Define the desired format
    val formatter = SimpleDateFormat("EEEE, dd MMM", Locale.getDefault())

    // Format the date
    return formatter.format(date)
}