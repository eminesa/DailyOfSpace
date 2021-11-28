package com.eminesa.dailyofspace.network

class Repository(private val apiService: ApiService) {

    suspend fun getDailyPhoto(
        nameSurname: String
    ) = apiService.getDailyPhoto(nameSurname)
}