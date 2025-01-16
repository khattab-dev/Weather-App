package unilever.it.org.forecast

import unilever.it.org.domain.models.ScreenAction

sealed interface ForecastActions : ScreenAction {
    data class GetForecastData(val lat: Double?, val lon: Double?) : ForecastActions
}