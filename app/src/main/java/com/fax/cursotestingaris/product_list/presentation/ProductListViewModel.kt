package com.fax.cursotestingaris.product_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fax.cursotestingaris.product_list.domain.models.ProductPromotion
import com.fax.cursotestingaris.product_list.domain.models.ProductWithPromotion
import com.fax.cursotestingaris.product_list.domain.models.SortOption
import com.fax.cursotestingaris.product_list.domain.repositories.SettingsRepository
import com.fax.cursotestingaris.product_list.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductListUIState>(ProductListUIState.Loading)
    val uiState: StateFlow<ProductListUIState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<ProductListEvent>(extraBufferCapacity = 1)
    val events: SharedFlow<ProductListEvent> = _events.asSharedFlow()

    val filtersVisible: StateFlow<Boolean> = settingsRepository.filtersVisible.stateIn(
        scope = viewModelScope,
        initialValue = true,
        started = SharingStarted.WhileSubscribed(5000)
    )

    private var productsJob: Job? = null

    init {
        loadProducts()
    }

    fun loadProducts() {
        _uiState.value = ProductListUIState.Loading
        productsJob?.cancel()
        productsJob = combine(
            flow = getProductsUseCase(),
            flow2 = settingsRepository.selectedCategory,
            flow3 = settingsRepository.sortOption
        ) { products, category, sortOption ->

            var filteredProducts = products
            if (category != null) {
                filteredProducts =
                    filteredProducts.filter { it.product.category == category }
            }

            val sorted = when (sortOption) {
                SortOption.PRICE_ASC -> filteredProducts.sortedBy { effectivePrice(it) }
                SortOption.PRICE_DESC -> filteredProducts.sortedByDescending { effectivePrice(it) }
                SortOption.NONE -> filteredProducts
                SortOption.DISCOUNT -> {
                    filteredProducts.sortedWith(
                        compareByDescending<ProductWithPromotion> { effectiveDiscountPercent(it) }
                            .thenBy {
                                it.promotion == null
                            }
                    )
                }
            }

            val categories: List<String> = products.map { it.product.category }.distinct().sorted()

            ProductListUIState.Success(
                products = sorted,
                categories = categories,
                selectedCategory = category,
                sortOption = sortOption
            )

        }.onEach { state ->
            _uiState.value = state
        }.catch { error ->
            _uiState.value = ProductListUIState.Error(error.message.orEmpty())
        }.launchIn(viewModelScope)

    }

    fun setCategory(category: String?) {
        // Send to database
        viewModelScope.launch {
            settingsRepository.setSelectedCategory(category)
        }
    }

    fun setSortOption(sortOption: SortOption) {
        // Send to database
        viewModelScope.launch {
            settingsRepository.setSortOption(sortOption)
        }
    }

    fun setFiltersVisible(visible: Boolean) {
        viewModelScope.launch {
            settingsRepository.setFiltersVisible(visible)
        }
    }

    private fun effectivePrice(item: ProductWithPromotion): Double {
        return when (val promo = item.promotion) {
            is ProductPromotion.Percent -> promo.discountedPrice
            else -> item.product.price
        }
    }

    private fun effectiveDiscountPercent(item: ProductWithPromotion): Double {
        return when (val promo = item.promotion) {
            is ProductPromotion.Percent -> promo.percent
            else -> 0.0
        }
    }

}