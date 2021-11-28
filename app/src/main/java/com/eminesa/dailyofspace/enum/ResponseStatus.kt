package com.eminesa.dailyofspace.enum

/**
 * Bu enum class'ı tüm requestler için servisten dönen response'ların başarı durumlarının tiplerini içerir.
 */
enum class ResponseStatus{
    /**
     * Servisin yükleniyor olması durumu
     */
    LOADING,

    /**
     * Servisin hatalı olması durumu
     */
    ERROR,

    /**
     * Servisin başarılı olması durumu
     */
    SUCCESS
}
