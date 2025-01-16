package unilever.it.org.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import unilever.it.org.data_source.local.dao.SearchDao
import unilever.it.org.data_source.local.database.WeatherDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WeatherDatabase {
        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "weather_app_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideSearchDao(
        db: WeatherDatabase
    ): SearchDao = db.searchDao()
}