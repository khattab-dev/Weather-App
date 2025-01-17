package unilever.it.org.data.mappers

import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import unilever.it.org.common.Constants
import unilever.it.org.data_source.network.models.current_weather.Clouds
import unilever.it.org.data_source.network.models.current_weather.Coord
import unilever.it.org.data_source.network.models.current_weather.CurrentWeatherResponse
import unilever.it.org.data_source.network.models.current_weather.Main
import unilever.it.org.data_source.network.models.current_weather.Sys
import unilever.it.org.data_source.network.models.current_weather.Weather
import unilever.it.org.data_source.network.models.current_weather.Wind
import unilever.it.org.mappers.toCurrentWeather

@RunWith(JUnit4::class)
class WeatherResponseMapperTestCase {
    private val sampleWeatherDescription = Weather(id = 800, main = "Clear", description = "clear sky", icon = "01d")
    private val sampleMain = Main(
        feelsLike = 21.5,
        pressure = 1015,
        humidity = 75,
        temp = 22.5,
        tempMax = 24.0,
        tempMin = 20.0,
        grndLevel = 141,
        seaLevel = 41241
    )
    private val sampleSys = Sys(
        sunrise = 1673815890, sunset = 1673862890,
        country = "EG",
        id = 41,
        type = 1
    )
    private val clouds = Clouds(
        all = 4
    )
    private val coords = Coord(1.4,12.2)
    private val sampleWind = Wind(speed = 5.0, deg = 90.0)
    private val sampleCurrentWeatherResponse = CurrentWeatherResponse(
        dt = 1674019200,
        sys = sampleSys,
        main = sampleMain,
        weather = listOf(sampleWeatherDescription),
        wind = sampleWind,
        name = "New York",
        base = "test",
        clouds = clouds,
        cod = 2,
        coord = coords,
        id = 2,
        timezone = 4,
        visibility = 1
    )

    @Test
    fun `test toCurrentWeather with invalid response data`() {

        val currentWeather = sampleCurrentWeatherResponse.toCurrentWeather()

        assertEquals(false, currentWeather.isDay)
        assertEquals("${Constants.BASE_IMAGE_URL}01n@2x.png", currentWeather.iconUrl)
        assertEquals(22.5, currentWeather.temp)
        assertEquals("Clear", currentWeather.weatherStatus)
        assertEquals(21.5, currentWeather.feelsLike)
        assertEquals(24.0, currentWeather.maxTemp)
        assertEquals(20.0, currentWeather.minTemp)
        assertEquals(1015, currentWeather.pressure)
        assertEquals(75, currentWeather.humidity)
        assertEquals(5.0, currentWeather.windSpeed)
        assertEquals(90.0, currentWeather.windDegree)
        assertEquals("New York", currentWeather.cityName)
    }
}