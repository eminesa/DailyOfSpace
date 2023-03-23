package com.eminesa.dailyofspace.di

import com.eminesa.dailyofspace.network.ImageRepository
import com.eminesa.dailyofspace.network.ImageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        movieRepositoryImpl: ImageRepositoryImpl
    ): ImageRepository
}
