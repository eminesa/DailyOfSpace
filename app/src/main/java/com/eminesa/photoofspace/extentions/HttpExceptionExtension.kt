package com.eminesa.photoofspace.extentions

import com.eminesa.photoofspace.R
import com.eminesa.photoofspace.model.ErrorDto
import com.eminesa.photoofspace.model.toErrorModel
import com.eminesa.photoofspace.util.UiText
import com.google.gson.Gson
import retrofit2.HttpException

val gson = Gson()

@Synchronized
fun HttpException.handleError(): UiText {
    val errorString = this.response()?.errorBody()?.string()
    errorString?.let {
        val errorModel = gson.fromJson(errorString, ErrorDto::class.java)?.toErrorModel()
        if (errorModel?.error != null)
            return UiText.DynamicString(errorModel.error)
        else
            return UiText.StringResource(R.string.unexpectedError)
    }
    return this.localizedMessage?.let { UiText.DynamicString(it) }
        ?: UiText.StringResource(R.string.unexpectedError)
}
