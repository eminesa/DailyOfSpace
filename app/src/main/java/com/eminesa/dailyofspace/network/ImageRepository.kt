package com.eminesa.dailyofspace.network

import com.eminesa.dailyofspace.model.DailyImage
import com.eminesa.dailyofspace.util.Resource
import kotlinx.coroutines.flow.Flow


interface ImageRepository {

    suspend fun getDailyPhoto(key: String?) : DailyImage

}