package com.rm.averagepriceapp.presentation.properties

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rm.averagepriceapp.common.Resource
import com.rm.averagepriceapp.domain.model.Properties
import com.rm.averagepriceapp.domain.usecase.PropertiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PropertiesViewModel @Inject constructor(private val propertiesUseCase: PropertiesUseCase) : ViewModel() {

    private val _state = MutableLiveData<PropertiesListState>()
    val viewState: LiveData<PropertiesListState> = _state

    init {
        getProperties()
    }

    private fun getProperties() {
        propertiesUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = PropertiesListState.Loading(isLoading = true)
                }
                is Resource.Success -> {
                    val propertiesData = result.data ?: emptyList()
                    _state.value = PropertiesListState.Success(data = propertiesAveragePrice(propertiesData))
                }
                is Resource.Error -> {
                    _state.value = PropertiesListState.Error(
                        errorMessage = result.message ?: "An unexpected error occured"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun propertiesAveragePrice(properties: List<Properties>): Double {
        var totalPrice = 0.0
        properties.forEach { totalPrice += it.price }
        return totalPrice / properties.size
    }
}