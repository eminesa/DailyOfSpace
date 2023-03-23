package com.eminesa.dailyofspace.di

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.eminesa.dailyofspace.BuildConfig
import com.eminesa.dailyofspace.local_storage.KeyValueStore
import com.eminesa.dailyofspace.local_storage.SharedPreferencesKeyValueStore
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideGson() = Gson()

    @Singleton
    @Provides
    fun provideSharedPreferencesKeyValueStore(preferences: SharedPreferences): KeyValueStore =
        SharedPreferencesKeyValueStore(preferences)

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences =
        appContext.getSharedPreferences(
            BuildConfig.APPLICATION_ID,
            Activity.MODE_PRIVATE
        )

   /* @Provides
    @Singleton
    fun provideMovieDatabase(app: Application, gson: Gson): MovieDatabase {
        return Room.databaseBuilder(app, MovieDatabase::class.java, "movie_db")
            .addTypeConverter(DatabaseConverters(GsonParser(gson)))
            .fallbackToDestructiveMigration()
            .build()
    }

     @Provides
    @Singleton
    fun provideDao(db: MovieDatabase): MovieDao = db.dao

     @Provides
    @Singleton
    fun provideMovieMapper(): MovieMapper = MovieMapper()
*/

}