package com.example.tatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tatapp.data.modelo.database.AppDatabase
import com.example.tatapp.ui.screens.carrito.CarritoScreen
import com.example.tatapp.ui.screens.carrito.CarritoViewModel
import com.example.tatapp.ui.screens.carrito.CarritoViewModelFactory
import com.example.tatapp.ui.screens.detalleProducto.DetalleProductoScreen
import com.example.tatapp.ui.screens.formRegistro.FormRegistro
import com.example.tatapp.ui.screens.home.Home
import com.example.tatapp.ui.screens.homeProductos.HomeProductosScreen
import com.example.tatapp.ui.screens.homeProductos.loadProductosFromJson
import com.example.tatapp.ui.screens.loggin.LoginScreen
import com.example.tatapp.data.clases.ClaseProductos
import com.example.tatapp.ui.screens.productos.ProductosScreen
import com.example.tatapp.ui.screens.subcategorias.SubCategoriasScreen
import com.example.tatapp.ui.theme.TatappTheme
import com.example.tatapp.viewmodel.SettingsViewModel
import com.example.tatapp.viewmodel.SettingsViewModelFactory
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {

    private lateinit var carritoViewModel: CarritoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { false }
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()

        // Crear DB y ViewModel con Factory
        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_db").build()
        val carritoFactory = CarritoViewModelFactory(db.carritoDao())
        carritoViewModel = ViewModelProvider(this, carritoFactory)[CarritoViewModel::class.java]

        setContent {
            val settingsVm: SettingsViewModel =
                androidx.lifecycle.viewmodel.compose.viewModel(
                    factory = SettingsViewModelFactory(application)
                )

            val isDark by settingsVm.darkMode.collectAsState()

            TatappTheme(darkTheme = isDark, dynamicColor = false) {
                val navController = rememberNavController()
                val context = LocalContext.current

                // Estado para almacenar todos los productos cargados desde JSON
                var productosJson by remember { mutableStateOf<List<ClaseProductos>>(emptyList()) }

                // Cargar productos desde JSON solo una vez
                LaunchedEffect(Unit) {
                    productosJson = loadProductosFromJson(context)
                }

                NavHost(navController = navController, startDestination = "homeProductosScreen") {

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
                    ) { backStackEntry ->
                        val categoria = backStackEntry.arguments?.getString("categoria") ?: ""
                        SubCategoriasScreen(navController = navController, categoria = categoria)
                    }

                    composable(
                        "productos/{categoria}/{subcategoria}",
                        arguments = listOf(
                            navArgument("categoria") { type = NavType.StringType },
                            navArgument("subcategoria") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val categoria = backStackEntry.arguments?.getString("categoria") ?: ""
                        val subcategoria = backStackEntry.arguments?.getString("subcategoria") ?: ""
                        ProductosScreen(
                            navController = navController,
                            carritoDao = db.carritoDao(),
                            categoria = categoria,
                            subcategoria = subcategoria
                        )
                    }

                    composable("carrito") {
                        CarritoScreen(navController = navController, viewModel = carritoViewModel)
                    }

                    composable(
                        "detalle/{productoId}",
                        arguments = listOf(navArgument("productoId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val productoId = backStackEntry.arguments?.getString("productoId") ?: ""
                        val producto = productosJson.find { it.id == productoId }

                        if (producto != null) {
                            DetalleProductoScreen(
                                navController = navController,
                                carritoViewModel = carritoViewModel,
                                producto = producto
                            )
                        } else {
                            // Opcional: pantalla de error o mensaje de producto no encontrado
                            // Puedes reemplazar con un Composable específico
                        }
                    }

                    composable("home") {
                        val isDark by settingsVm.darkMode.collectAsState()
                        Home(
                            navController = navController,
                            isDark = isDark,
                            onToggleDark = { settingsVm.toggleDark() },
                            carritoViewModel = carritoViewModel
                        )
                    }

                    composable("login") {
                        LoginScreen(navController)
                    }

                    //composable("registro") {
                        //RegistroScreen(navController)
                    //}

                    composable("registro") { FormRegistro(navController) }
                }
            }
        }
    }
}