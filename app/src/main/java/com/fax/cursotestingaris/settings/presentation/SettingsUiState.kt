package com.fax.cursotestingaris.settings.presentation

import com.fax.cursotestingaris.core.domain.model.ThemeMode
import com.fax.cursotestingaris.core.domain.model.ThemeMode.SYSTEM

data class SettingsUiState (
    val inStockOnly: Boolean = false,
    val themeMode: ThemeMode = SYSTEM,
)