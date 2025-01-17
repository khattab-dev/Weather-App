package unilever.it.org.mappers

import unilever.it.org.common.DateUtils.extractDate
import unilever.it.org.common.DateUtils.getDayName
import unilever.it.org.common.getIconUrl
import unilever.it.org.data_source.network.models.weather_forcast.WeatherForecastResponse
import unilever.it.org.data_source.network.models.weather_forcast.WeatherItem
import unilever.it.org.domain.models.Forecast

fun WeatherForecastResponse.toForecastList(): List<Forecast> {
    val forecastList = mutableListOf<Forecast>()
    var currentDate: String? = null

    this.list?.forEach { forecast ->
        val date = extractDate(forecast?.dtTxt ?: "")
        if (date != currentDate) {
            val dayName = getDayName(date)
            val forecastItem = createForecast(forecast, dayName, currentDate == null)
            forecastList.add(forecastItem)
            currentDate = date
        }
    }

    return forecastList
}


internal fun createForecast(forecast: WeatherItem?, dayName: String, isToday: Boolean): Forecast {
    val isDay = forecast?.sys?.pod == "d"
    return Forecast(
        temperature = forecast?.main?.temp ?: 0.0,
        iconUrl = getIconUrl(isDay = isDay, weatherCode = forecast?.weather?.firstOrNull()?.id ?: 0),
        status = forecast?.weather?.firstOrNull()?.main ?: "",
        date = if (isToday) "Today" else dayName
    )
}

