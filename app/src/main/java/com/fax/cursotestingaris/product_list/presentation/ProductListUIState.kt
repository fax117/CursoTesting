package com.fax.cursotestingaris.product_list.presentation

import com.fax.cursotestingaris.product_list.domain.models.Product
import com.fax.cursotestingaris.product_list.domain.models.ProductWithPromotion
import com.fax.cursotestingaris.product_list.domain.models.SortOption

sealed class ProductListUIState {
    data object Loading : ProductListUIState()
    data class Error(val message: String) : ProductListUIState()
    data class Success(
        val products: List<ProductWithPromotion>,
        val categories: List<String>,
        val selectedCategory: String?,
        val sortOption: SortOption
    ) : ProductListUIState()
}
