package com.eminesa.photoofspace.utils

import com.eminesa.photoofspace.model.DailyImage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class MockHelper {
    companion object {
        private const val errorJson = "{\"error\":\"\"}"

        val ioException = IOException()
        val dailyImage = DailyImage(
            copyright = "",
            date = "",
            explanation = "",
            title = "",
            hdUrl = "",
            mediaType = "",
            serviceVersion = "",
            url = ""
        )


        fun getHttpException(): HttpException {
            return HttpException(
                Response.error<ResponseBody>(
                    500,
                    errorJson.toResponseBody("plain/text".toMediaTypeOrNull())
                )
            )
        }
    }
}
