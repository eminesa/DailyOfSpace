package com.eminesa.dailyofspace.di

import com.eminesa.dailyofspace.local_storage.KeyValueStore
import com.eminesa.dailyofspace.local_storage.LocalStorageService
import com.google.gson.Gson
  import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Singleton
    @Provides
    fun provideLocalStorageService(gson: Gson, keyValueStore: KeyValueStore) = LocalStorageService(gson, keyValueStore)
}
