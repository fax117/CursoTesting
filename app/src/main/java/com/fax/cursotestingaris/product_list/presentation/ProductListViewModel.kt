package com.fax.cursotestingaris.product_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fax.cursotestingaris.product_list.domain.models.Product
import com.fax.cursotestingaris.product_list.domain.models.SortOption
import com.fax.cursotestingaris.product_list.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductListUIState>(ProductListUIState.Loading)
    val uiState: StateFlow<ProductListUIState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<ProductListEvent>(extraBufferCapacity = 1)
    val events: SharedFlow<ProductListEvent> = _events.asSharedFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        _uiState.value = ProductListUIState.Loading
        getProductsUseCase().onEach { products: List<Product> ->
                val categories: List<String> = products.map { it.category }.distinct().sorted()
                _uiState.value = ProductListUIState.Success(
                    products = products,
                    categories = categories,
                    selectedCategory = null,
                    sortOption = SortOption.NONE
                )
            }.catch { error ->
                _uiState.value = ProductListUIState.Error(error.message.orEmpty())
            }.launchIn(viewModelScope)
    }

    fun setCategory(category: String?) {
        // Send to database
        viewModelScope.launch {
            //Llamar settings Repository
        }
    }

    fun setSortOption(sortOption: SortOption) {
        // Send to database
        viewModelScope.launch {
            //Llamar settings Repository
        }
    }

}