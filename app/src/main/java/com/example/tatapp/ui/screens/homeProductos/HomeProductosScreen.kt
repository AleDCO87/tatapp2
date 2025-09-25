package com.example.tatapp.ui.screens.homeProductos

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tatapp.R
import com.example.tatapp.ui.components.TopBarOverflowMenu
import com.example.tatapp.ui.screens.carrito.CarritoViewModel
import com.example.tatapp.ui.screens.productos.ClaseProductos
import com.example.tatapp.ui.components.drawableMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.tatapp.ui.components.BottomHomeBar
import com.example.tatapp.ui.components.BottomItem
import com.example.tatapp.ui.components.CarruselCategorias
import com.example.tatapp.ui.components.SearchTopBar
import com.example.tatapp.viewmodel.SettingsViewModel


// --- Función para cargar JSON desde assets ---
suspend fun loadProductosFromJson(context: Context): List<ClaseProductos> {
    return withContext(Dispatchers.IO) {
        val jsonString = context.assets.open("productos.json")
            .bufferedReader()
            .use { it.readText() }
        Json.decodeFromString(jsonString)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeProductosScreen(
    navController: NavHostController,
    carritoViewModel: CarritoViewModel,
    settingsVm: SettingsViewModel,
    context: Context = LocalContext.current
) {
    val isDark by settingsVm.darkMode.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    var productosJson by remember { mutableStateOf<List<ClaseProductos>>(emptyList()) }
    // Cargar productos desde JSON una sola vez
    LaunchedEffect(Unit) {
        productosJson = loadProductosFromJson(context)
    }

    val carrito by carritoViewModel.carrito.collectAsState()
    val totalEnCarrito = carrito.sumOf { it.cantidad }

    val currentRoute = navController.currentBackStackEntryFlow
        .collectAsState(initial = navController.currentBackStackEntry)
        .value?.destination?.route ?: "home"

    val items = listOf(
        BottomItem("home", R.drawable.home, "Inicio"),
        BottomItem("home", R.drawable.menu, "Menú"),
        BottomItem(
            "carrito",
            R.drawable.shopping_cart,
            "Carrito",
            label = "CARRO",
            showLabelAlways = true,
            badgeCount = totalEnCarrito,
            iconSize = 50.dp,
            labelFontSize = 16.sp,
            itemWidth = 84.dp
        ),
        BottomItem("home", R.drawable.user, "Perfil"),
        BottomItem("home", R.drawable.figura, "Icono personalizado")
    )

    val todasCategorias = categoriasProductos + categoriasServicios

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            SearchTopBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = { q ->
                    // TODO: navega o filtra productos con "q"
                    // navController.navigate("buscar?query=${Uri.encode(q)}")
                },
                onVoiceClick = {
                    // TODO: integra reconocimiento de voz cuando quieras
                },
                isDark = isDark,
                onToggleDark = { settingsVm.toggleDark() },
                onOpenPerfil = { /*navController.navigate("perfil")*/ },
                onOpenConfig = { /*navController.navigate("config")*/ }
            )
        },
        bottomBar = {
            BottomHomeBar(
                items = items,
                selectedId = currentRoute,
                onItemSelected = { tapped ->
                    if (tapped.id != currentRoute) {
                        navController.navigate(tapped.id) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                        }
                    }
                },
                barHeight = 137.dp,
                itemWidth = 68.dp,
                iconSize = 40.dp,
                labelFontSize = 16.sp
            )
        }

    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {

            item {
                CarruselCategorias(
                    categorias = categoriasProductos + categoriasServicios,
                    onCategoriaClick = { categoria ->
                        navController.navigate("subcategorias/${categoria.nombreCat}")
                    }
                )
            }

            item {
                Text(
                    text = "¿Qué necesitas hoy?",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }

            todasCategorias.forEach { categoria ->
                item {
                    val productosFiltrados = productosJson
                        .filter { it.categoria.name == categoria.nombreCat || it.categoria.displayName == categoria.nombreCat }
                        .sortedByDescending { it.evaluacion }
                        .take(5)

                    CategoriaSection(
                        nombreCategoria = categoria.nombreCat,
                        productos = productosFiltrados,
                        onVerTodoClick = { navController.navigate("subcategorias/${categoria.nombreCat}") },
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun CategoriaSection(
    nombreCategoria: String,
    productos: List<ClaseProductos>,
    onVerTodoClick: () -> Unit,
    navController: NavHostController
) {
    val cs = MaterialTheme.colorScheme
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = nombreCategoria,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = cs.onBackground
            )
            Button(
                onClick = onVerTodoClick,
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(text = "Ver todo", fontSize = 18.sp)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(productos) { producto ->
                ProductoCard(
                    producto = producto,
                    onClick = {
                        navController.navigate("detalle/${producto.id}")

                    }
                )
            }
        }
    }
}

@Composable
fun ProductoCard(
    producto: ClaseProductos,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .size(width = 160.dp, height = 200.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary
        )

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
                //.background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            val drawableId = drawableMap[producto.imagenRes] ?: R.drawable.ej_alim
            Image(
                painter = painterResource(id = drawableId),
                contentDescription = producto.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = producto.nombre,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}