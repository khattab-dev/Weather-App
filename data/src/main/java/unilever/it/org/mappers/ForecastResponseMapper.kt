package unilever.it.org.mappers

import unilever.it.org.common.getIconUrl
import unilever.it.org.data_source.network.models.weather_forcast.WeatherForecastResponse
import unilever.it.org.domain.models.Forecast

import java.text.SimpleDateFormat
import java.util.*

fun WeatherForecastResponse.toForecastList(): List<Forecast> {
    val forecastList = mutableListOf<Forecast>()
    var currentDate: String? = null
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())

    this.list?.forEach { forecast ->
        val date = forecast?.dtTxt?.substring(0, 10) ?: ""

        if (date != currentDate) {
            val dayName = try {
                val dateObj = dateFormat.parse(date) ?: ""
                dayFormat.format(dateObj)
            } catch (e: Exception) {
                ""
            }

            val isDay = forecast?.sys?.pod == "d"
            forecastList.add(
                Forecast(
                    temperature = forecast?.main?.temp ?: 0.0,
                    iconUrl = getIconUrl(isDay = isDay, weatherCode = forecast?.weather?.firstOrNull()?.id ?: 0),
                    status = forecast?.weather?.firstOrNull()?.main ?: "",
                    date = if (currentDate == null) "Today" else dayName
                )
            )
            currentDate = date
        }
    }

    return forecastList
}

