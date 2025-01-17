package unilever.it.org.data.utils

import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import unilever.it.org.common.Constants
import unilever.it.org.common.getIconUrl

@RunWith(JUnit4::class)
class UtilsTestCase {
    @Test
    fun `test getIconUrl with valid weather codes and day conditions`() {
        assertEquals("${Constants.BASE_IMAGE_URL}01d@2x.png", getIconUrl(800, true))
        assertEquals(
            "${Constants.BASE_IMAGE_URL}11d@2x.png",
            getIconUrl(200, true)
        )
        assertEquals("${Constants.BASE_IMAGE_URL}09d@2x.png", getIconUrl(300, true))
        assertEquals("${Constants.BASE_IMAGE_URL}10d@2x.png", getIconUrl(500, true))
        assertEquals("${Constants.BASE_IMAGE_URL}13d@2x.png", getIconUrl(600, true))
        assertEquals("${Constants.BASE_IMAGE_URL}50d@2x.png", getIconUrl(701, true))
        assertEquals("${Constants.BASE_IMAGE_URL}02d@2x.png", getIconUrl(801, true))
    }

    @Test
    fun `test getIconUrl with valid weather codes and night conditions`() {
        assertEquals("${Constants.BASE_IMAGE_URL}01n@2x.png", getIconUrl(800, false))
        assertEquals(
            "${Constants.BASE_IMAGE_URL}11n@2x.png",
            getIconUrl(200, false)
        )
        assertEquals("${Constants.BASE_IMAGE_URL}09n@2x.png", getIconUrl(300, false))
        assertEquals("${Constants.BASE_IMAGE_URL}10n@2x.png", getIconUrl(500, false))
        assertEquals("${Constants.BASE_IMAGE_URL}13n@2x.png", getIconUrl(600, false))
        assertEquals("${Constants.BASE_IMAGE_URL}50n@2x.png", getIconUrl(701, false))
        assertEquals("${Constants.BASE_IMAGE_URL}02n@2x.png", getIconUrl(801, false))
    }

    @Test
    fun `test getIconUrl with edge weather codes`() {
        assertEquals("${Constants.BASE_IMAGE_URL}01d@2x.png", getIconUrl(800, true))
        assertEquals(
            "${Constants.BASE_IMAGE_URL}04d@2x.png",
            getIconUrl(803, true)
        )
        assertEquals("${Constants.BASE_IMAGE_URL}01n@2x.png", getIconUrl(800, false))
        assertEquals(
            "${Constants.BASE_IMAGE_URL}04n@2x.png",
            getIconUrl(804, false)
        )
    }

    @Test
    fun `test getIconUrl with unknown weather code`() {
        assertEquals(
            "${Constants.BASE_IMAGE_URL}01d@2x.png",
            getIconUrl(999, true)
        )
        assertEquals(
            "${Constants.BASE_IMAGE_URL}01n@2x.png",
            getIconUrl(999, false)
        )
    }
}


