package com.eminesa.photoofspace.use_cases

import com.eminesa.photoofspace.network.ImageRepository
import com.eminesa.photoofspace.use_case.GetDailyImageUseCase
import com.eminesa.photoofspace.use_case.GetDailyImageUseCaseImpl
import com.eminesa.photoofspace.util.Resource
import com.eminesa.photoofspace.util.UiText
import com.eminesa.photoofspace.utils.MockHelper
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