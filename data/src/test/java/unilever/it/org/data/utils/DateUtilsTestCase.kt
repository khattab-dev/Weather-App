package unilever.it.org.data.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import unilever.it.org.common.DateUtils.extractDate
import unilever.it.org.common.DateUtils.getDayName
import unilever.it.org.common.DateUtils.isDayTime

@RunWith(JUnit4::class)
class DateUtilsTestCase {
    @Test
    fun `test getDayName with valid date`() {
        val result = getDayName("2025-01-17")
        assertEquals("Friday", result)
    }

    @Test
    fun `test getDayName with invalid date`() {
        val result = getDayName("invalid-date")
        assertEquals("", result)
    }

    @Test
    fun `test isDayTime when current time is within daytime`() {
        val currentTime = 12_00L
        val sunriseTime = 6_00L
        val sunsetTime = 18_00L

        val result = isDayTime(currentTime, sunriseTime, sunsetTime)

        assertTrue(result)
    }

    @Test
    fun `test isDayTime when current time is before sunrise`() {
        val currentTime = 5_00L
        val sunriseTime = 6_00L
        val sunsetTime = 18_00L

        val result = isDayTime(currentTime, sunriseTime, sunsetTime)

        assertFalse(result)
    }

    @Test
    fun `test isDayTime when current time is after sunset`() {
        val currentTime = 19_00L
        val sunriseTime = 6_00L
        val sunsetTime = 18_00L

        val result = isDayTime(currentTime, sunriseTime, sunsetTime)

        assertFalse(result)
    }

    @Test
    fun `test extractDate with valid datetime`() {
        val result = extractDate("2025-01-17 15:00:00")
        assertEquals("2025-01-17", result)
    }
}