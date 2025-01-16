package unilever.it.org.domain.usecases

import unilever.it.org.domain.repositories.ForecastRepository
import javax.inject.Inject

class GetWeatherForecastByCoordinatesUseCase @Inject constructor(private val repo: ForecastRepository){
    suspend operator fun invoke(lat : Double?, lon : Double?) = repo.getForecastData(lat,lon)
}