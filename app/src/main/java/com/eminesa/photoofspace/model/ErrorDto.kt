package com.eminesa.photoofspace.model


data class ErrorDto(val error: String?)

fun ErrorDto.toErrorModel(): ErrorModel = ErrorModel(error = error)
