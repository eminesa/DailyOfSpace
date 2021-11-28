package com.eminesa.dailyofspace.network

import com.eminesa.dailyofspace.network.ApiModule.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Bu değişken yapılandırılmış değerler ile OkHttpClient.Builder instance'dir.
 */

private fun getClient(): OkHttpClient.Builder{
    return OkHttpClient.Builder()
        .readTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .addNetworkInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.NONE
        })
}

/**
 * Bu değişken yapılandırılmış değerler ile Moshi instance'dir.
 */
val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Bu değişken yapılandırılmış değerler ile Retrofit instance'dir.
 */
private fun getRetrofit(): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .client(getClient().build())
        .build()
}

/**
 * Bu değişken yapılandırılmış değerler ile ApiService instance'dir.
 */
fun getApiService(): ApiService{
    return getRetrofit().create(ApiService::class.java)
}


