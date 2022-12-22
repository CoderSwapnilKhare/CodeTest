package com.rm.averagepriceapp.data.remote.dto

import com.rm.averagepriceapp.domain.model.Properties
import com.squareup.moshi.Json

data class Property(
    @Json(name = "id") val id: Int,
    @Json(name = "price") val price: Int,
    @Json(name = "bedrooms") val bedrooms: Int,
    @Json(name = "bathrooms") val bathrooms: Int,
    @Json(name = "number") val number: String,
    @Json(name = "address") val address: String,
    @Json(name = "region") val region: String,
    @Json(name = "postcode") val postcode: String,
    @Json(name = "propertyType") val propertyType: String
)

fun Property.toProperty(): Properties {
    return Properties(
        price = price
    )
}