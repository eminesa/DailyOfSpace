package com.eminesa.photoofspace.di

import com.eminesa.photoofspace.network.ImageRepository
import com.eminesa.photoofspace.network.ImageRepositoryImpl
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
    abstract fun bindDailyImageRepository(
        dailyImageRepositoryImpl: ImageRepositoryImpl
    ): ImageRepository
}
