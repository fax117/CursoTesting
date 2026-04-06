package com.fax.cursotestingaris.product_list.data.mappers

import com.fax.cursotestingaris.product_list.data.local.database.entity.PromotionEntity
import com.fax.cursotestingaris.product_list.data.remote.response.PromotionResponse
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

fun PromotionResponse.toEntity(json: Json): PromotionEntity? {

    if(startAtEpoch == null || endAtEpoch == null) return null

    val productIds: List<String> = listOf(productId)
    val productIdsJson: String =
        json.encodeToString(
            serializer = ListSerializer(String.serializer()),
            value = productIds
        )

    return PromotionEntity(
        id = id,
        productIds = productIdsJson,
        type = type,
        percentage = percentage,
        buyX = buyX,
        payY = payY,
        startAtEpoch = startAtEpoch,
        endAtEpoch = endAtEpoch
    )

}