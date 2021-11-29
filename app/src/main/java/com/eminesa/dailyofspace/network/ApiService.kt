package com.eminesa.dailyofspace.network

import com.eminesa.dailyofspace.model.NasaByIdResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    //https://api.nasa.gov/planetary/apod?api_key=da2QThPK0PiunpjTKv6NUE67Od0L8E78ntl3GuOR
    @GET("apod")
    suspend fun getDailyPhoto(@Query("api_key") api_key: String): NasaByIdResponse
}