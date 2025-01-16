package unilever.it.org.repositories

import android.util.Log
import unilever.it.org.data_source.local.dao.SearchDao
import unilever.it.org.data_source.local.entities.SearchEntity
import unilever.it.org.data_source.network.ApiService
import unilever.it.org.domain.models.CurrentWeather
import unilever.it.org.domain.repositories.CurrentWeatherRepository
import unilever.it.org.data_source.network.models.current_weather.CurrentWeatherResponse
import unilever.it.org.data_source.network.safeCall
import unilever.it.org.domain.models.NetworkError
import unilever.it.org.domain.models.Result
import unilever.it.org.domain.models.map
import unilever.it.org.mappers.toCurrentWeather
import javax.inject.Inject

class CurrentWeatherRepoImpl @Inject constructor(private val apiService: ApiService,private val searchDao: SearchDao) : CurrentWeatherRepository {
    override suspend fun getCurrentWeather(lat: Double?, lon: Double?,name: String?): Result<CurrentWeather?, NetworkError> {
        return safeCall<CurrentWeatherResponse> {

            name?.let {
                searchDao.insertSearch(SearchEntity(name = it))
            }

            when (lat == null || lon == null) {
                true -> apiService.getCurrentWeather(name = name ?: "London")
                false -> apiService.getCurrentWeather(lat,lon)
            }
        }.map {
            it?.toCurrentWeather()
        }
    }
}