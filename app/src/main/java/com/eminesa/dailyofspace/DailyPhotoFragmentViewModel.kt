package com.eminesa.dailyofspace

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.eminesa.dailyofspace.model.NasaByIdResponse
import com.eminesa.dailyofspace.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class DailyPhotoFragmentViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    fun getDailyPhoto(platform: String): LiveData<GlobalResponse<out NasaByIdResponse>> =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {

            emit(GlobalResponse.loading(null))
            try {
                emit(GlobalResponse.success(data = repository.getDailyPhoto(platform)))
            } catch (e: Exception) {
                emit(
                    GlobalResponse.error(
                        data = null, e.localizedMessage
                    )
                )
            }

        }
}