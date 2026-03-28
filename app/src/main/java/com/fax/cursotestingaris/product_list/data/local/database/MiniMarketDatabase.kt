package com.fax.cursotestingaris.product_list.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fax.cursotestingaris.product_list.data.local.database.dao.ProductDao
import com.fax.cursotestingaris.product_list.data.local.database.dao.PromotionDao
import com.fax.cursotestingaris.product_list.data.local.database.entity.ProductEntity
import com.fax.cursotestingaris.product_list.data.local.database.entity.PromotionEntity

@Database(
    entities = [ProductEntity::class, PromotionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MiniMarketDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun promotionDao(): PromotionDao

}