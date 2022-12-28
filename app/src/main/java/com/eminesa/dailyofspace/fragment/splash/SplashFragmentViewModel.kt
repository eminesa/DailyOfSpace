package com.eminesa.dailyofspace.fragment.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.eminesa.dailyofspace.model.NasaByIdResponse
import com.eminesa.dailyofspace.network.Repository
import com.eminesa.dailyofspace.response.GlobalResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class SplashFragmentViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

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