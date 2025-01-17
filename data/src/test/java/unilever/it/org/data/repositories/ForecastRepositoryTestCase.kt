package unilever.it.org.data.repositories

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import unilever.it.org.domain.models.Forecast
import unilever.it.org.domain.models.NetworkError
import unilever.it.org.domain.models.Result
import unilever.it.org.repositories.ForecastRepoImpl

@RunWith(JUnit4::class)
class ForecastRepositoryTestCase {
    private val repo = mock<ForecastRepoImpl>()
    private val fakeResponse = mock<List<Forecast>>()

    @Test
    fun `test getCurrentWeather with network error`() = runBlocking {
        val lat = 40.7128
        val lon = -74.0060
        val networkError = NetworkError.SERVER_ERROR

        `when`(repo.getForecastData(lat, lon, null)).thenReturn(Result.Error(networkError))

        val result = repo.getForecastData(lat = lat, lon = lon, name = null)

        assertTrue(result is Result.Error)
        val error = (result as Result.Error).error
        assertEquals(networkError, error)
    }

    @Test
    fun `test getCurrentWeather with success`() = runBlocking {
        val lat = 40.7128
        val lon = -74.0060

        `when`(repo.getForecastData(lat, lon, null)).thenReturn(Result.Success(fakeResponse))

        val result = repo.getForecastData(lat = lat, lon = lon, name = null)

        assertTrue(result is Result.Success)
    }
}