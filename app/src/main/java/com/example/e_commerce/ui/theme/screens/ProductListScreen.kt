package com.example.e_commerce.ui.theme.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.e_commerce.viewmodel.CartViewModel
import com.example.e_commerce.viewmodel.ProductViewModel

@Composable
fun ProductListScreen(
    viewModel: ProductViewModel,
    cartViewModel: CartViewModel,
    navController: NavController
) {
    val products by viewModel.products.collectAsState()
    val loading by viewModel.loading.collectAsState()

    if (loading) {
        Text(
            "Loading products...",
            modifier = Modifier.fillMaxSize(),
            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
        )
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(products) { product ->
                ProductItem(product = product, onAddToCartClick = {
                    cartViewModel.addToCart(product)
                    navController.navigate("cart")
                })
            }
        }
    }
}

