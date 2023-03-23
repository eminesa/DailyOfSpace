package com.eminesa.dailyofspace.network

import com.eminesa.dailyofspace.model.DailyImage


interface ImageRepository {

    suspend fun getDailyPhoto(key: String?) : DailyImage

}