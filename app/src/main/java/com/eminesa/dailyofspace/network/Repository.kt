package com.eminesa.dailyofspace.network

class Repository(private val apiService: ApiService) {

    suspend fun getDailyPhoto(key: String?) = apiService.getDailyPhoto(key)

}