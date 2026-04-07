package com.fax.cursotestingaris.product_list.data.mappers

import com.fax.cursotestingaris.product_list.data.local.database.entity.PromotionEntity
import com.fax.cursotestingaris.product_list.data.remote.response.PromotionResponse
import com.fax.cursotestingaris.product_list.domain.models.Promotion
import com.fax.cursotestingaris.product_list.domain.models.PromotionType
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import java.time.Instant

fun PromotionEntity.toDomain(json: Json): Promotion? {

    val decodedProductIds: List<String>? = runCatching {
        json.decodeFromString(
            deserializer = ListSerializer(String.serializer()),
            string = productIds
        )
    }.getOrNull()

    val decodedType = runCatching {
        PromotionType.valueOf(
            type.trim().uppercase()
        )
    }.getOrNull()

    if(decodedProductIds == null || decodedType == null) return null

    val finalOfferValue = when(decodedType){
        PromotionType.PERCENT -> percentage?.toDoubleOrNull()
        PromotionType.BUY_X_PAY_Y -> payY?.toDouble()
    }

    finalOfferValue ?: return null

    return Promotion(
        id = id,
        productIds = decodedProductIds,
        type = decodedType,
        value = finalOfferValue,
        buyQuantity = buyX,
        startTime = Instant.ofEpochSecond(startAtEpoch),
        endTime = Instant.ofEpochSecond(endAtEpoch),
    )
}

fun PromotionResponse.toEntity(json: Json): PromotionEntity? {

    if (startAtEpoch == null || endAtEpoch == null) return null

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