package com.eminesa.dailyofspace.base

import android.app.Application
import com.yerli.sosyal.utils.storage.LocaleStorageManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppName : Application(){
    override fun onCreate() {
        super.onCreate()

        LocaleStorageManager.init(this)

    }
}