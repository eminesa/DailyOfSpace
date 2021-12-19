package com.eminesa.dailyofspace.fragment.dailyPhoto

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.eminesa.dailyofspace.enum.ResponseStatus
import com.eminesa.dailyofspace.response.GlobalResponse
import com.eminesa.dailyofspace.model.NasaByIdResponse
import com.eminesa.dailyofspace.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import android.widget.Toast

import com.eminesa.dailyofspace.activity.MainActivity

import android.content.Intent

import android.content.BroadcastReceiver
import android.content.IntentFilter


@HiltViewModel
class DailyPhotoFragmentViewModel @Inject constructor(val repository: Repository) : ViewModel() {



    fun getDailyPhoto(key: String?): LiveData<GlobalResponse<out NasaByIdResponse>> =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {

            emit(GlobalResponse.loading(null))
            try {
                emit(GlobalResponse.success(data = repository.getDailyPhoto(key)))
            } catch (e: Exception) {
                emit(
                    GlobalResponse.error(
                        data = null, e.localizedMessage
                    )
                )
            }
        }
}