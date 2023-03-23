package com.eminesa.dailyofspace.util

/**
 * Bu sealed class'ı tüm requestler için servisten dönen response'ların bulundukları durumlarının her birini clas olarak içerir.
 */
sealed class Resource<T>(val data: T? = null, val message: UiText = UiText.DynamicString("")) {

    /**
     * Servisin yükleniyor olması durumu
     */
    class Loading<T>(data: T? = null) : Resource<T>(data)

    /**
     * Servisin başarılı olması durumu
     */
    class Success<T>(data: T?) : Resource<T>(data)

    /**
     * Servisin hatalı olması durumu
     */
    class Error<T>(message: UiText, data: T? = null) : Resource<T>(data, message)
}
