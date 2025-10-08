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
import androidx.compose.runtime.remember
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
    val totalEnCarrito by remember(carrito) { derivedStateOf { carrito.sumOf { it.cantidad } } }

    val subtotal: Long = carrito.sumOf { it.precio * it.cantidad }.toLong()
    val total: Long = viewModel.totalPrecio.toLong()

    val items = listOf(
        BottomItem("home", com.example.tatapp.R.drawable.home, "Inicio"),
        BottomItem("home", com.example.tatapp.R.drawable.menu, "Menú"),
        BottomItem(
            "carrito",
            com.example.tatapp.R.drawable.shopping_cart,
            "Carrito",
            label = "CARRO",
            showLabelAlways = true,
            badgeCount = totalEnCarrito,
            iconSize = 50.dp,
            labelFontSize = 16.sp,
            itemWidth = 84.dp
        ),
        BottomItem("home", com.example.tatapp.R.drawable.user, "Perfil"),
        BottomItem("home", R.drawable.figura, "Icono personalizado")
    )

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
                items = items,
                selectedId = "carrito",
                onItemSelected = { tapped ->
                    when (tapped.id) {
                        "home" -> navController.navigate("home")
                        "menu" -> navController.navigate("home")
                        "perfil" -> navController.navigate("home")
                        "icono" -> navController.navigate("home")
                        "carrito" -> navController.navigate("carrito")
                    }
                },
                barHeight = 137.dp,
                itemWidth = 68.dp,
                iconSize = 40.dp,
                labelFontSize = 16.sp
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

                /*Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Total: \$${NumberFormat.getNumberInstance(Locale.forLanguageTag("es-CL")).format(viewModel.totalPrecio)}",
                        fontSize = 22.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Button(
                        onClick = { /* Acción para pagar */ },
                        modifier = Modifier.weight(1f).height(50.dp)
                    ) { Text("Pagar", fontSize = 20.sp) }
                }*/
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