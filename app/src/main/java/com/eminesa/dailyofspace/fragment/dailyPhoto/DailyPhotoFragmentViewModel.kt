package com.eminesa.dailyofspace.fragment.dailyPhoto

import android.content.Context
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import android.widget.Toast
import com.eminesa.dailyofspace.clouddb.CloudDBManager
import com.eminesa.dailyofspace.clouddb.ObjPhoto
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@HiltViewModel
class DailyPhotoFragmentViewModel @Inject constructor(private val cloudDBManager: CloudDBManager) :
    ViewModel() {

    fun saveUser(spot: ObjPhoto, context: Context) {
        viewModelScope.launch {
            cloudDBManager.saveUser(spot) {
                if (it) {
                    Toast.makeText(context, "Saved succesfully", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private val spotListLiveData: MutableLiveData<MutableList<ObjPhoto>> = MutableLiveData()
    fun getSpotListLiveData(): LiveData<MutableList<ObjPhoto>> = spotListLiveData

    fun getSpots() {
        viewModelScope.launch {
            delay(TimeUnit.SECONDS.toMillis(2))
            cloudDBManager.getSpots {
                spotListLiveData.value = it
            }
        }
    }
}