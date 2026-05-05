package com.fax.cursotestingaris.product_detail.domain.usecase

import com.fax.cursotestingaris.product_list.domain.models.ProductWithPromotion
import com.fax.cursotestingaris.product_list.domain.repositories.ProductRepository
import com.fax.cursotestingaris.product_list.domain.repositories.PromotionRepository
import com.fax.cursotestingaris.product_list.domain.usecase.GetPromotionForProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.Instant
import javax.inject.Inject

class GetProductDetailWithPromotionUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    private val promotionRepository: PromotionRepository,
    private val getPromotionForProduct: GetPromotionForProduct
) {

    operator fun invoke(productId: String) : Flow<ProductWithPromotion?> {
        return combine(
            productRepository.getProductById(productId),
            promotionRepository.getActivePromotions()
        ){ product, promotions ->
            val now = Instant.now()
            val activePromotions = promotions.filter {
                it.startTime <= now && it.endTime >= now
            }

            product?.let {
                val finalPromotion = getPromotionForProduct(it, activePromotions)
                ProductWithPromotion(product = it, promotion = finalPromotion)
            }
        }
    }

}