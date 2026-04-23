package com.fax.cursotestingaris.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.fax.cursotestingaris.core.data.coroutines.DefaultDispatchersProvider
import com.fax.cursotestingaris.core.domain.coroutines.DispatchersProvider
import com.fax.cursotestingaris.product_list.data.repositories.ProductRepositoryImplementation
import com.fax.cursotestingaris.product_list.data.repositories.PromotionRepositoryImplementation
import com.fax.cursotestingaris.product_list.data.repositories.SettingsRepositoryImpl
import com.fax.cursotestingaris.product_list.domain.repositories.ProductRepository
import com.fax.cursotestingaris.product_list.domain.repositories.PromotionRepository
import com.fax.cursotestingaris.product_list.domain.repositories.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

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

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(settingsRepositoryImpl: SettingsRepositoryImpl): SettingsRepository {
        return settingsRepositoryImpl
    }
}
