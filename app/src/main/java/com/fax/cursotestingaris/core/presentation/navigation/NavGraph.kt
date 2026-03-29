package com.fax.cursotestingaris.core.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.fax.cursotestingaris.product_list.presentation.ProductListScreen

@Composable
fun NavGraph() {
    val backStack = rememberNavBackStack(Screen.ProductList)
    val entries = entryProvider<NavKey> {
        entry<Screen.ProductList> {
            ProductListScreen()
        }
        entry<Screen.Cart> {
            Text(text = "Cart", fontSize = 30.sp)
        }
        entry<Screen.Settings> {
            Text(text = "Settings", fontSize = 30.sp)
        }
        entry<Screen.ProductDetail> {
            Text(text = "Product Detail", fontSize = 30.sp)
        }
    }

    NavDisplay(
        modifier = Modifier,
        backStack = backStack,
        entryProvider = entries,
        onBack = { backStack.removeLastOrNull() }
    )
}