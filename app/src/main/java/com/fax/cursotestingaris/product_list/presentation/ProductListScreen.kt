package com.fax.cursotestingaris.product_list.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fax.cursotestingaris.product_list.domain.models.Product
import com.fax.cursotestingaris.product_list.presentation.components.FiltersMenu
import com.fax.cursotestingaris.product_list.presentation.components.ProductItem

@Composable
fun ProductListScreen(
    modifier: Modifier = Modifier,
    productListViewModel: ProductListViewModel = hiltViewModel()
) {

    val uiState by productListViewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        productListViewModel.events.collect { event ->
            when (event) {
                is ProductListEvent.ShowMessage -> {
                    snackbarHostState.showSnackbar(event.message)
                }

                else -> {}
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->

        when (val state: ProductListUIState = uiState) {
            is ProductListUIState.Loading -> {
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is ProductListUIState.Error -> {
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "ERROR: ${state.message}", fontSize = 30.sp, color = Color.Red)
                }
            }

            is ProductListUIState.Success -> {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    FiltersMenu(
                        state = state,
                        onCategorySelected = { category ->
                            productListViewModel.setCategory(category)
                        },
                        onSortOrderSelected = { sortOptions ->
                            productListViewModel.setSortOption(
                                sortOptions
                            )
                        }
                    )

                    Text(
                        text = "${state.products.size} products",
                        modifier = Modifier.padding(16.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    if (state.products.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "🔍",
                                    style = MaterialTheme.typography.displayMedium
                                )
                                Text(
                                    text = "No products found",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.tertiary
                                )

                            }
                        }
                    } else {
                        LazyColumn {
                            items(state.products) { product: Product ->
                                ProductItem(product = product, onClick = {})
                            }
                        }
                    }
                }
            }
        }
    }
}

