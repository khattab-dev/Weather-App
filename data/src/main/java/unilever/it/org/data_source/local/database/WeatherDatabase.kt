package unilever.it.org.data_source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import unilever.it.org.data_source.local.dao.SearchDao
import unilever.it.org.data_source.local.entities.SearchEntity

@Database(
    entities = [SearchEntity::class], version = 1, exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun searchDao() : SearchDao
}