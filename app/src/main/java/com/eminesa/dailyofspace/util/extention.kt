package com.eminesa.dailyofspace.util

fun String.getUserName(): String {
    return "@".plus(this)
}