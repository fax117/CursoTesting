package com.fax.cursotestingaris.di

import android.app.Application
import androidx.room.Room
import com.fax.cursotestingaris.product_list.data.local.database.MiniMarketDatabase
import com.fax.cursotestingaris.product_list.data.local.database.dao.ProductDao
import com.fax.cursotestingaris.product_list.data.local.database.dao.PromotionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideMiniMarketDatabase(application: Application): MiniMarketDatabase {
        return Room.databaseBuilder(
            application,
            MiniMarketDatabase::class.java,
            "mini_market_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideProductDao(db: MiniMarketDatabase): ProductDao {
        return db.productDao()
    }

    @Provides
    @Singleton
    fun providePromotionDao(db: MiniMarketDatabase): PromotionDao {
        return db.promotionDao()
    }

}