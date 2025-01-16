package unilever.it.org.data_source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import unilever.it.org.data_source.local.entities.SearchEntity

@Dao
interface SearchDao {
    @Query("SELECT * FROM search_entity LIMIT 5 OFFSET (SELECT COUNT(*) FROM search_entity) - 5")
    fun getLatestSearch(): Flow<List<SearchEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(searchEntity: SearchEntity)
}