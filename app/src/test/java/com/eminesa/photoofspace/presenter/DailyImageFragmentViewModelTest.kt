package com.eminesa.photoofspace.presenter

import com.eminesa.photoofspace.BuildConfig
import com.eminesa.photoofspace.presenters.dailyPhoto.DailyImageFragmentViewModel
import com.eminesa.photoofspace.presenters.dailyPhoto.DailyImageViewState
import com.eminesa.photoofspace.use_case.GetDailyImageUseCase
import com.eminesa.photoofspace.util.Resource
import com.eminesa.photoofspace.util.UiText
import com.eminesa.photoofspace.utils.MockHelper
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DailyImageFragmentViewModelTest {

    private val key: String = BuildConfig.API_KEY
    private val errorMessage: String = "error"

    @Mock
    private lateinit var getDailyImageUseCase: GetDailyImageUseCase

    private lateinit var viewModel: DailyImageFragmentViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = DailyImageFragmentViewModel(getDailyImageUseCase)
    }

    @Test
    fun `getDailyImageUseCase emits success`() = runTest {
        whenever(getDailyImageUseCase.getDailyImage(any())).thenAnswer {
            flow { emit(Resource.Success(data = MockHelper.dailyImage)) }
        }

        viewModel.getDailyImage(key)
        val currentState = viewModel.getViewState()
        assertThat(currentState.value).isInstanceOf(DailyImageViewState.Success::class.java)
    }

    @Test
    fun `getDailyImageCase emits error`() = runTest {
        whenever(getDailyImageUseCase.getDailyImage(any())).thenAnswer {
            flow<Resource<Any>> { emit(Resource.Error(message = UiText.DynamicString(value = errorMessage))) }
        }
        viewModel.getDailyImage(key)
        val currentState = viewModel.getViewState()
        assertThat(currentState.value).isInstanceOf(DailyImageViewState.Error::class.java)
    }

    @Test
    fun `getDailyImageUseCase emits loading`() = runTest {
        whenever(getDailyImageUseCase.getDailyImage(any())).thenAnswer {
            flow<Resource<Any>> { emit(Resource.Loading()) }
        }
        viewModel.getDailyImage(key)
        val currentState = viewModel.getViewState()
        assertThat(currentState.value).isInstanceOf(DailyImageViewState.Loading::class.java)
    }

    @Test
    fun `verify getDailyImageUseCase called with correct parameter`() = runTest {
        whenever(getDailyImageUseCase.getDailyImage(any())).thenAnswer {
            flow<Resource<Any>> { emit(Resource.Loading()) }
        }
        viewModel.getDailyImage(key)
        verify(getDailyImageUseCase).getDailyImage(eq(key))
    }

    @Test
    fun `verify setLoading function called with isLoading=true `() = runTest {
        viewModel.setLoading(true)
        val currentState = viewModel.getViewState()
        assertThat(currentState.value).isInstanceOf(DailyImageViewState.Loading::class.java)
        val loadingState = currentState.value as DailyImageViewState.Loading
        assertThat(loadingState.isLoading).isEqualTo(true)
    }

    @Test
    fun `verify setLoading function called with isLoading=false `() = runTest {
        viewModel.setLoading(false)
        val currentState = viewModel.getViewState()
        assertThat(currentState.value).isInstanceOf(DailyImageViewState.Loading::class.java)
        val loadingState = currentState.value as DailyImageViewState.Loading
        assertThat(loadingState.isLoading).isEqualTo(false)
    }

    @After
    fun tearDown() = Dispatchers.resetMain()

}