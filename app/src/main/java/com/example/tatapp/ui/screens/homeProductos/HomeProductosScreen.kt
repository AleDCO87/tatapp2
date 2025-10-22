package com.example.tatapp.ui.screens.homeProductos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.tatapp.ui.components.CarruselCategorias
import com.example.tatapp.ui.screens.carrito.CarritoViewModel
import com.example.tatapp.viewmodel.SettingsViewModel
import com.example.tatapp.data.clases.CategoriaItem

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
    val cartBadge by carritoViewModel.totalEnCarrito.collectAsState()

    // --------- Network / VM (tipo explícito para evitar el error del delegate) ----------
    val retrofit by remember {
        mutableStateOf(
            NetworkModule.retrofit(NetworkModule.okHttp { null })
        )
    }

    val service = remember { retrofit.create(CatalogService::class.java) }
    val repo = remember { CatalogRepository(service) }
    val vm = remember { CatalogHomeViewModel(repo) }

    val prodsState by vm.products.collectAsState()
    val catsState by vm.categories.collectAsState()

    LaunchedEffect(Unit) { vm.loadAll() }

    // -------- Bottom bar items + estado seleccionado --------
    val bottomItems = remember {
        listOf(
            BottomItem("home", R.drawable.home, "Inicio", iconSize = 50.dp),
            BottomItem("menu", R.drawable.menu, "Menú", iconSize = 45.dp),
            BottomItem("carrito", R.drawable.carrito, "Carrito", iconSize = 40.dp),
            BottomItem("perfil", R.drawable.perfil, "Perfil", iconSize = 40.dp),
            BottomItem("config", R.drawable.icon_tatapp, "Más", iconSize = 50.dp, tintIcon = false)
        )
    }
    var selectedBottom by remember { mutableStateOf("home") }
    val snackbar = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbar)},
        topBar = {
            SearchTopBar(
                query = query,
                onQueryChange = { query = it },
                onSearch = { /* noop: conectar a búsqueda si aplica */ },
                onVoiceClick = { /* noop */ },
                isDark = isDark,
                onToggleDark = { settingsVm.toggleDark() },
                onOpenPerfil = { navController.navigate("login") },
                onOpenConfig = { navController.navigate("config") }
            )
        },
        bottomBar = {
            BottomHomeBar(
                items = bottomItems.map { if (it.id == "carrito") it.copy(badgeCount = cartBadge) else it },
                selectedId = selectedBottom,
                onItemSelected = { item ->
                    selectedBottom = item.id
                    when (item.id) {
                        "home"    -> navController.navigate("homeProductosScreen") { launchSingleTop = true }
                        "menu"    -> navController.navigate("homeProductosScreen")
                        "carrito" -> navController.navigate("carrito")
                        "perfil"  -> navController.navigate("registro")
                        "config"  -> navController.navigate("homeProductosScreen")
                    }
                },
                backgroundColor = Color(0xFFF47606),
                contentColor = Color.White,
                selectedLift = 58.dp,
                selectedBubbleSize = 70.dp
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
        ) {
            // --------------------- CATEGORIAS REALES ---------------------
            when (val cs = catsState) {
                is UiState.Loading, UiState.Loading -> CenterLoader()
                is UiState.Error -> Text("Error categorías: ${cs.message}")
                is UiState.Success -> {
                    val items = mapToCategoriaItems(cs.data)
                    if (items.isNotEmpty()){
                        Text(
                            text = "Explora por categoria",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 6.dp)
                        )
                        CarruselCategorias(
                            categorias = items,
                            onCategoriaClick = { cat ->
                                navController.navigate("subcategorias/${cat.nombreCat}")
                            }
                        )
                        Spacer(Modifier.height(12.dp))
                    }
                }

                UiState.Idle -> { /* No hacer nada TODO()*/ }
            }

            // --------------------- PRODUCTOS REALES ---------------------
            Text(
                text = "Catálogo",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            when (val ps = prodsState) {
                is UiState.Idle    -> Unit
                is UiState.Loading -> CenterLoader()
                is UiState.Error   -> ErrorMini("Error catálogo: ${ps.message}")
                is UiState.Success -> {
                    val productos = ps.data

                    // Sección 1
                    SectionCarousel(
                        title = "Recomendados",
                        products = productos,
                        onProductClick = { p -> navController.navigate("detalle/${p.id}") },
                        onAddToCart = { /* TODO: agregar al carrito/Room-API */ }
                    )
                    Spacer(Modifier.height(12.dp))
                    // Sección 2
                    SectionCarousel(
                        title = "Populares",
                        products = productos,
                        onProductClick = { p -> navController.navigate("detalle/${p.id}") },
                        onAddToCart = { /* TODO: agregar al carrito/Room-API */ }
                    )

                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }
}


/* ---------- Helper: Category (dominio) -> CategoriaItem (UI del carrusel) ---------- */
private fun mapToCategoriaItems(categories: List<com.example.tatapp.domain.model.Category>): List<CategoriaItem> {
    fun iconFor(name: String): Int = when (name.lowercase()) {
        "alimentos" -> R.drawable.alimentos
        "salud"     -> R.drawable.salud
        "mascotas"  -> R.drawable.mascotas
        "jardín"    -> R.drawable.jardin
        else        -> R.drawable.figura
    }
    return categories.map { CategoriaItem(nombreCat = it.name, iconoCat = iconFor(it.name)) }
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
        Text(title, style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(horizontal = 16.dp))
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

/* ---------- Helpers UI ---------- */
@Composable private fun CenterLoader() {
    Box(Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
@Composable private fun ErrorMini(msg: String) {
    Text(text = msg, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(16.dp))
}
