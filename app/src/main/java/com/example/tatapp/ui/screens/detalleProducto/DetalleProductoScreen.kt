package com.example.tatapp.ui.screens.detalleProducto

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tatapp.modelo.dao.CarritoDao
import com.example.tatapp.ui.screens.productos.ProductosViewModel
import com.example.tatapp.ui.screens.productos.productosBase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleProductoScreen(
    navController: NavHostController,
    carritoDao: CarritoDao,
    productoId: String
) {
    val productosViewModel = remember { ProductosViewModel(carritoDao, null, "") }
    val producto = productosBase.find { it.id == productoId } ?: return

    var cantidad by remember { mutableStateOf(1) }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detalle", fontSize = 30.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, "Volver")
                    }
                }
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
            Image(
                painter = painterResource(producto.imagenRes),
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
                Button(
                    onClick = { if (cantidad > 1) cantidad-- },
                    modifier = Modifier.size(50.dp)
                ) { Text("-", fontSize = 20.sp) }
                Text("$cantidad", fontSize = 30.sp)
                Button(
                    onClick = { cantidad++ },
                    modifier = Modifier.size(50.dp)
                ) { Text("+", fontSize = 20.sp) }
            }

            Button(
                onClick = { scope.launch { productosViewModel.agregarAlCarrito(producto, cantidad) } },
                modifier = Modifier.fillMaxWidth().height(60.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Agregar al Carrito", fontSize = 24.sp)
            }
        }
    }
}