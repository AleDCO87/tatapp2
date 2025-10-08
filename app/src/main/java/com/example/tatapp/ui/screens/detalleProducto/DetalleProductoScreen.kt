package com.example.tatapp.ui.screens.detalleProducto

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tatapp.data.modelo.entity.CarritoEntity
import com.example.tatapp.ui.components.drawableMap
import com.example.tatapp.ui.screens.carrito.CarritoViewModel
import com.example.tatapp.data.clases.ClaseProductos
import kotlinx.coroutines.launch
import com.example.tatapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleProductoScreen(
    navController: NavHostController,
    carritoViewModel: CarritoViewModel,
    producto: ClaseProductos
) {

    val carrito by carritoViewModel.carrito.collectAsState()
    val totalEnCarrito by remember(carrito) { derivedStateOf { carrito.sumOf { it.cantidad } } }

    var cantidad by remember { mutableStateOf(1) }
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detalle", fontSize = 30.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("carrito") }) {
                        BadgedBox(
                            badge = {
                                if (carritoViewModel.totalEnCarrito > 0) {
                                    Badge (
                                        containerColor = Color.Red,
                                        contentColor = Color.White
                                    ){ Text(totalEnCarrito.toString()) }
                                }
                            }
                        ) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                        }
                    }
                },
                modifier = Modifier.statusBarsPadding(),
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,            // Fondo
                    titleContentColor = MaterialTheme.colorScheme.onSurface,       // Texto
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface, // Iconos izq.
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface   // Iconos der.
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val drawableId = drawableMap[producto.imagenRes] ?: com.example.tatapp.R.drawable.ej_alim
            Image(
                painter = painterResource(id = drawableId),
                contentDescription = producto.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )

            Text(producto.nombre, fontSize = 28.sp, color = Color.Black)
            Text(producto.descripcion, fontSize = 20.sp, color = Color.DarkGray)
            Text("Evaluación: ${producto.evaluacion}", fontSize = 20.sp)

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { if (cantidad > 1) cantidad-- }, modifier = Modifier.size(50.dp)) {
                    Text("-", fontSize = 20.sp)
                }
                Text("$cantidad", fontSize = 30.sp)
                Button(onClick = { cantidad++ }, modifier = Modifier.size(50.dp)) {
                    Text("+", fontSize = 20.sp)
                }
            }

            Button(
                onClick = {
                    scope.launch {
                        val drawableId = drawableMap[producto.imagenRes] ?: R.drawable.ej_alim
                        val nuevoProducto = CarritoEntity(
                            id = producto.id,
                            nombre = producto.nombre,
                            precio = producto.precio,
                            cantidad = cantidad,
                            imagenRes = drawableId
                        )
                        carritoViewModel.agregarAlCarrito(nuevoProducto)
                    }
                },
                modifier = Modifier.fillMaxWidth().height(60.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Agregar al Carrito", fontSize = 24.sp)
            }
        }
    }
}