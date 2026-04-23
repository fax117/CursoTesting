package com.fax.cursotestingaris.product_list.domain.repositories

import com.fax.cursotestingaris.core.domain.model.ThemeMode
import com.fax.cursotestingaris.product_list.domain.models.SortOption
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    val inStockOnly: Flow<Boolean>
    suspend fun setInStockOnly(value: Boolean)

    val themeMode: Flow<ThemeMode>
    suspend fun setThemeMode(value: ThemeMode)

    val selectedCategory: Flow<String?>
    suspend fun setSelectedCategory(value: String?)

    val filtersVisible: Flow<Boolean>
    suspend fun setFiltersVisible(value: Boolean)

    val sortOption: Flow<SortOption>
    suspend fun setSortOption(value: SortOption)



}