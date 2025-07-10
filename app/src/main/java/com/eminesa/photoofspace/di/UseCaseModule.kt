package com.eminesa.photoofspace.di

import com.eminesa.photoofspace.use_case.GetDailyImageUseCase
import com.eminesa.photoofspace.use_case.GetDailyImageUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    @Singleton
    abstract fun bindGetDailyImageUseCase(
        getDailyImageUseCaseImpl: GetDailyImageUseCaseImpl
    ): GetDailyImageUseCase

}