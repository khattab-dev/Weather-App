package unilever.it.org.domain.usecases

import unilever.it.org.domain.repositories.ForecastRepository
import javax.inject.Inject

class GetWeatherForecastUseCase @Inject constructor(private val repo: ForecastRepository){
    suspend operator fun invoke(lat : Double?, lon : Double?, name : String? = null) = repo.getForecastData(lat,lon,name)
}