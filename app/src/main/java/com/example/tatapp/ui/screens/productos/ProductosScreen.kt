package com.example.tatapp.ui.screens.productos

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tatapp.modelo.dao.CarritoDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductosScreen(
    navController: NavHostController,
    carritoDao: CarritoDao,
    categoria: String,
    subcategoria: String
) {
    val productosViewModel: ProductosViewModel = viewModel(
        factory = ProductosViewModelFactory(
            carritoDao = carritoDao,
            categoriaSeleccionada = CategoriaProducto.values()
                .find { it.name.equals(categoria, true) },
            subcategoriaSeleccionada = subcategoria
        )
    )

    val carrito by carritoDao.obtenerCarrito().collectAsState(initial = emptyList())
    val totalEnCarrito = carrito.sumOf { it.cantidad }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Productos", fontSize = 30.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, "Volver")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Rounded.Menu, contentDescription = "Productos") },
                    label = { Text("Productos", fontSize = 18.sp) },
                    selected = true,
                    onClick = {}
                )
                NavigationBarItem(
                    icon = {
                        BadgedBox(
                            badge = {
                                if (totalEnCarrito > 0) Badge { Text("$totalEnCarrito") }
                            }
                        ) {
                            Icon(Icons.Rounded.ShoppingCart, contentDescription = "Carrito")
                        }
                    },
                    label = { Text("Carrito", fontSize = 18.sp) },
                    selected = false,
                    onClick = { navController.navigate("carrito") }
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(productosViewModel.productosFiltrados) { producto ->
                ProductoItemRow(
                    producto = producto,
                    productosViewModel = productosViewModel,
                    scope = scope,
                    navController = navController // <- PASAMOS EL NAVCONTROLLER
                )
            }
        }
    }
}

@Composable
fun ProductoItemRow(
    producto: ClaseProductos,
    productosViewModel: ProductosViewModel,
    scope: CoroutineScope,
    navController: NavHostController
) {
    var cantidad by remember { mutableStateOf(1) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA)),
        border = BorderStroke(2.dp, Color.Gray)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clickable {
                        navController.navigate("detalle/${producto.id}")
                    }
            ) {
                Image(
                    painter = painterResource(id = producto.imagenRes),
                    contentDescription = producto.nombre,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = producto.nombre,
                    fontSize = 22.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopStart)
                        .background(Color.Black.copy(0.5f), RoundedCornerShape(4.dp))
                        .padding(6.dp, 2.dp)
                )

                Text(
                    text = "\$${NumberFormat.getNumberInstance(Locale.forLanguageTag("es-CL")).format(producto.precio)}",
                    fontSize = 27.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.BottomEnd)
                        .background(Color.Black.copy(0.5f), RoundedCornerShape(4.dp))
                        .padding(6.dp, 2.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = { if (cantidad > 1) cantidad-- }, modifier = Modifier.size(50.dp)) { Text("-", fontSize=20.sp) }
                    Text("$cantidad", fontSize = 30.sp)
                    Button(onClick = { cantidad++ }, modifier = Modifier.size(50.dp)) { Text("+", fontSize=20.sp) }
                }

                Button(
                    onClick = { scope.launch { productosViewModel.agregarAlCarrito(producto, cantidad) } },
                    modifier = Modifier.height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Agregar", fontSize = 24.sp)
                }
            }
        }
    }
}