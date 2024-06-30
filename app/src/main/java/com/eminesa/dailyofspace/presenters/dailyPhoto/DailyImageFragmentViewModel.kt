package com.eminesa.dailyofspace.presenters.dailyPhoto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eminesa.dailyofspace.use_case.GetDailyImageUseCase
import com.eminesa.dailyofspace.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyImageFragmentViewModel @Inject constructor(
    private val getDailyImageUseCase: GetDailyImageUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<DailyImageViewState>(DailyImageViewState.Init)
    fun getViewState(): StateFlow<DailyImageViewState> = _state.asStateFlow()

    fun setLoading(isLoading: Boolean) {
        _state.value = DailyImageViewState.Loading(isLoading)
    }

    fun getDailyImage(key: String?) {
        viewModelScope.launch {
            getDailyImageUseCase.getDailyImage(key).onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        setLoading(false)
                        _state.value = DailyImageViewState.Error(result.message)
                    }

                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        setLoading(false)
                        if (result.data == null) {
                            _state.value = DailyImageViewState.SuccessWithEmptyData
                        } else {
                            _state.value = DailyImageViewState.Success(result.data)
                        }
                    }
                }
            }.launchIn(this)
        }
    }

}