package com.eminesa.photoofspace.network

import com.eminesa.photoofspace.model.DailyImage


interface ImageRepository {

    suspend fun getDailyPhoto(key: String?) : DailyImage

}