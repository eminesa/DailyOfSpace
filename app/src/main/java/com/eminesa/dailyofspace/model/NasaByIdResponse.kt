package com.eminesa.dailyofspace.model

import android.os.Parcelable
import com.squareup.moshi.Json
import java.util.*
import kotlinx.parcelize.Parcelize

@Parcelize
data class NasaByIdResponse(

    @field:Json(name = "date")
    val date: String? = null,

    @field:Json(name = "explanation")
     val explanation: String? = null,

    @field:Json(name = "title")
    val title: String? = null,

    @field:Json(name = "url")
    val url: String? = null,

): Parcelable