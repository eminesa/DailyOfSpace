package com.eminesa.dailyofspace.clouddb

interface LoginListener{
    fun onSuccess(savedUser: ObjPhoto)
    fun onFailure(e: Exception)
}