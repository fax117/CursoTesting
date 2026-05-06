package com.fax.cursotestingaris.product_detail.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fax.cursotestingaris.product_list.domain.models.Product

@Composable
fun AddToCartButton(
    modifier: Modifier = Modifier,
    product: Product?,
    isLoading: Boolean,
    addToCArt: () -> Unit
) {

    product?.let {
        if (it.stock > 0) {
            AddToCartButtonWithStock(
                modifier,
                product = it,
                isLoading = isLoading,
                addToCArt = addToCArt
            )
        } else {
            AddToCartButtonNoStock(modifier, product = it)
        }
    }

}