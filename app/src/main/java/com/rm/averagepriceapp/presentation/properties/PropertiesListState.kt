package com.rm.averagepriceapp.presentation.properties

sealed class PropertiesListState {
    data class Loading(var isLoading: Boolean) : PropertiesListState()
    data class Error(var errorMessage: String) : PropertiesListState()
    data class Success(var data: Any) : PropertiesListState()
}
