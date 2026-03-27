package com.fax.cursotestingaris.product_list.presentation

sealed interface ProductListEvent {
    data class ShowMessage(val message: String) : ProductListEvent
    data class ShowError(val message: String) : ProductListEvent
    data class ShowToast(val message: String) : ProductListEvent
}