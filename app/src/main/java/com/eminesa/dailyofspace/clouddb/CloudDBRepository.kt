package com.eminesa.dailyofspace.clouddb

import javax.inject.Inject

class CloudDBRepository @Inject constructor(
    private val cloudDBManager: CloudDBManager
) {

    fun saveUser(user: ObjPhoto, loginListener: LoginListener) {
        cloudDBManager.saveUser(user, loginListener)
    }
}