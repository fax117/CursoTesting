package com.fax.cursotestingaris.product_list.domain.repositories

import com.fax.cursotestingaris.product_list.domain.models.Promotion
import kotlinx.coroutines.flow.Flow

interface PromotionRepository {
    fun getActivePromotions(): Flow<List<Promotion>>

    suspend fun refreshPromotions()
}