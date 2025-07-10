package com.eminesa.photoofspace.use_case

import com.eminesa.photoofspace.R
import com.eminesa.photoofspace.extentions.handleError
import com.eminesa.photoofspace.model.DailyImage
import com.eminesa.photoofspace.network.ImageRepository
import com.eminesa.photoofspace.util.Resource
import com.eminesa.photoofspace.util.UiText
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