package unilever.it.org.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import unilever.it.org.data_source.local.dao.SearchDao
import unilever.it.org.domain.repositories.SearchRepository
import javax.inject.Inject

class SearchRepoImpl @Inject constructor(private val dao : SearchDao) : SearchRepository {
    override fun getLatestSearch(): Flow<List<String>> {
        return dao.getLatestSearch().map { it.map { it.name }.reversed() }
    }
}