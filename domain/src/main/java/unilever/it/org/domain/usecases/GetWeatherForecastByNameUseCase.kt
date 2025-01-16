package unilever.it.org.domain.usecases

import unilever.it.org.domain.repositories.ForecastRepository
import javax.inject.Inject

class GetWeatherForecastByNameUseCase  @Inject constructor(private val repo: ForecastRepository){
    suspend operator fun invoke(name : String) = repo.getForecastData(name)
}