package com.eminesa.dailyofspace.di

import android.content.Context
import android.util.Log
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import com.eminesa.dailyofspace.BuildConfig
import com.eminesa.dailyofspace.network.ApiService
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/*
@Module annotasyonuyla bağımlılıkları sağladığımız yani nesneleri oluşturduğumuz sınıflarımızın en başına eklenir.

@InstallIn annotasyonu ise modulün hangi component üzerinde bulunmasını istediğimizi belirtiriz. Componentlerin bir lifecycle’ı bulunmaktadır.
SingletonComponent bileşeni, @InstallIn anotasyonu kullanılarak belirtilir
ve uygulama yaşam döngüsü boyunca tek bir örnekte kalacak şekilde tasarlanmıştır.
Bu bileşenin amacı, uygulamanın herhangi bir yerinde kullanılabilecek tüm bağımlılıkları sağlamak
ve uygulamanın genel performansını artırmaktır.

Singleton bileşenindeki bağımlılıklar, uygulamanın farklı yerlerinde kullanılabilir.
Örneğin, bir uygulamanın farklı aktivitelerinde veya farklı fragmanlarda aynı veritabanı erişim katmanını kullanması
gerekebilir. Bu durumda, SingletonComponent bileşeni kullanılarak veritabanı bağımlılığı bir kez oluşturulabilir
ve daha sonra tüm aktiviteler veya fragmanlar tarafından @Inject annotasyonuyla kullanılabilir.
* */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    /**
     * Bu fonksiyon yapılandırılmış değerler ile CertificatePinner instance oluşturur ve döndürür.
     * @return CertificatePinner
     */
    @Singleton
    @Provides //@Provides annotasyonu modullerde nesneleri bağımlılık olarak tanımlamak için oluşturduğumuz methodların üzerinde kullanılır.
    fun certificatePinner() = CertificatePinner.Builder()
        .add("", "")// istenen SHA eklenir
        .build()


    /**
     * Bu fonksiyon yapılandırılmış değerler ile Retrofit instance oluşturur ve döndürür.
     * @param okHttpClient request oluşturmak için kullanılır.
     * @return Retrofit
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build().create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()
        .readTimeout(20, TimeUnit.SECONDS)
        .connectTimeout(20, TimeUnit.SECONDS)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: LoggingInterceptor,
        chuckerInterceptor: ChuckerInterceptor,
        builder: OkHttpClient.Builder
    ): OkHttpClient {
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(httpLoggingInterceptor)
            builder.addInterceptor(chuckerInterceptor)
        }
        builder.addInterceptor(
            Interceptor { chain ->
                val original = chain.request()
                val url = original.url.newBuilder().addQueryParameter("api_key", BuildConfig.API_KEY).build()
                val requestBuilder = original.newBuilder().url(url)
                chain.proceed(requestBuilder.build())
            },
        )
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): LoggingInterceptor =
        LoggingInterceptor.Builder().setLevel(Level.BODY).log(Log.VERBOSE).build()

    @Singleton
    @Provides
    fun provideChuckerInterceptor(@ApplicationContext appContext: Context): ChuckerInterceptor =
        ChuckerInterceptor.Builder(appContext)
            .collector(ChuckerCollector(appContext))
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()
}
