package com.eminesa.photoofspace.use_case

import com.eminesa.photoofspace.model.DailyImage
import com.eminesa.photoofspace.util.Resource
import kotlinx.coroutines.flow.Flow

interface GetDailyImageUseCase {

    suspend fun getDailyImage(key: String?): Flow<Resource<DailyImage>>

}