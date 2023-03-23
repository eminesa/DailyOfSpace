package com.eminesa.dailyofspace.use_case

import com.eminesa.dailyofspace.R
import com.eminesa.dailyofspace.extentions.handleError
import com.eminesa.dailyofspace.model.DailyImage
import com.eminesa.dailyofspace.network.ImageRepository
import com.eminesa.dailyofspace.util.Resource
import com.eminesa.dailyofspace.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDailyImageUseCaseImpl @Inject constructor(
    private val repository: ImageRepository,
) : GetDailyImageUseCase {
    override suspend fun getDailyImage(key: String?): Flow<Resource<DailyImage>> = flow {
        try {
            emit(Resource.Loading())

            val response = repository.getDailyPhoto(key = key)

            emit(Resource.Success(data = response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.handleError()))
        } catch (e: IOException) {
            emit(Resource.Error(UiText.StringResource(R.string.couldnt_reach_server_error)))
        }
    }

}