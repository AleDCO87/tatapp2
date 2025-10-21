package com.example.tatapp.ui.screens.homeProductos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tatapp.R
import com.example.tatapp.core.ui.UiState
import com.example.tatapp.data.remote.NetworkModule
import com.example.tatapp.data.remote.services.CatalogService
import com.example.tatapp.data.repository.CatalogRepository
import com.example.tatapp.domain.model.Product
import com.example.tatapp.ui.components.BottomHomeBar
import com.example.tatapp.ui.components.BottomItem
import com.example.tatapp.ui.components.SearchTopBar
import com.example.tatapp.ui.screens.carrito.CarritoViewModel
import com.example.tatapp.viewmodel.SettingsViewModel
import kotlinx.coroutines.flow.collectLatest
import retrofit2.Retrofit

@Composable
fun HomeProductosScreen(
    navController: NavHostController,
    settingsVm: SettingsViewModel,
    carritoViewModel: CarritoViewModel
) {
    // --------- Tema claro/oscuro ----------
    val isDark by settingsVm.darkMode.collectAsState()

    // --------- Estado buscador ----------
    var query by remember { mutableStateOf("") }

    // --------- Badge carrito (usa siempre initial) ----------
    val cartBadge by carritoViewModel.totalEnCarrito.collectAsState(initial = 0)

    // --------- Network / VM (tipo explícito para evitar el error del delegate) ----------
    val retrofit: Retrofit by remember {
        mutableStateOf(
            NetworkModule.retrofit(
                NetworkModule.okHttp { /* token si aplica */ null }
            )
        )
    }
    val catalogService: CatalogService = remember { retrofit.create(CatalogService::class.java) }
    val catalogRepo = remember { CatalogRepository(catalogService) }
    val catalogVm: CatalogNetworkViewModel = viewModel(
        factory = CatalogNetworkVMFactory(catalogRepo)
    )

    val state by catalogVm.state.collectAsState()

    // Carga catálogo una sola vez
    LaunchedEffect(Unit) { catalogVm.loadCatalog() }

    // --------- Bottom bar items ----------
    val bottomItems: List<BottomItem> = remember {
        listOf(
            BottomItem("home",    R.drawable.home,    "Inicio",  "Inicio"),
            BottomItem("cart",    R.drawable.carrito, "Carro",   "Carro"),
            BottomItem("profile", R.drawable.perfil,  "Perfil",  "Perfil"),
            BottomItem("more",    R.drawable.figura,  "Más",     "Más")
        )
    }
    var selectedBottom by remember { mutableStateOf("home") }

    Scaffold(
        topBar = {
            SearchTopBar(
                query = query,
                onQueryChange = { query = it },
                onSearch = { /* TODO: conectar a búsqueda si aplica */ },
                onVoiceClick = { /* TODO */ },
                isDark = isDark,
                onToggleDark = { settingsVm.toggleDark() },
                onOpenPerfil = { navController.navigate("perfil") },
                onOpenConfig = { navController.navigate("config") }
            )
        },
        bottomBar = {
            BottomHomeBar(
                items = bottomItems.map { item ->
                    if (item.id == "cart") item.copy(badgeCount = cartBadge) else item
                },
                selectedId = selectedBottom,
                onItemSelected = { item ->
                    selectedBottom = item.id
                    when (item.id) {
                        "home"    -> navController.navigate("homeProductosScreen") { launchSingleTop = true }
                        "cart"    -> navController.navigate("carrito")
                        "profile" -> navController.navigate("perfil")
                        "more"    -> navController.navigate("config")
                    }
                },
                backgroundColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
        ) {
            // --------------------- Título / encabezado de sección ---------------------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Catálogo",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            // --------------------- Secciones (carruseles por categorías) ---------------------
            when (val s = state) {
                is UiState.Idle, UiState.Loading -> CenterLoader()
                is UiState.Error -> ErrorWithRetry(message = s.message) { catalogVm.loadCatalog() }
                is UiState.Success -> {
                    val productos = s.data

                    SectionCarousel(
                        title = "Recomendados",
                        products = productos,
                        onProductClick = { p -> navController.navigate("detalle/${p.id}") },
                        onAddToCart = { _ -> /* TODO: integrar acción de carrito si quieres */ }
                    )

                    Spacer(Modifier.height(8.dp))

                    SectionCarousel(
                        title = "Populares",
                        products = productos,
                        onProductClick = { p -> navController.navigate("detalle/${p.id}") },
                        onAddToCart = { _ -> /* TODO */ }
                    )

                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }
}

/* =================== Sección reutilizable (carrusel horizontal) =================== */

@Composable
private fun SectionCarousel(
    title: String,
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    onAddToCart: (Product) -> Unit
) {
    if (products.isEmpty()) return

    Column(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, style = MaterialTheme.typography.titleSmall)
        }

        Spacer(Modifier.height(6.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(products, key = { it.id }) { p ->
                ProductCardNeutral(
                    product = p,
                    onClick = { onProductClick(p) },
                    onAddToCart = { onAddToCart(p) }
                )
            }
        }
    }
}

/* =================== Card neutral mínima (sustituible por la tuya) =================== */

@Composable
private fun ProductCardNeutral(
    product: Product,
    onClick: () -> Unit,
    onAddToCart: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .width(220.dp)
            .heightIn(min = 120.dp),
        onClick = onClick
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(product.name, style = MaterialTheme.typography.titleSmall, maxLines = 2)
            Spacer(Modifier.height(4.dp))
            Text("$${"%.0f".format(product.price)}", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
            Button(onClick = onAddToCart) { Text("Agregar") }
        }
    }
}

/* =================== Helpers neutrales =================== */

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
