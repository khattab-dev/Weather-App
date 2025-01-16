package unilever.it.org.domain.repositories

import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun getLatestSearch() : Flow<List<String>>
}