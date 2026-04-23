package com.fax.cursotestingaris.product_list.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.fax.cursotestingaris.core.domain.model.ThemeMode
import com.fax.cursotestingaris.product_list.domain.models.SortOption
import com.fax.cursotestingaris.product_list.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    companion object {
        val IN_STOCK_ONLY = booleanPreferencesKey("in_stock_only")
        val THEME_MODE = intPreferencesKey("theme_mode")
        val SELECTED_CATEGORY = stringPreferencesKey("selected_category")
        val FILTERS_VISIBLE = booleanPreferencesKey("filters_visible")
        val SORT_OPTION = stringPreferencesKey("sort_option")
    }


    private val dataStoreFlow = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }

    override val inStockOnly: Flow<Boolean> = dataStoreFlow.map { preferences ->
        preferences[IN_STOCK_ONLY] ?: false

    }

    override suspend fun setInStockOnly(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[IN_STOCK_ONLY] = value
        }
    }

    override val themeMode: Flow<ThemeMode> = dataStoreFlow.map { preferences ->
        when (preferences[THEME_MODE]) {
            ThemeMode.SYSTEM.id -> ThemeMode.SYSTEM
            ThemeMode.LIGHT.id -> ThemeMode.LIGHT
            ThemeMode.DARK.id -> ThemeMode.DARK
            else -> ThemeMode.SYSTEM
        }
    }

    override suspend fun setThemeMode(value: ThemeMode) {
        dataStore.edit { preferences ->
            when (value) {
                ThemeMode.SYSTEM -> preferences[THEME_MODE] = ThemeMode.SYSTEM.id
                ThemeMode.LIGHT -> preferences[THEME_MODE] = ThemeMode.LIGHT.id
                ThemeMode.DARK -> preferences[THEME_MODE] = ThemeMode.DARK.id
            }
        }
    }

    override val selectedCategory: Flow<String?> = dataStoreFlow.map { preferences ->
        preferences[SELECTED_CATEGORY]
    }

    override suspend fun setSelectedCategory(value: String?) {
        dataStore.edit { preferences ->
            if (value == null) {
                preferences.remove(SELECTED_CATEGORY)
            } else {
                preferences[SELECTED_CATEGORY] = value
            }
        }
    }

    override val filtersVisible: Flow<Boolean> = dataStoreFlow.map { preferences ->
        preferences[FILTERS_VISIBLE] ?: true
    }

    override suspend fun setFiltersVisible(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[FILTERS_VISIBLE] = value
        }
    }

    override val sortOption: Flow<SortOption> = dataStoreFlow.map { preferences ->
        val raw = preferences[SORT_OPTION]

        runCatching {
            SortOption.valueOf(
                raw ?: SortOption.NONE.name
            )
        }.getOrDefault(SortOption.NONE)
    }

    override suspend fun setSortOption(value: SortOption) {
        dataStore.edit { preferences -> preferences[SORT_OPTION] = value.name }
    }
}