package com.eminesa.dailyofspace.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class NasaByIdResponse(

    @field:Json(name = "date")
    val date: String? = null,

    @field:Json(name = "explanation")
     val explanation: String? = null,

    @field:Json(name = "title")
    val title: String? = null,

    @field:Json(name = "media_type")
    val media_type: String? = null,

    @field:Json(name = "service_version")
    val service_version: String? = null,

    @field:Json(name = "url")
    val url: String? = null,

): Parcelable