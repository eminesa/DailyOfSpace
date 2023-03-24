package com.eminesa.dailyofspace.presenters.dailyPhoto

import com.eminesa.dailyofspace.model.DailyImage
import com.eminesa.dailyofspace.util.UiText


sealed class DailyImageViewState {
    object Init : DailyImageViewState()
    data class Loading(val isLoading: Boolean) : DailyImageViewState()
    data class Success(val data: DailyImage) : DailyImageViewState()
    object SuccessWithEmptyData : DailyImageViewState()
    data class Error(val error: UiText) : DailyImageViewState()
}