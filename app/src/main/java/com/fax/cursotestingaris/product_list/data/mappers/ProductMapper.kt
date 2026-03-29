package com.fax.cursotestingaris.product_list.data.mappers

import com.fax.cursotestingaris.product_list.data.local.database.entity.ProductEntity
import com.fax.cursotestingaris.product_list.data.remote.response.ProductResponse
import com.fax.cursotestingaris.product_list.domain.models.Product

fun ProductResponse.toEntity(): ProductEntity {
    val finalPrice: Double = priceCents?.div(100.0) ?: 0.0

    return ProductEntity(
        id = id,
        name = name,
        description = description,
        price = finalPrice,
        category = category,
        stock = stock,
        imageUrl = imageUrl
    )
}

fun ProductEntity.toDomain(): Product? {
    if (category.isNullOrEmpty()) {
        return null
    }

    return Product(
        id = id,
        name = name,
        description = description ?: "",
        price = price,
        category = category,
        stock = stock ?: 0,
        imageUrl = imageUrl
    )
}