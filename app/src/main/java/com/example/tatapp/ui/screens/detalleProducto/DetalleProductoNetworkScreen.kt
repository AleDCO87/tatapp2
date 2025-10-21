package com.example.tatapp.ui.screens.detalleProducto

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tatapp.core.ui.UiState
import com.example.tatapp.domain.model.Product

@Composable
fun DetalleProductoNetworkScreen(
    navController: NavHostController,
    vm: DetalleNetworkViewModel,
    productoId: String,
    content: @Composable (product: Product, onAddToCart: () -> Unit) -> Unit
) {
    val state by vm.state.collectAsState()

    LaunchedEffect(productoId) { vm.loadProduct(productoId) }

    when (val s = state) {
        is UiState.Idle, UiState.Loading -> CenterLoader()
        is UiState.Error -> ErrorWithRetry(s.message) { vm.loadProduct(productoId) }
        is UiState.Success -> {
            val p = s.data
            content(p) { /* TODO: Integrar add to cart de red o local */ }
        }
    }
}

@Composable private fun CenterLoader() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable private fun ErrorWithRetry(message: String, onRetry: () -> Unit) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(message)
            Spacer(Modifier.height(8.dp))
            Button(onClick = onRetry) { Text("Reintentar") }
        }
    }
}
