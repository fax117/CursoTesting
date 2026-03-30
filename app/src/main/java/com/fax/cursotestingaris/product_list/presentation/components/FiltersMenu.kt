package com.fax.cursotestingaris.product_list.presentation.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fax.cursotestingaris.product_list.domain.models.SortOption
import com.fax.cursotestingaris.product_list.presentation.ProductListUIState


@Composable
fun FiltersMenu(
    modifier: Modifier = Modifier,
    state: ProductListUIState.Success,
    onCategorySelected: (String?) -> Unit,
    onSortOrderSelected: (SortOption) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "Categories")
            Row(
                Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = state.selectedCategory.equals(null),
                    onClick = { onCategorySelected(null) },
                    label = {
                        Text(
                            text = "All", style = MaterialTheme.typography.labelSmall
                        )
                    })

                state.categories.forEach { category ->
                    FilterChip(
                        selected = category.equals(state.selectedCategory, ignoreCase = true),
                        onClick = { onCategorySelected(category) },
                        label = {
                            Text(
                                text = category, style = MaterialTheme.typography.labelSmall
                            )
                        })
                }
            }

            HorizontalDivider()

            Text(text = "Sort by")

            Row(
                Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = state.sortOption == SortOption.PRICE_ASC,
                    onClick = { onSortOrderSelected(SortOption.PRICE_ASC) },
                    label = {
                        Text(
                            text = "Price Up", style = MaterialTheme.typography.labelSmall
                        )
                    }, modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = state.sortOption == SortOption.PRICE_DESC,
                    onClick = { onSortOrderSelected(SortOption.PRICE_DESC) },
                    label = {
                        Text(
                            text = "Price Down", style = MaterialTheme.typography.labelSmall
                        )
                    }, modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = state.sortOption == SortOption.DISCOUNT,
                    onClick = { onSortOrderSelected(SortOption.DISCOUNT) },
                    label = {
                        Text(
                            text = "Discount", style = MaterialTheme.typography.labelSmall
                        )
                    }, modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
