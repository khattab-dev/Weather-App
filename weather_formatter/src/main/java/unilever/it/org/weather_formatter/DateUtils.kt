package unilever.it.org.weather_formatter

import java.text.SimpleDateFormat
import java.util.Locale

object DateUtils {
    private var dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private var dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())

    fun extractDate(dateTime: String): String {
        return dateTime.substring(0, 10)
    }

    fun getDayName(date: String): String {
        return try {
            val dateObj = dateFormat.parse(date)
            if (dateObj != null) dayFormat.format(dateObj) else ""
        } catch (e: Exception) {
            ""
        }
    }

    fun isDayTime(currentTime: Long, sunriseTime: Long, sunsetTime: Long): Boolean {
        return currentTime in sunriseTime..sunsetTime
    }
}