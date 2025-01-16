package unilever.it.org.forecast

import unilever.it.org.domain.models.Forecast
import unilever.it.org.domain.models.ScreenState

data class ForecastState(
    val forecasts: List<Forecast> = emptyList(),
    val isLoading : Boolean = false
) : ScreenState