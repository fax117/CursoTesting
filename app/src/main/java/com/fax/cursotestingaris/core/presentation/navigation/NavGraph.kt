package com.fax.cursotestingaris.core.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.fax.cursotestingaris.product_detail.presentation.ProductDetailScreen
import com.fax.cursotestingaris.product_list.presentation.ProductListScreen
import com.fax.cursotestingaris.settings.presentation.SettingsScreen

@Composable
fun NavGraph() {
    val backStack = rememberNavBackStack(Screen.ProductList)
    val entries = entryProvider<NavKey> {
        entry<Screen.ProductList> {
            ProductListScreen(
                navigateToSettings = { backStack.add(Screen.Settings) },
                navigateToProductDetail = { productId ->
                    backStack.add(
                        Screen.ProductDetail(
                            productId
                        )
                    )
                }
            )
        }
        entry<Screen.Cart> {
            Text(text = "Cart", fontSize = 30.sp)
        }
        entry<Screen.Settings> {
            SettingsScreen(onBack = { backStack.removeLastOrNull() })
        }
        entry<Screen.ProductDetail> { route ->
            ProductDetailScreen(
                productId = route.productId,
                onBack = { backStack.removeLastOrNull() })
        }
    }

    NavDisplay(
        modifier = Modifier,
        backStack = backStack,
        entryProvider = entries,
        onBack = { backStack.removeLastOrNull() }
    )
}