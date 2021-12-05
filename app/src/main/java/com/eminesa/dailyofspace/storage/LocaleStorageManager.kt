package com.yerli.sosyal.utils.storage

import android.content.Context
import android.content.SharedPreferences
import java.io.File

/**
 * Bu class Shared Preferences işlemlerini gerçekleştireceğimiz fonksiyonları içerir.
 */
object LocaleStorageManager {

    private lateinit var prefs: SharedPreferences
    private lateinit var prefsEditor: SharedPreferences.Editor

    private const val PREFS_NAME = "daily_of_space"

    /**
     * Bu fonksiyon shared preferences instance oluşturur.
     */
    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Bu fonksiyon shared preferences'de String tipinde bir değer tutmamızı sağlar.
     * @param prefName tutulacak değerin key değeri
     * @param prefValue tutulacak olan değer.
     */
    fun setPreferences(prefName: String, prefValue: String?) {
        prefsEditor = prefs.edit()
        with(prefsEditor) {
            if (prefValue == null) {
                putString(prefName, null)
            } else {
           //     putString(prefName, CryptHelper.instance?.aesEncrypt(prefValue)) radle  ici
                putString(prefName,prefValue)
            }
            commit()
        }
    }

    /**
     * Bu fonksiyon shared preferences'de Int tipinde bir değer tutmamızı sağlar.
     * @param prefName tutulacak değerin key değeri
     * @param prefValue tutulacak olan değer.
     */
    fun setPreferences(prefName: String, prefValue: Int) {
        prefsEditor = prefs.edit()
        with(prefsEditor) {
            putInt(prefName, prefValue)
            commit()
        }
    }

    /**
     * Bu fonksiyon shared preferences'de Boolean tipinde bir değer tutmamızı sağlar.
     * @param prefName tutulacak değerin key değeri
     * @param prefValue tutulacak olan değer.
     */
    fun setPreferences(prefName: String, prefValue: Boolean) {
        prefsEditor = prefs.edit()
        with(prefsEditor) {
            putBoolean(prefName, prefValue)
            commit()
        }
    }

    /**
     * Bu fonksiyon shared preferences'de String tipinde tutulan değeri döndürür
     * @param prefName tutulan değerin key değeri
     * @return shared preferences'de tutulan String değeri
     */
    fun getPreferencesStrVal(prefName: String): String? {
        var result = prefs.getString(prefName, "")
        if (result != null) {
            result = result
        }
        return result
    }

    /**
     * Bu fonksiyon shared preferences'de Boolean tipinde tutulan değeri döndürür
     * @param prefName tutulan değerin key değeri
     * @return shared preferences'de tutulan Boolean değeri
     */
    fun getPreferencesBoolVal(prefName: String): Boolean {
        return prefs.getBoolean(prefName, false)
    }

    /**
     * Bu fonksiyon shared preferences'de Int tipinde tutulan değeri döndürür
     * @param prefName tutulan değerin key değeri
     * @return shared preferences'de tutulan Int değeri
     */
    fun getPreferencesIntVal(prefName: String): Int {
        return prefs.getInt(prefName, 0)
    }

    /**
     * Bu fonksiyon cache'de tutulan post için oluşturulmuş verileri temizler.
     * @param context external cache'e ulaşım sağlamak için.
     */
    fun deleteCachePhotos(context: Context) {
        val file = File(context.externalCacheDir.toString() + "/galleryCache/")
        val files: Array<String> = file.list() ?: emptyArray()
        for (i in files.indices) {
            val myFile = File(file, files[i])
            myFile.delete()
        }
    }


}