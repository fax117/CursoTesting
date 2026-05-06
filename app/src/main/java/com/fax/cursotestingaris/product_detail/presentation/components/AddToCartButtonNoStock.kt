package com.fax.cursotestingaris.product_detail.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fax.cursotestingaris.product_list.domain.models.Product

@Composable
fun AddToCartButtonNoStock(modifier: Modifier = Modifier, product: Product) {
    Surface(
        modifier = modifier.fillMaxWidth()
            .navigationBarsPadding(),
        shadowElevation = 8.dp,
        tonalElevation = 2.dp
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {},
                enabled = false,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(disabledContainerColor = MaterialTheme.colorScheme.errorContainer)
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(Modifier.width(8.dp))

                Text(
                    text = "No stock available",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}