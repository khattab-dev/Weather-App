package unilever.it.org.domain.usecases

import unilever.it.org.domain.repositories.CurrentWeatherRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(private val repo: CurrentWeatherRepository){
    suspend operator fun invoke(lat : Double, lon : Double) = repo.getCurrentWeather(lat,lon)
}