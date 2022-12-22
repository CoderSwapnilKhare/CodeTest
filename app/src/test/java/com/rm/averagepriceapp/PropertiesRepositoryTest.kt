package com.rm.averagepriceapp

import com.rm.averagepriceapp.data.remote.PropertiesApi
import com.rm.averagepriceapp.data.remote.dto.PropertiesDto
import com.rm.averagepriceapp.data.remote.dto.Property
import com.rm.averagepriceapp.data.repository.PropertiesRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class PropertiesRepositoryTest {
    private val dispatcher = UnconfinedTestDispatcher()
    private val mockPropertiesApi: PropertiesApi = mock()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun repoReturnSuccess() {
        val propertiesDto = getPropertiesDto()
        runTest {
            whenever(mockPropertiesApi.getProperties()).thenReturn(propertiesDto)
            val propertiesRepository = PropertiesRepositoryImpl(mockPropertiesApi)

            val propertiesData = propertiesRepository.getPropertiesList()

            assertEquals(propertiesData.properties.size, propertiesDto.properties.size)
            assertEquals(propertiesData.properties.first().id, propertiesDto.properties.first().id)
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