package unilever.it.org.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import unilever.it.org.domain.models.CurrentWeather
import unilever.it.org.domain.models.Forecast
import unilever.it.org.domain.models.onError
import unilever.it.org.domain.models.onSuccess
import unilever.it.org.domain.usecases.GetCurrentWeatherUseCase
import unilever.it.org.domain.usecases.GetWeatherForecastUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getForecastUseCase: GetWeatherForecastUseCase
) :
    ViewModel() {
    private val _currentWeather = MutableStateFlow<CurrentWeather?>(null)
    val currentWeather get() = _currentWeather.asStateFlow()

    private val _forecast = MutableStateFlow<List<Forecast>>(emptyList())
    val forecast get() = _forecast.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading get() = _loading.asStateFlow()

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    fun getWeatherData(lat: Double, lon: Double) = viewModelScope.launch(Dispatchers.IO) {
        getCurrentWeather(lat, lon)
        getForecastData(lat, lon)
    }

    private suspend fun getCurrentWeather(lat: Double, lon: Double) {
        val result = getCurrentWeatherUseCase(lat, lon)

        result.onSuccess {
            _currentWeather.value = it
        }.onError {
            _error.send(it.toString())
        }
    }

    private suspend fun getForecastData(lat: Double?, lon: Double?) {
        getForecastUseCase.invoke(lat, lon).onSuccess {
            _forecast.value = it
        }.onError {
            _error.send(it.toString())
        }
    }
}