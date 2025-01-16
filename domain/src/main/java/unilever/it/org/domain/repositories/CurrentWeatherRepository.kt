package unilever.it.org.domain.repositories

import unilever.it.org.domain.models.CurrentWeather
import unilever.it.org.domain.models.NetworkError
import unilever.it.org.domain.models.Result

interface CurrentWeatherRepository {
    suspend fun getCurrentWeather(lat : Double, lon : Double): Result<CurrentWeather?,NetworkError>
}