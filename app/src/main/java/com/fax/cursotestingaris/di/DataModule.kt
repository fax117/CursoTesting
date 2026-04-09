package com.fax.cursotestingaris.di

import com.fax.cursotestingaris.core.data.coroutines.DefaultDispatchersProvider
import com.fax.cursotestingaris.core.domain.coroutines.DispatchersProvider
import com.fax.cursotestingaris.product_list.data.repositories.ProductRepositoryImplementation
import com.fax.cursotestingaris.product_list.data.repositories.PromotionRepositoryImplementation
import com.fax.cursotestingaris.product_list.domain.repositories.ProductRepository
import com.fax.cursotestingaris.product_list.domain.repositories.PromotionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideDispatchersProvider(defaultDispatchersProvider: DefaultDispatchersProvider): DispatchersProvider {
        return defaultDispatchersProvider
    }

    @Provides
    @Singleton
    fun provideProductRepository(productRepositoryImplementation: ProductRepositoryImplementation): ProductRepository {
        return productRepositoryImplementation
    }

    @Provides
    @Singleton
    fun providePromotionRepository(promotionRepositoryImplementation: PromotionRepositoryImplementation): PromotionRepository {
        return promotionRepositoryImplementation
    }
}
