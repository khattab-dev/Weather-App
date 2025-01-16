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
import unilever.it.org.domain.models.onError
import unilever.it.org.domain.models.onSuccess
import unilever.it.org.domain.usecases.GetCurrentWeatherUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase) :
    ViewModel() {
    private val _currentWeather = MutableStateFlow<CurrentWeather?>(null)
    val currentWeather get() = _currentWeather.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading get() = _loading.asStateFlow()

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    fun getCurrentWeather(lat: Double, lon: Double) =
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            val result = getCurrentWeatherUseCase(lat, lon)

            result.onSuccess {
                _currentWeather.value = it
            }.onError {
                _error.send(it.toString())
            }

            _loading.value = false
        }
}