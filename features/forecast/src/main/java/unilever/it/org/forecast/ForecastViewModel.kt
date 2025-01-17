package unilever.it.org.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import unilever.it.org.domain.models.onError
import unilever.it.org.domain.models.onSuccess
import unilever.it.org.domain.usecases.GetWeatherForecastUseCase
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val getWeatherForecastUseCase: GetWeatherForecastUseCase
) :
    ViewModel() {
    private val _state = MutableStateFlow(ForecastState())
    val state = _state.asStateFlow()

    private val _events = Channel<ForecastEvents>()
    val events = _events.receiveAsFlow()

    fun onAction(action: ForecastActions) {
        when (action) {
            is ForecastActions.GetForecastData -> {
                getForecastData(action.lat, action.lon)
            }
        }
    }

    private fun getForecastData(lat: Double?, lon: Double?) =
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(isLoading = true)
            }

            getWeatherForecastUseCase.invoke(lat, lon).onSuccess {
                _state.update { state ->
                    state.copy(forecasts = it)
                }
            }.onError {
                _events.send(ForecastEvents.Error(it))
            }

            _state.update {
                it.copy(isLoading = false)
            }
        }
}