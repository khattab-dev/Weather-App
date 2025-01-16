package unilever.it.org.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import unilever.it.org.domain.repositories.CurrentWeatherRepository
import unilever.it.org.repositories.CurrentWeatherRepoImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {
    @Binds
    @Singleton
    abstract fun bindCurrentWeatherRepo(repo: CurrentWeatherRepoImpl) : CurrentWeatherRepository
}