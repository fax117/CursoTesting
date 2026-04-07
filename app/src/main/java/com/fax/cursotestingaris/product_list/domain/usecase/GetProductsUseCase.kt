package com.fax.cursotestingaris.product_list.domain.usecase

import com.fax.cursotestingaris.product_list.domain.models.ProductWithPromotion
import com.fax.cursotestingaris.product_list.domain.repositories.ProductRepository
import com.fax.cursotestingaris.product_list.domain.repositories.PromotionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.Instant
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    private val promotionRepository: PromotionRepository,
    private val getPromotionForProduct: GetPromotionForProduct
) {
    operator fun invoke(): Flow<List<ProductWithPromotion>> {

        return combine(
            productRepository.getProducts(),
            promotionRepository.getActivePromotions()
        ) { products, promotions ->

            val now = Instant.now()
            val activePromotions = promotions.filter {
                it.startTime.isBefore(now) && it.endTime.isAfter(now)
            }

            products.forEach { product ->
                val promotion = getPromotionForProduct(product, activePromotions)
                ProductWithPromotion(product = product, promotion = promotion)
            }

            emptyList()
        }
    }
}