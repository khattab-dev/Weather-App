package unilever.it.org.domain.models

data class Forecast(
    val temperature: Double,
    val iconUrl : String,
    val status : String,
    val date : String
)