package com.fax.cursotestingaris.product_detail.presentation

import com.fax.cursotestingaris.product_list.domain.models.ProductWithPromotion

data class ProductDetailUiState(
    val item: ProductWithPromotion? = null,
    val isLoading: Boolean = true,
)