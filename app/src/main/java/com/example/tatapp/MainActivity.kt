package com.example.tatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.tatapp.data.modelo.database.AppDatabase
import com.example.tatapp.data.remote.NetworkModule
import com.example.tatapp.data.remote.services.CatalogService
import com.example.tatapp.data.remote.services.CartService
import com.example.tatapp.data.repository.CatalogRepository
import com.example.tatapp.data.repository.CartRepository
import com.example.tatapp.ui.screens.carrito.CarritoScreen
import com.example.tatapp.ui.screens.carrito.CarritoViewModel
import com.example.tatapp.ui.screens.carrito.CarritoViewModelFactory
import com.example.tatapp.ui.screens.detalleProducto.DetalleProductoNetworkScreen
import com.example.tatapp.ui.screens.detalleProducto.DetalleNetworkViewModel
import com.example.tatapp.ui.screens.formRegistro.FormRegistro
import com.example.tatapp.ui.screens.home.Home
import com.example.tatapp.ui.screens.homeProductos.HomeProductosScreen
import com.example.tatapp.ui.screens.productos.ProductosScreen
import com.example.tatapp.ui.screens.subcategorias.SubCategoriasScreen
import com.example.tatapp.ui.theme.TatappTheme
import com.example.tatapp.viewmodel.SettingsViewModel
import com.example.tatapp.viewmodel.SettingsViewModelFactory
import retrofit2.create

class MainActivity : ComponentActivity() {

    private lateinit var carritoViewModel: CarritoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        splash.setKeepOnScreenCondition { false }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_db").build()
        val carritoFactory = CarritoViewModelFactory(db.carritoDao())
        carritoViewModel = ViewModelProvider(this, carritoFactory)[CarritoViewModel::class.java]

        setContent {
            val settingsVm: SettingsViewModel =
                androidx.lifecycle.viewmodel.compose.viewModel(factory = SettingsViewModelFactory(application))
            val isDark by settingsVm.darkMode.collectAsState()

            // Retrofit base
            val client = remember { NetworkModule.okHttp { null } }
            val retrofit = remember { NetworkModule.retrofit(client) }
            val catalogRepo = remember { CatalogRepository(retrofit.create<CatalogService>()) }
            val cartRepo = remember { CartRepository(retrofit.create<CartService>()) }

            // Detalle VM simple (si prefieres Factory, lo cambiamos luego)
            val detalleVm = remember { com.example.tatapp.ui.screens.detalleProducto.DetalleNetworkViewModel(catalogRepo) }

            TatappTheme(darkTheme = isDark, dynamicColor = false) {
                val navController = rememberNavController()

                NavHost(navController, startDestination = "homeProductosScreen") {

                    composable("homeProductosScreen") {
                        HomeProductosScreen(
                            navController = navController,
                            settingsVm = settingsVm,
                            carritoViewModel = carritoViewModel
                        )
                    }

                    composable(
                        "subcategorias/{categoria}",
                        arguments = listOf(navArgument("categoria") { type = NavType.StringType })
                    ) { backStack ->
                        val categoria = backStack.arguments?.getString("categoria") ?: ""
                        SubCategoriasScreen(navController, categoria)
                    }

                    composable(
                        "productos/{categoria}/{subcategoria}",
                        arguments = listOf(
                            navArgument("categoria") { type = NavType.StringType },
                            navArgument("subcategoria") { type = NavType.StringType }
                        )
                    ) { backStack ->
                        val categoria = backStack.arguments?.getString("categoria") ?: ""
                        val subcategoria = backStack.arguments?.getString("subcategoria") ?: ""
                        ProductosScreen(navController, db.carritoDao(), categoria, subcategoria)
                    }

                    composable("carrito") {
                        CarritoScreen(navController, carritoViewModel)
                    }

                    composable(
                        "detalle/{productoId}",
                        arguments = listOf(navArgument("productoId") { type = NavType.StringType })
                    ) { backStack ->
                        val id = backStack.arguments?.getString("productoId") ?: return@composable
                        DetalleProductoNetworkScreen(
                            navController = navController,
                            vm = detalleVm,
                            productoId = id
                        ) { p, onAdd ->
                            // UI simple; puedes sustituir por tu DetalleProductoScreen custom
                            Column(Modifier.padding(16.dp)) {
                                Text(p.name, style = MaterialTheme.typography.titleLarge)
                                Text("$${"%.0f".format(p.price)}", style = MaterialTheme.typography.titleMedium)
                                Spacer(Modifier.height(12.dp))
                                Button(onClick = onAdd) { Text("Agregar al carrito") }
                            }
                        }
                    }

                    composable("home") {
                        val dark by settingsVm.darkMode.collectAsState()
                        Home(
                            navController = navController,
                            isDark = dark,
                            onToggleDark = { settingsVm.toggleDark() },
                            carritoViewModel = carritoViewModel
                        )
                    }

                    composable("registro") {
                        FormRegistro(
                            navController = navController,
                            viewModel = carritoViewModel
                        )
                    }
                }
            }
        }
    }
}
