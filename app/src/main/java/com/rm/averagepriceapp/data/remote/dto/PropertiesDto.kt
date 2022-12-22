package com.rm.averagepriceapp.data.remote.dto

import com.squareup.moshi.Json

data class PropertiesDto(@Json(name = "properties") val properties: List<Property>)
