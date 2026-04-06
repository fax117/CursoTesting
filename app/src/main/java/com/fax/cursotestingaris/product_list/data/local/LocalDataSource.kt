package com.fax.cursotestingaris.product_list.data.local

import com.fax.cursotestingaris.product_list.data.local.database.dao.ProductDao
import com.fax.cursotestingaris.product_list.data.local.database.dao.PromotionDao
import com.fax.cursotestingaris.product_list.data.local.database.entity.ProductEntity
import com.fax.cursotestingaris.product_list.data.local.database.entity.PromotionEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class LocalDataSource @Inject constructor(
    private val productDao: ProductDao,
    private val promotionDao: PromotionDao
) {

    fun getAllProducts(): Flow<List<ProductEntity>> {
        return productDao.getAllProducts()
    }

    fun getAllPromotions(): Flow<List<PromotionEntity>> {
        return promotionDao.getAllPromotions()
    }

    suspend fun saveProducts(productsEntity: List<ProductEntity>) {
        productDao.replaceAll(productsEntity)
    }

    suspend fun savePromotions(promotionsEntity: List<PromotionEntity>) {
        promotionDao.replaceAll(promotionsEntity)
    }

}