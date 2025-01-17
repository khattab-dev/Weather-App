package unilever.it.org.data.repositories

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
import unilever.it.org.repositories.SearchRepoImpl


@RunWith(JUnit4::class)
class SearchRepositoryTestCase {
    private val repo = mock<SearchRepoImpl>()

    @Test
    fun `test getLatestSearch returns a valid list of search results`() = runBlocking {
        val searchResults = listOf("Item1", "Item2", "Item3")
        val flow: Flow<List<String>> = flow {
            emit(searchResults)
        }

        `when`(repo.getLatestSearch()).thenReturn(flow)

        val resultFlow = repo.getLatestSearch()

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

        `when`(repo.getLatestSearch()).thenReturn(flow)

        val resultFlow = repo.getLatestSearch()

        resultFlow.collect { result ->
            assertTrue(result.isEmpty())
        }
    }

    @Test
    fun `test getLatestSearch with error`() = runBlocking {
        val errorFlow: Flow<List<String>> = flow {
            throw Exception("Network error")
        }

        `when`(repo.getLatestSearch()).thenReturn(errorFlow)

        try {
            val resultFlow = repo.getLatestSearch()

            resultFlow.collect { result ->

            }

            assertTrue("Expected exception to be thrown",false)
        } catch (e: Exception) {
            assertEquals("Network error", e.message)
        }
    }
}
