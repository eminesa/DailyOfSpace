package com.eminesa.photoofspace.network

import com.eminesa.photoofspace.model.DailyImage
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    //https://api.nasa.gov/planetary/apod?api_key=da2QThPK0PiunpjTKv6NUE67Od0L8E78ntl3GuOR
    @GET("apod")
    suspend fun getDailyPhoto(@Query("api_key") api_key: String?): DailyImage
}