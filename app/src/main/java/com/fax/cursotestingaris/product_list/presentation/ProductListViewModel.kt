package com.fax.cursotestingaris.product_list.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class ProductListViewModel @Inject constructor() : ViewModel() {

    private val _usState = MutableStateFlow<ProductListUIState>(ProductListUIState.Loading)
    val uiState : StateFlow<ProductListUIState> = _usState.asStateFlow()

    private val _events = MutableSharedFlow<ProductListEvent>(extraBufferCapacity = 1)
    val events : SharedFlow<ProductListEvent> = _events.asSharedFlow()

    init {
        loadProducts()
    }

    fun loadProducts(){
        _usState.value = ProductListUIState.Loading



    }

}