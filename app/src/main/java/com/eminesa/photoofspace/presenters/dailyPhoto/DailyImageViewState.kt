package com.eminesa.photoofspace.presenters.dailyPhoto

import com.eminesa.photoofspace.model.DailyImage
import com.eminesa.photoofspace.util.UiText


sealed class DailyImageViewState {
    object Init : DailyImageViewState()
    data class Loading(val isLoading: Boolean) : DailyImageViewState()
    data class Success(val data: DailyImage) : DailyImageViewState()
    object SuccessWithEmptyData : DailyImageViewState()
    data class Error(val error: UiText) : DailyImageViewState()
}