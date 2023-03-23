package com.eminesa.dailyofspace.use_case

import com.eminesa.dailyofspace.model.DailyImage
import com.eminesa.dailyofspace.util.Resource
import kotlinx.coroutines.flow.Flow

interface GetDailyImageUseCase {

    suspend fun getDailyImage(key: String?): Flow<Resource<DailyImage>>

}