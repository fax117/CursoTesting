package com.fax.cursotestingaris.product_list.domain.usecase

import com.fax.cursotestingaris.product_list.domain.models.Product
import com.fax.cursotestingaris.product_list.domain.repositories.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(): Flow<List<Product>>{
        return productRepository.getProducts()
    }
}