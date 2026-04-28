package com.fax.cursotestingaris.product_detail.presentation

sealed interface ProductDetailEvent {
    data class ShowMessage(val message: String) : ProductDetailEvent
    data class ShowError(val message: String) : ProductDetailEvent
}