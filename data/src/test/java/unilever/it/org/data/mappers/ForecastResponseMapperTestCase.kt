package unilever.it.org.data.mappers

import android.util.Log
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import unilever.it.org.common.Constants
import unilever.it.org.data_source.network.models.weather_forcast.City
import unilever.it.org.data_source.network.models.weather_forcast.Coord
import unilever.it.org.data_source.network.models.weather_forcast.Main
import unilever.it.org.data_source.network.models.weather_forcast.Sys
import unilever.it.org.data_source.network.models.weather_forcast.Weather
import unilever.it.org.data_source.network.models.weather_forcast.WeatherForecastResponse
import unilever.it.org.data_source.network.models.weather_forcast.WeatherItem
import unilever.it.org.mappers.toForecastList

@RunWith(JUnit4::class)
class ForecastResponseMapperTestCase {
    private val sampleCoord = Coord(lat = 40.7128, lon = -74.0060)
    private val sampleCity = City(
        coord = sampleCoord,
        country = "US",
        id = 5128581,
        name = "New York",
        population = 8419600,
        sunrise = 1673815890,
        sunset = 1673862890,
        timezone = -5
    )

    private val sampleMain = Main(
        feelsLike = 21.5,
        grndLevel = 1015,
        humidity = 75,
        pressure = 1020,
        seaLevel = 1018,
        temp = 22.5,
        tempKf = 0.0,
        tempMax = 24.0,
        tempMin = 20.0
    )

    private val sampleSys = Sys(pod = "d")
    private val sampleWeatherDescription = Weather(id = 800, main = "Clear", description = "clear sky", icon = "01d")
    private val sampleWeatherItem = WeatherItem(
        clouds = null,
        dt = 1674019200,
        dtTxt = "2025-01-17 12:00:00",
        main = sampleMain,
        pop = 0,
        sys = sampleSys,
        visibility = 10000,
        weather = listOf(sampleWeatherDescription),
        wind = null
    )

    private val sampleWeatherResponse = WeatherForecastResponse(
        city = sampleCity,
        cnt = 1,
        cod = "200",
        list = listOf(sampleWeatherItem),
        message = 0
    )


    @Test
    fun `test toForecastList with valid weather data`() {
        val result = sampleWeatherResponse.toForecastList()

        assertEquals(1, result.size)
        assertEquals("Today", result[0].date)
        assertEquals(22.5, result[0].temperature)
        assertEquals("Clear", result[0].status)

        val expectedIconUrl = "${Constants.BASE_IMAGE_URL}01d@2x.png"
        assertEquals(expectedIconUrl, result[0].iconUrl)
    }
}