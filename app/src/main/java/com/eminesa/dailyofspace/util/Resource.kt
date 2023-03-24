package com.eminesa.dailyofspace.util

/**
 *Sealed classlar, birbirleriyle ilişkili bir grup alt sınıfı bir araya getirir ve bu alt sınıfların tümünü kapsayan bir tür tanımlarlar.
 *Sealed classlar, özellikle durum yönetimi (state management) gibi senaryolarda kullanışlıdır.
 *Bir sealed class içinde, alt sınıflar belirli bir durumu temsil ederler ve bu duruma özgü davranışları uygularlar.

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
