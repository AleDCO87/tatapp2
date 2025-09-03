package com.example.tatapp.ui.screens.homeProductos

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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tatapp.R
import com.example.tatapp.ui.screens.productos.ClaseProductos
import com.example.tatapp.ui.screens.productos.productosBase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeProductosScreen(navController: NavHostController) {

    var selectedItem by remember { mutableStateOf(0) }
    val todasCategorias = categoriasProductos + categoriasServicios

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("TatApp", fontSize = 30.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Perfil")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("carrito")  }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                    }
                },
                modifier = Modifier.statusBarsPadding()
            )
        },
        bottomBar = {
            Surface(
                tonalElevation = 4.dp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.navigationBarsPadding()
            ) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(todasCategorias) { index, categoria ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clickable {
                                    selectedItem = index
                                    navController.navigate("subcategorias/${categoria.nombreCat}")
                                }
                                .padding(vertical = 4.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = categoria.iconoCat),
                                contentDescription = categoria.nombreCat,
                                modifier = Modifier.size(28.dp),
                                tint = Color.White
                            )
                            Text(
                                text = categoria.nombreCat,
                                fontSize = 12.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F8F8))
                .padding(paddingValues),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {

            item {
                Text(
                    text = "¿Qué necesitas hoy?",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }

            // Mostrar secciones para cada categoría
            todasCategorias.forEach { categoria ->
                item {
                    CategoriaSection(
                        nombreCategoria = categoria.nombreCat,
                        productos = productosBase.filter { it.categoria.name == categoria.nombreCat || it.categoria.displayName == categoria.nombreCat },
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
                color = Color(0xFF222222)
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
                ProductoCard(nombre = producto.nombre) {
                    navController.navigate("productos") // Puedes agregar categoría/subcategoría si deseas
                }
            }
        }
    }
}

@Composable
fun ProductoCard(nombre: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .size(width = 160.dp, height = 200.dp)
            .clickable {  },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.ej_alim),
                contentDescription = nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = nombre,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF222222),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}
