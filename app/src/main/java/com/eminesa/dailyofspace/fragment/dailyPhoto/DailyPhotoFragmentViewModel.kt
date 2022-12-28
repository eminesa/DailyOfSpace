package com.eminesa.dailyofspace.fragment.dailyPhoto

import androidx.lifecycle.*
import com.eminesa.dailyofspace.response.GlobalResponse
import com.eminesa.dailyofspace.model.NasaByIdResponse
import com.eminesa.dailyofspace.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

import android.util.Log
import com.eminesa.dailyofspace.clouddb.CloudDBRepository
import com.eminesa.dailyofspace.clouddb.LoginListener
import com.eminesa.dailyofspace.clouddb.ObjPhoto

@HiltViewModel
class DailyPhotoFragmentViewModel @Inject constructor(private val repository: Repository, private val dbRepo: CloudDBRepository) : ViewModel() {

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

    fun saveUser(user: ObjPhoto){
        dbRepo.saveUser(user, object : LoginListener {
            override fun onSuccess(savedUser: ObjPhoto) {
                Log.i("USER",savedUser.userName)
            }

            override fun onFailure(e: Exception) {
                Log.i("ERROR",e.message.toString())
            }
        })
    }
}