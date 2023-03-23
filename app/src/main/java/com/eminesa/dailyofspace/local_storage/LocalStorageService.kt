package com.eminesa.dailyofspace.local_storage

import com.eminesa.dailyofspace.BuildConfig
import com.google.gson.Gson
import javax.inject.Inject

class LocalStorageService @Inject constructor(
    private var gson: Gson,
    private val keyValueStore: KeyValueStore
) {

    fun <T> getObject(key: String, a: Class<T>?): T? {
        val data = keyValueStore.getString(key)
        return if (data == null) {
            null
        } else {
            try {
                gson.fromJson(data, a)
            } catch (e: Exception) {
                throw e
            }
        }
    }

    fun removeObject(key: String) = keyValueStore.remove(key)

    fun setPassIntro(isPassed: Boolean) = keyValueStore.setBool(BuildConfig.PASS_INTRO, isPassed)

    fun getPassIntro(): Boolean? = keyValueStore.getBool(BuildConfig.PASS_INTRO)

}
