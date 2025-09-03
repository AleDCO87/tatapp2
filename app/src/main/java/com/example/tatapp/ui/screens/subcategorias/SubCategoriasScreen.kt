package com.example.tatapp.ui.screens.subcategorias

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubCategoriasScreen(
    navController: NavHostController,
    categoria: String
) {
    // Lista de subcategorías según la categoría seleccionada
    val subcategorias = subcategoriasMap[categoria] ?: emptyList()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(categoria, fontSize = 30.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("homeProductosScreen") }) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F8F8))
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(subcategorias) { subcategoria ->
                Card(
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clickable {
                            // Navegar a ProductosScreen con categoría y subcategoría
                            navController.navigate("productos/${categoria}/${subcategoria}")
                        },
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = subcategoria,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF222222)
                        )
                    }
                }
            }
        }
    }
}