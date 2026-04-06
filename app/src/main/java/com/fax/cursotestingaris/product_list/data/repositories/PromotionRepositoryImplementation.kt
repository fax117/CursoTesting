package com.fax.cursotestingaris.product_list.data.repositories

import com.fax.cursotestingaris.core.domain.coroutines.DispatchersProvider
import com.fax.cursotestingaris.product_list.data.local.LocalDataSource
import com.fax.cursotestingaris.product_list.data.local.database.entity.PromotionEntity
import com.fax.cursotestingaris.product_list.data.mappers.toEntity
import com.fax.cursotestingaris.product_list.data.remote.RemoteDataSource
import com.fax.cursotestingaris.product_list.data.remote.response.PromotionResponse
import com.fax.cursotestingaris.product_list.domain.models.Promotion
import com.fax.cursotestingaris.product_list.domain.repositories.PromotionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class PromotionRepositoryImplementation @Inject constructor(
    val remoteDataSource: RemoteDataSource,
    val localDataSource: LocalDataSource,
    val dispatchers: DispatchersProvider,
    private val json: Json
) : PromotionRepository {
    override fun getActivePromotions(): Flow<List<Promotion>> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshPromotions() {
        withContext(dispatchers.io) {
            val promotions: List<PromotionResponse> = remoteDataSource.getPromotions().getOrThrow()
            val promotionsEntity: List<PromotionEntity> = promotions.mapNotNull {
                it.toEntity(json)
            }
            localDataSource.savePromotions(promotionsEntity)
        }
    }
}