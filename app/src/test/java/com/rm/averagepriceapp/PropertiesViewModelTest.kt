package com.rm.averagepriceapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rm.averagepriceapp.data.remote.dto.PropertiesDto
import com.rm.averagepriceapp.data.remote.dto.Property
import com.rm.averagepriceapp.domain.repository.PropertiesRepository
import com.rm.averagepriceapp.domain.usecase.PropertiesUseCase
import com.rm.averagepriceapp.presentation.properties.PropertiesListState
import com.rm.averagepriceapp.presentation.properties.PropertiesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.junit.MockitoJUnit
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class PropertiesViewModelTest {

    @Rule
    @JvmField
    var mockRule = MockitoJUnit.rule()

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    private val dispatcher = UnconfinedTestDispatcher()
    private lateinit var propertiesViewModel: PropertiesViewModel
    private val mockPropertiesRepository: PropertiesRepository = mock()
    private lateinit var propertiesUseCase: PropertiesUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        propertiesUseCase = PropertiesUseCase(mockPropertiesRepository)
        propertiesViewModel = PropertiesViewModel(propertiesUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testLoadingState() {
        runBlocking {
            whenever(mockPropertiesRepository.getPropertiesList()).thenReturn(any())
            propertiesViewModel = PropertiesViewModel(PropertiesUseCase(mockPropertiesRepository))
            propertiesViewModel.viewState.observeForever {
                assertTrue(it is PropertiesListState.Loading)
            }
        }
    }

    @Test
    fun isSuccess() {
        val properties = getPropertiesDto()
        runBlocking {
            whenever(mockPropertiesRepository.getPropertiesList()).thenReturn(properties)
            propertiesViewModel = PropertiesViewModel(PropertiesUseCase(mockPropertiesRepository))
            propertiesViewModel.viewState.observeForever {
                assertTrue(it is PropertiesListState.Success)
            }
        }
    }

    @Test
    fun isFail() {
        runBlocking {
            whenever(mockPropertiesRepository.getPropertiesList()).thenReturn(null)
            propertiesViewModel = PropertiesViewModel(PropertiesUseCase(mockPropertiesRepository))
            propertiesViewModel.viewState.observeForever {
                assertTrue(it is PropertiesListState.Error)
            }
        }
    }

    @Test
    fun testPropertiesAveragePrice() {
        val properties = getPropertiesDto()
        runBlocking {
            whenever(mockPropertiesRepository.getPropertiesList()).thenReturn(properties)
            propertiesViewModel = PropertiesViewModel(PropertiesUseCase(mockPropertiesRepository))

            val stateFlow = propertiesViewModel.viewState
            assertEquals("Average of properties prices", stateFlow.value, PropertiesListState.Success(1000000.0))
        }
    }

    private fun getPropertiesDto(): PropertiesDto {
        return PropertiesDto(
            listOf(
                Property(
                    id = 1,
                    price = 1000000,
                    bedrooms = 7,
                    bathrooms = 2,
                    number = "12",
                    address = "Richard Lane",
                    region = "London",
                    postcode = "W1F 3FT",
                    propertyType = "DETACHED"
                )
            )
        )
    }
}