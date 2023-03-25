package com.eminesa.dailyofspace.use_cases

import com.eminesa.dailyofspace.network.ImageRepository
import com.eminesa.dailyofspace.use_case.GetDailyImageUseCase
import com.eminesa.dailyofspace.use_case.GetDailyImageUseCaseImpl
import com.eminesa.dailyofspace.util.Resource
import com.eminesa.dailyofspace.util.UiText
import com.eminesa.dailyofspace.utils.MockHelper
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import com.google.common.truth.Truth.assertThat


@RunWith(MockitoJUnitRunner::class)
 class GetDailyImageUseCaseTest {


    private lateinit var getDailyImageUseCase: GetDailyImageUseCase

    @Mock
    private lateinit var imageRepository: ImageRepository

    //@Mock
    private val key: String = "" // sadece string cagırma durumunda mock kullanmak önerilen bir durum degildir

    @Before
    fun setUp() {
        getDailyImageUseCase = GetDailyImageUseCaseImpl(imageRepository)
    }

    @Test
    fun `check getDailyImage() success case`() = runBlocking {
        // when
        whenever(imageRepository.getDailyPhoto(key)).thenAnswer { MockHelper.dailyImage } // sahte bir response üretmek icin kullanıyoruz
        val result = getDailyImageUseCase.getDailyImage(key)
        val flowList = result.toList()
        // then
        assertThat(flowList[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(flowList[1]).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun `check getDailyImage() http exception error case`() = runBlocking {
        // when
        whenever(imageRepository.getDailyPhoto(key)).thenAnswer { throw MockHelper.getHttpException() }  // sahte bir response üretmek icin kullanıyoruz
        val result = getDailyImageUseCase.getDailyImage(key)
        val flowList = result.toList()
        // then
        assertThat(flowList[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(flowList[1]).isInstanceOf(Resource.Error::class.java)
        assertThat(flowList[1].message).isInstanceOf(UiText.DynamicString::class.java)
    }

    @Test
    fun `check getDailyImage() io exception error case`() = runBlocking {
        // when
        whenever(imageRepository.getDailyPhoto(key)).thenAnswer { throw MockHelper.ioException } // sahte bir response üretmek icin kullanıyoruz
        val result = getDailyImageUseCase.getDailyImage(key)
        val flowList = result.toList()
        // then

        assertThat(flowList[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(flowList[1]).isInstanceOf(Resource.Error::class.java)
        assertThat(flowList[1].message).isInstanceOf(UiText.StringResource::class.java)
    }

}