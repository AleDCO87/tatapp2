package com.example.tatapp.ui.screens.loggin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tatapp.R
import com.example.tatapp.data.repositorio.AuthRepository
import com.example.tatapp.ui.screens.datosUsuario.DatosUsuarioScreen
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel()
) {
    val authRepo = remember { AuthRepository() }
    val currentUser = authRepo.currentUser()

    // Si hay un usuario logueado, mostrar pantalla de bienvenida
    if (currentUser != null) {
        DatosUsuarioScreen(
            displayName = currentUser.displayName ?: "Usuario",
            onLogout = {
                viewModel.logout()
                navController.navigate("login") {
                    popUpTo("homeProductosScreen") { inclusive = true }
                }
            }
        )
        return
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Login", fontSize = 30.sp) },
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
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(32.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ico_tatapp),
                    contentDescription = "Logo Tatapp",
                    modifier = Modifier.size(140.dp)
                )

                Text(
                    text = "Bienvenido",
                    fontSize = 26.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                OutlinedTextField(
                    value = viewModel.email,
                    onValueChange = { viewModel.onEmailChange(it) },
                    label = { Text("Correo electrónico") },
                    singleLine = true,
                    isError = viewModel.emailError != null,
                    supportingText = {
                        viewModel.emailError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !viewModel.isLoading,
                    colors= OutlinedTextFieldDefaults.colors(
                        focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                        errorLabelColor = MaterialTheme.colorScheme.error,
                    )
                )

                OutlinedTextField(
                    value = viewModel.password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    isError = viewModel.passwordError != null,
                    supportingText = {
                        viewModel.passwordError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !viewModel.isLoading,
                    colors= OutlinedTextFieldDefaults.colors(
                        focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                        errorLabelColor = MaterialTheme.colorScheme.error,
                    )
                )

                Button(
                    onClick = { viewModel.login() },
                    enabled = !viewModel.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    if (viewModel.isLoading) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text("Iniciar sesión", fontSize = 22.sp)
                    }
                }

                TextButton(onClick = { navController.navigate("registro") }) {
                    Text("¿No tienes cuenta? Regístrate", fontSize = 18.sp)
                }
            }
        }
    }

    // Navegación al login exitoso
    if (viewModel.success) {
        LaunchedEffect(Unit) {
            navController.navigate("homeProductosScreen") {
                popUpTo("login") { inclusive = true }
            }
            viewModel.resetSuccess()
        }
    }
}