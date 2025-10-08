package com.example.tatapp.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tatapp.R
import com.example.tatapp.ui.components.BottomHomeBar
import com.example.tatapp.ui.components.BottomItem
import com.example.tatapp.ui.components.CarruselCategorias
import com.example.tatapp.ui.screens.homeProductos.CategoriaItem
import com.example.tatapp.ui.components.SearchTopBar
import com.example.tatapp.ui.screens.carrito.CarritoViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Home(
    navController: NavHostController,
    isDark: Boolean,
    onToggleDark: () -> Unit,
    carritoViewModel: CarritoViewModel
) {
    var query by remember { mutableStateOf("")}

    val totalEnCarrito = carritoViewModel.totalEnCarrito

    val categorias = remember {
        listOf(
            CategoriaItem("Alimentos",R.drawable.alimentos),
            CategoriaItem("Hogar",R.drawable.hogar),
            CategoriaItem("Salud",R.drawable.salud),
            CategoriaItem("Mascotas", R.drawable.mascotas),
        )
    }

    // bottom bar state
    var selectedBottom by remember { mutableStateOf("home") }
    val bottomItems = listOf(
        BottomItem("home", R.drawable.home, "Inicio", iconSize = 40.dp),
        BottomItem("homeProductosScreen", R.drawable.menu, "Menú", iconSize = 40.dp),
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
        BottomItem("home", R.drawable.user, "Perfil", iconSize = 40.dp),
        BottomItem("home", R.drawable.figura, "Icono personalizado", iconSize = 40.dp)
    )

    Scaffold(
        topBar = {
            SearchTopBar(
                query = query,
                onQueryChange = { query = it },
                onSearch = { /* TODO: ejecutar búsqueda con query */ },
                onVoiceClick = { /* TODO mic */ },
                isDark = isDark,
                onToggleDark = onToggleDark,
                onOpenPerfil = { navController.navigate("perfil") },
                onOpenConfig = { navController.navigate("config") }
            )
        },
        bottomBar = {
            BottomHomeBar(
                items = bottomItems,
                selectedId = selectedBottom,
                onItemSelected = { item ->
                    selectedBottom = item.id
                    when (item.id) {
                        "home"   -> navController.navigate("home")
                        "homeProductosScreen"   -> navController.navigate("homeProductosScreen")
                        "carrito"   -> navController.navigate("carrito")
                        "perfil"-> navController.navigate("perfil")
                        "config"   -> navController.navigate("config")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(8.dp))

            // Carrusel de banners
            val banners = listOf(
                R.drawable.figura,
                R.drawable.shopping_cart,
                R.drawable.user
            )
            BannerCarousel(images = banners, height = 500.dp)

            Spacer(Modifier.height(16.dp))

            // Título + carrusel de categorías (productos)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Explora por categoría", style = MaterialTheme.typography.titleMedium)
            }

            Spacer(Modifier.height(8.dp))

            CarruselCategorias(
                categorias = categorias,
                onCategoriaClick = { categoria ->
                    // navega a tu flujo existente
                    navController.navigate("subcategorias/${categoria.nombreCat}")
                }
            )

            Spacer(Modifier.height(16.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BannerCarousel(
    images: List<Int>,
    height: Dp = 320.dp
) {
    val pagerState = rememberPagerState(pageCount = { images.size })

    Box(modifier = Modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 24.dp),
            pageSpacing = 12.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
        ) { page ->
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(images[page]),
                    contentDescription = "Banner $page",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        // Indicadores (•••)
        Row(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pagerState.pageCount) { index ->
                val selected = pagerState.currentPage == index
                Box(
                    Modifier
                        .size(if (selected) 8.dp else 6.dp)
                        .clip(CircleShape)
                        .background(
                            if (selected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.outline
                        )
                )
            }
        }
    }
}
