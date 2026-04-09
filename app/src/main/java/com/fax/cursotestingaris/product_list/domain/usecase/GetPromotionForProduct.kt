package com.fax.cursotestingaris.product_list.domain.usecase

import com.fax.cursotestingaris.core.presentation.ex.roundTo2Decimals
import com.fax.cursotestingaris.product_list.domain.models.Product
import com.fax.cursotestingaris.product_list.domain.models.ProductPromotion
import com.fax.cursotestingaris.product_list.domain.models.Promotion
import com.fax.cursotestingaris.product_list.domain.models.PromotionType
import javax.inject.Inject

class GetPromotionForProduct @Inject constructor() {

    operator fun invoke(product: Product, promotions: List<Promotion>): ProductPromotion? {
        val productPromos = promotions.filter {
            it.productIds.contains(product.id)
        }

        val percentPromo: Promotion? =
            productPromos.filter { it.type == PromotionType.PERCENT }
                .maxByOrNull { it.value ?: 0.0 }

        if (percentPromo != null) {
            val percent = percentPromo.value.coerceIn(0.0, 100.0)
            val discountedPrice = product.price * (1 - percent / 100.0).roundTo2Decimals()
            return ProductPromotion.Percent(percent, discountedPrice)
        }

        val buyXPayYPromo =
            productPromos.firstOrNull() {
                it.type == PromotionType.BUY_X_PAY_Y
            }

        if(buyXPayYPromo != null){
            val buy = buyXPayYPromo.buyQuantity ?: return null
            val pay = buyXPayYPromo.value.toInt().coerceIn(0, buy)

            return ProductPromotion.BuyXPayY(
                buy = buy,
                pay = pay,
                label = "Buy $buy for $pay"
            )

        }
        return null
    }
}