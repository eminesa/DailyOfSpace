package com.eminesa.dailyofspace.network

import com.eminesa.dailyofspace.GlobalResponse
import com.eminesa.dailyofspace.model.NasaByIdResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("apod?api_key")
    suspend fun getDailyPhoto(@Query("api_key") api_key: String): NasaByIdResponse
}