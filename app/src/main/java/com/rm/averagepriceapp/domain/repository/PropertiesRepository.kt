package com.rm.averagepriceapp.domain.repository

import com.rm.averagepriceapp.data.remote.dto.PropertiesDto

interface PropertiesRepository {
    suspend fun getPropertiesList(): PropertiesDto
}
