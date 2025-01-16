package unilever.it.org.data_source.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("search_entity")
data class SearchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
)
