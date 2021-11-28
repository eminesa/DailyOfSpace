package com.eminesa.dailyofspace.network

import androidx.viewbinding.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    val BASE_URL = "https://images-api.nasa.gov"


    /**
     * Bu fonksiyon yapılandırılmış değerler ile CertificatePinner instance oluşturur ve döndürür.
     * @return CertificatePinner
     */
    @Singleton
    @Provides
    fun certificatePinner() = CertificatePinner.Builder()
        .build()

    /**
     * Bu fonksiyon yapılandırılmış değerler ile OkHttpClient instance oluşturur ve döndürür.
     * @param certificatePinner hangi sertifikaların güvenilir olduğunu sınırlayan sertifika pinner'ı ayarlar.
     * @param interceptor DynamicInterceptor
     * @return OkHttpClient
     */
    @Singleton
    @Provides
    fun getClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
           // .certificatePinner(certificatePinner)
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BODY
                else
                    HttpLoggingInterceptor.Level.NONE
            })
            .build()
    }

    /**
     * Bu fonksiyon yapılandırılmış değerler ile Retrofit instance oluşturur ve döndürür.
     * @param okHttpClient request oluşturmak için kullanılır.
     * @return Retrofit
     */
    @Singleton
    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
            ))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    /**
     * Bu fonksiyon yapılandırılmış değerler ile ApiService instance oluşturur ve döndürür.
     * @param retrofit ApiService instance oluşturmak için kullanılır.
     * @return Retrofit
     */
    @Singleton
    @Provides
    fun getApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    /**
     * Bu fonksiyon yapılandırılmış değerler ile Repository instance oluşturur ve döndürür.
     * @param apiService Repository parametresi
     * @return Retrofit
     */
    @Singleton
    @Provides
    fun providesRepository(apiService: ApiService) = Repository(apiService)
}