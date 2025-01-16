package unilever.it.org.forecast

import unilever.it.org.domain.models.NetworkError
import unilever.it.org.domain.models.ScreenAction

sealed interface ForecastEvents : ScreenAction {
    data class Error(val error : NetworkError) : ForecastEvents
}