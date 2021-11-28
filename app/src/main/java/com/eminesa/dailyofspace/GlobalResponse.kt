package com.eminesa.dailyofspace

import android.os.Parcelable
import com.eminesa.dailyofspace.enum.ResponseStatus
import kotlinx.parcelize.Parcelize

/**
 * Bu data class içerisinde bulunan funksiyonları dinler ve dönen değere göre sonuç üreten response modelidir.
 * success fonksiyonu servisin başarılı olması durumunda çağrılır.
 * error fonksiyonu servisin hatalı olması durumunda çağrılır.
 * loading fonksiyonu servisin yükleniyor olması durumunda çağrılır.
 */
@Parcelize
data class GlobalResponse<T: Parcelable>(var status: ResponseStatus, var    data: T?, var message: String?): Parcelable {

    companion object{
        fun<T: Parcelable> success(data:T?) = GlobalResponse(status= ResponseStatus.SUCCESS,data = data,message = null)
        fun<T: Parcelable> error(data:T?,message: String?)= GlobalResponse(status= ResponseStatus.ERROR,data = data,message = message)
        fun<T: Parcelable> loading(data:T?)= GlobalResponse(status= ResponseStatus.LOADING,data = data,message = null)
    }
}
