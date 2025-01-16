package unilever.it.org.search_city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import unilever.it.org.domain.models.CurrentWeather
import unilever.it.org.domain.models.Forecast
import unilever.it.org.domain.models.NetworkError
import unilever.it.org.domain.models.onError
import unilever.it.org.domain.models.onSuccess
import unilever.it.org.domain.usecases.GetCurrentWeatherUseCase
import unilever.it.org.domain.usecases.GetRecentSearchUseCase
import unilever.it.org.domain.usecases.GetWeatherForecastUseCase
import javax.inject.Inject

@HiltViewModel
class SearchCityViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getWeatherForecastUseCase: GetWeatherForecastUseCase,
    private val getRecentSearchUseCase: GetRecentSearchUseCase
) : ViewModel() {
    private val _searchValue: MutableStateFlow<String?> = MutableStateFlow(null)
    val searchValue get() = _searchValue.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading get() = _loading.asStateFlow()

    private val _currentWeather = MutableStateFlow<CurrentWeather?>(null)
    val currentWeather get() = _currentWeather.asStateFlow()

    private val _forecast = MutableStateFlow<List<Forecast>>(emptyList())
    val forecast get() = _forecast.asStateFlow()

    private val _error : Channel<NetworkError> = Channel()
    val error get() = _error.receiveAsFlow()

    private val _recentSearch = MutableStateFlow<List<String>>(emptyList())
    val recentSearch get() = _recentSearch.asStateFlow()

    private var job: Job? = null

    init {
        getRecentSearch()
    }

    private fun getRecentSearch() {
        viewModelScope.launch(Dispatchers.IO) {
            getRecentSearchUseCase().collect {
                _recentSearch.value = it
            }
        }
    }

    fun updateSearchValue(value: String) {
        _searchValue.value = value
    }

    fun getWeatherDataByCityName() {
        if (job?.isActive == true) {
            job?.cancel()
        }

        job = viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            getCurrentWeatherUseCase(name = searchValue.value, lat = null, lon = null).onSuccess {
                _currentWeather.value = it
            }.onError {
                _error.send(it)
            }

            getWeatherForecastUseCase(name = searchValue.value, lat = null, lon = null).onSuccess {
                _forecast.value = it
            }.onError {

            }

            _loading.value = false
        }
    }
}