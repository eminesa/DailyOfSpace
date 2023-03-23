package com.eminesa.dailyofspace.presenters.dailyPhoto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eminesa.dailyofspace.model.DailyImage
import com.eminesa.dailyofspace.use_case.GetDailyImageUseCase
import com.eminesa.dailyofspace.util.Resource
import com.eminesa.dailyofspace.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyImageFragmentViewModel @Inject constructor(
    private val getMoviesUseCase: GetDailyImageUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<HomeViewState>(HomeViewState.Init)
    fun getViewState(): StateFlow<HomeViewState> = _state.asStateFlow()

    private fun setLoading(isLoading: Boolean) {
        _state.value = HomeViewState.Loading(isLoading)
    }

    fun getDailyImage(key: String?) {
        viewModelScope.launch {
            getMoviesUseCase.getDailyImage(key).onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        setLoading(false)
                        _state.value = HomeViewState.Error(result.message)
                    }
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        setLoading(false)
                        if (result.data == null) {
                            _state.value = HomeViewState.SuccessWithEmptyData
                        } else {
                            _state.value = HomeViewState.Success(result.data)
                        }
                    }
                }
            }.launchIn(this)
        }
    }

}

sealed class HomeViewState {
    object Init : HomeViewState()
    data class Loading(val isLoading: Boolean) : HomeViewState()
    data class Success(val data: DailyImage) : HomeViewState()
    object SuccessWithEmptyData : HomeViewState()
    data class Error(val error: UiText) : HomeViewState()
}