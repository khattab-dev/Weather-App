package unilever.it.org.domain.repositories

import unilever.it.org.domain.models.CurrentWeather
import unilever.it.org.domain.models.Forecast
import unilever.it.org.domain.models.NetworkError
import unilever.it.org.domain.models.Result

interface ForecastRepository {
    suspend fun getForecastData(lat : Double?, lon : Double?, name : String? = null): Result<List<Forecast>, NetworkError>
}