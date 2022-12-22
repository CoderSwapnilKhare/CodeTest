package com.rm.averagepriceapp.domain.usecase

import com.rm.averagepriceapp.common.Resource
import com.rm.averagepriceapp.data.remote.dto.toProperty
import com.rm.averagepriceapp.domain.model.Properties
import com.rm.averagepriceapp.domain.repository.PropertiesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PropertiesUseCase @Inject constructor(private val propertiesRepository: PropertiesRepository) {
    operator fun invoke(): Flow<Resource<List<Properties>>> = flow {
        try {
            emit(Resource.Loading())
            val properties = propertiesRepository.getPropertiesList().properties.map { it.toProperty() }
            emit(Resource.Success(properties))
        } catch (e: NullPointerException) {
            emit(Resource.Error("NullPointerException"))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}