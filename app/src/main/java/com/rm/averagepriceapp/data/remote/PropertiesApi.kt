package com.rm.averagepriceapp.data.remote

import com.rm.averagepriceapp.data.remote.dto.PropertiesDto
import retrofit2.http.GET

interface PropertiesApi {
    @GET("properties.json")
    suspend fun getProperties(): PropertiesDto
}