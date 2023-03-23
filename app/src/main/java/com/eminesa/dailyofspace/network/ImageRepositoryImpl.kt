package com.eminesa.dailyofspace.network

import com.eminesa.dailyofspace.model.DailyImage
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val api: ApiService,
    // private val dao: ImageDao,
    // private val mapper: ImageMapper
) :
    ImageRepository {
    override suspend fun getDailyPhoto(key: String?): DailyImage = api.getDailyPhoto(key)
}
