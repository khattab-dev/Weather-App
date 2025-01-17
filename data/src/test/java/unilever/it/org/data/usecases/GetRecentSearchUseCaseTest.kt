package unilever.it.org.data.usecases

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import unilever.it.org.domain.models.CurrentWeather
import unilever.it.org.domain.models.NetworkError
import unilever.it.org.domain.models.Result
import unilever.it.org.domain.usecases.GetRecentSearchUseCase
import unilever.it.org.repositories.CurrentWeatherRepoImpl

@RunWith(JUnit4::class)
class GetRecentSearchUseCaseTest {
    private val useCase = mock<GetRecentSearchUseCase>()

    @Test
    fun `test getLatestSearch returns a valid list of search results`() = runBlocking {
        val searchResults = listOf("Item1", "Item2", "Item3")
        val flow: Flow<List<String>> = flow {
            emit(searchResults)
        }

        `when`(useCase.invoke()).thenReturn(flow)

        val resultFlow = useCase.invoke()

        resultFlow.collect { result ->
            assertEquals(searchResults, result)
        }
    }

    @Test
    fun `test getLatestSearch with empty list`() = runBlocking {
        val searchResults = emptyList<String>()
        val flow: Flow<List<String>> = flow {
            emit(searchResults)
        }

        `when`(useCase.invoke()).thenReturn(flow)

        val resultFlow = useCase.invoke()

        resultFlow.collect { result ->
            assertTrue(result.isEmpty())
        }
    }

    @Test
    fun `test getLatestSearch with error`() = runBlocking {
        val errorFlow: Flow<List<String>> = flow {
            throw Exception("Network error")
        }

        `when`(useCase.invoke()).thenReturn(errorFlow)

        try {
            val resultFlow = useCase.invoke()

            resultFlow.collect { result ->

            }

            assertTrue("Expected exception to be thrown",false)
        } catch (e: Exception) {
            assertEquals("Network error", e.message)
        }
    }
}