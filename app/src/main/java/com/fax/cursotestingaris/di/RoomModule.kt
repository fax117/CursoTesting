package com.fax.cursotestingaris.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.fax.cursotestingaris.cart.data.local.database.dao.CartItemDao
import com.fax.cursotestingaris.core.data.local.database.MiniMarketDatabase
import com.fax.cursotestingaris.product_list.data.local.database.dao.ProductDao
import com.fax.cursotestingaris.product_list.data.local.database.dao.PromotionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideMiniMarketDatabase(@ApplicationContext context: Context): MiniMarketDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = MiniMarketDatabase::class.java,
            name = "mini_market_database"
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

    @Provides
    @Singleton
    fun provideCartItemDao(db: MiniMarketDatabase): CartItemDao {
        return db.cartItemDao()
    }

}