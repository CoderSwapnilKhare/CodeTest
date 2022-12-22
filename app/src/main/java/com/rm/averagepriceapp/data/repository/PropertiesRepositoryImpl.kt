package com.rm.averagepriceapp.data.repository

import com.rm.averagepriceapp.data.remote.PropertiesApi
import com.rm.averagepriceapp.data.remote.dto.PropertiesDto
import com.rm.averagepriceapp.domain.repository.PropertiesRepository
import javax.inject.Inject

class PropertiesRepositoryImpl @Inject constructor(private val propertiesApi: PropertiesApi) : PropertiesRepository {
    override suspend fun getPropertiesList(): PropertiesDto {
       return propertiesApi.getProperties()
    }
}