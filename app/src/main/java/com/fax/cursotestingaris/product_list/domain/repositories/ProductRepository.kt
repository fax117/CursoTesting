package com.fax.cursotestingaris.product_list.domain.repositories

import com.fax.cursotestingaris.product_list.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<List<Product>>
    fun getProductById(id: String) : Flow<Product?>
    suspend fun refreshProducts()
}
