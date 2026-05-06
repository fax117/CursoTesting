package com.fax.cursotestingaris.cart.data.mapper

import com.fax.cursotestingaris.cart.data.local.database.entity.CartItemEntity
import com.fax.cursotestingaris.cart.domain.model.CartItem

fun CartItemEntity.toDomain(): CartItem {
    return CartItem(
        productId = productId,
        quantity = quantity
    )
}

fun CartItem.toEntity(): CartItemEntity{
    return CartItemEntity(
        productId = productId,
        quantity = quantity
    )
}