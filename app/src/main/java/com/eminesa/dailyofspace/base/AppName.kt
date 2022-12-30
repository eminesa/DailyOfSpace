package com.eminesa.dailyofspace.base

import android.app.Application
import com.huawei.agconnect.crash.AGConnectCrash
import com.yerli.sosyal.utils.storage.LocaleStorageManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppName : Application(){
    override fun onCreate() {
        super.onCreate()

        LocaleStorageManager.init(this)
        AGConnectCrash.getInstance().enableCrashCollection(true) //enable crash service

    }
}