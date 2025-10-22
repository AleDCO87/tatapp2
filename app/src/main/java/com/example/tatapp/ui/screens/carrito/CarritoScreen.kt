package com.example.tatapp.ui.screens.carrito

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tatapp.R
import com.example.tatapp.data.modelo.entity.CarritoEntity
import com.example.tatapp.ui.components.BottomHomeBar
import com.example.tatapp.ui.components.BottomItem
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(navController: NavHostController, viewModel: CarritoViewModel) {
    val carrito by viewModel.carrito.collectAsState()
    var selectedBottom by remember { mutableStateOf("carrito") }
    //val totalEnCarrito by remember(carrito) { derivedStateOf { carrito.sumOf { it.cantidad } } }
    val cartBadge by viewModel.totalEnCarrito.collectAsState()

    val subtotal: Long = carrito.sumOf { it.precio * it.cantidad }.toLong()
    val total: Long = viewModel.totalPrecio.value.toLong()

    val items = remember {
        listOf(
            BottomItem("home", R.drawable.home, "Inicio", iconSize = 50.dp),
            BottomItem("menu", R.drawable.menu, "Menú", iconSize = 45.dp),
            BottomItem("carrito", R.drawable.carrito, "Carrito", iconSize = 40.dp),
            BottomItem("perfil", R.drawable.perfil, "Perfil", iconSize = 40.dp),
            BottomItem("config", R.drawable.icon_tatapp, "Más", iconSize = 50.dp, tintIcon = false)
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Carrito", fontSize = 30.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,            // Fondo
                    titleContentColor = MaterialTheme.colorScheme.onSurface,       // Texto
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface, // Iconos izq.
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface   // Iconos der.
                )
            )
        },
        bottomBar = {
            BottomHomeBar(
                items = items.map { if (it.id == "carrito") it.copy(badgeCount = cartBadge) else it },
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxSize()
        ) {

            if (carrito.isEmpty()) {
                Text(
                    "El carrito está vacío",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(carrito) { item ->
                        CarritoItemRow(item, viewModel)
                    }
                }

                Spacer(Modifier.height(12.dp))

                ResumenPedidoCard(
                    subtotal = subtotal,
                    total = total,
                    onPagar = { /* TODO: flujo de pago */ },
                    onVaciar = { viewModel.vaciarCarrito() }
                )
            }
        }
    }
}

@Composable
fun CarritoItemRow(item: CarritoEntity, viewModel: CarritoViewModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = item.imagenRes),
                contentDescription = item.nombre,
                modifier = Modifier.size(70.dp)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(item.nombre, fontSize = 22.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    FilledIconButton(
                        onClick = { viewModel.disminuirCantidad(item) },
                        modifier = Modifier.size(40.dp)
                    ) { Text("-", fontSize = 20.sp) }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("${item.cantidad}", fontSize = 22.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    FilledIconButton(
                        onClick = { viewModel.aumentarCantidad(item) },
                        modifier = Modifier.size(40.dp)
                    ) { Text("+", fontSize = 20.sp) }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Subtotal: \$${NumberFormat.getNumberInstance(Locale.forLanguageTag("es-CL")).format(item.precio * item.cantidad)}",
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }

            FilledIconButton(
                onClick = { viewModel.eliminarProducto(item) },
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                )
            ) {
                Icon(Icons.Rounded.Delete, contentDescription = "Eliminar producto")
            }
        }
    }
}