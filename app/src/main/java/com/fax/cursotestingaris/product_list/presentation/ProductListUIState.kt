package com.fax.cursotestingaris.product_list.presentation

import com.fax.cursotestingaris.product_list.domain.models.Product

sealed class ProductListUIState {
    data object Loading : ProductListUIState()
    data class Error(val message: String) : ProductListUIState()
    data class Success(
        val products: List<Product>,
//        val categories: List<Categories>,
//        val selectedCategory: String,
//        val sortOption: String
    ) : ProductListUIState()
}