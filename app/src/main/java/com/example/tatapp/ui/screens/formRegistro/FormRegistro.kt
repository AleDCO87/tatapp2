package com.example.tatapp.ui.screens.formRegistro

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tatapp.R
import com.example.tatapp.ui.components.BottomHomeBar
import com.example.tatapp.ui.components.BottomItem
import com.example.tatapp.ui.components.PasswordField
import com.example.tatapp.ui.components.esRutValidoConFuncion
import com.example.tatapp.ui.components.formatearRUT
import com.example.tatapp.ui.components.rutCompleto
import com.example.tatapp.ui.screens.carrito.CarritoViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormRegistro(
    navController: NavController,
    vm: FormRegistroViewModel = viewModel(),
    viewModel: CarritoViewModel
) {
    val focusManager = LocalFocusManager.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()
    var selectedBottom by remember { mutableStateOf("registro") }
    val cartBadge by viewModel.totalEnCarrito.collectAsState()
    val snackbar = remember { SnackbarHostState() }
    val items = remember {
        listOf(
            BottomItem("home", com.example.tatapp.R.drawable.home, "Inicio", iconSize = 50.dp),
            BottomItem("menu", com.example.tatapp.R.drawable.menu, "Menú", iconSize = 45.dp),
            BottomItem("carrito", com.example.tatapp.R.drawable.carrito, "Carrito", iconSize = 40.dp),
            BottomItem("perfil", com.example.tatapp.R.drawable.perfil, "Perfil", iconSize = 40.dp),
            BottomItem("config", R.drawable.icon_tatapp, "Más", iconSize = 50.dp, tintIcon = false)
        )
    }

    // Confirmación y navegación
    LaunchedEffect(vm.registroExitoso) {
        if (vm.registroExitoso) {
            snackbarHostState.showSnackbar("Registro completado ✅")
            navController.navigate("homeProductosScreen") {
                popUpTo("registro") { inclusive = true }
            }
            vm.registroExitoso = false
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Registro", fontSize = 30.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Volver")
                    }
                }
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
        },
        snackbarHost = { SnackbarHost(hostState = snackbar) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Crear cuenta", style = MaterialTheme.typography.displayMedium)
            Spacer(Modifier.height(16.dp))

            // Nombre
            OutlinedTextField(
                value = vm.regNombre,
                onValueChange = { vm.onRegNombreChange(it) },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = vm.errorNombre != null,
                supportingText = { vm.errorNombre?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                colors= OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    errorLabelColor = MaterialTheme.colorScheme.error,
                )
            )

            Spacer(Modifier.height(8.dp))

            // Apellido
            OutlinedTextField(
                value = vm.regApellido,
                onValueChange = { vm.onRegApellidoChange(it) },
                label = { Text("Apellido") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = vm.errorApellido != null,
                supportingText = { vm.errorApellido?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                colors= OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    errorLabelColor = MaterialTheme.colorScheme.error,
                )
            )

            Spacer(Modifier.height(8.dp))

            // RUT
            OutlinedTextField(
                value = vm.regRut,
                onValueChange = { input ->
                    vm.regRut = input
                    if (rutCompleto(input)) {
                        if (esRutValidoConFuncion(input)) {
                            vm.errorRut = null
                            vm.regRut = formatearRUT(input)
                        } else {
                            vm.errorRut = "El rut es inválido"
                        }
                    } else vm.errorRut = null
                },
                label = { Text("RUT (12.345.678-9)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = vm.errorRut != null,
                supportingText = { vm.errorRut?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                colors= OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    errorLabelColor = MaterialTheme.colorScheme.error,
                )
            )

            Spacer(Modifier.height(8.dp))

            // Correo
            OutlinedTextField(
                value = vm.regCorreo,
                onValueChange = { vm.onRegCorreoChange(it) },
                label = { Text("ejemplo@dominio.com") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = vm.errorCorreo != null,
                supportingText = { vm.errorCorreo?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                colors= OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    errorLabelColor = MaterialTheme.colorScheme.error,
                )
            )

            Spacer(Modifier.height(8.dp))

            // Teléfono
            OutlinedTextField(
                value = vm.regTelefono,
                onValueChange = { vm.onRegTelefonoChange(it) },
                label = { Text("+56 9 1234 5678") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = vm.errorTelefono != null,
                supportingText = { vm.errorTelefono?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                colors= OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    errorLabelColor = MaterialTheme.colorScheme.error,
                )
            )

            Spacer(Modifier.height(8.dp))

            // Contraseña
            PasswordField(
                value = vm.regPassword,
                onValueChange = { vm.regPassword = it; vm.errorPassword = null },
                errorText = vm.errorPassword,
                confirmValue = vm.regConfirmarPassword,
                minLength = 6,
                modifier = Modifier.fillMaxWidth(),
                label = "Contraseña"
            )

            Spacer(Modifier.height(8.dp))

            // Confirmar contraseña
            var confirmReveal by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = vm.regConfirmarPassword,
                onValueChange = { vm.onRegConfirmarPasswordChange(it) },
                label = { Text("Confirmar contraseña") },
                visualTransformation = if (confirmReveal) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = vm.errorConfirmPassword != null,
                supportingText = { vm.errorConfirmPassword?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                trailingIcon = {
                    TextButton(onClick = { confirmReveal = !confirmReveal }) {
                        Text(if (confirmReveal) "Ocultar" else "Mostrar")
                    }
                },
                colors= OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    errorLabelColor = MaterialTheme.colorScheme.error,
                )
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { vm.registrarUsuario() },
                modifier = Modifier.fillMaxWidth(),
                enabled = !vm.isLoading
            ) {
                if (vm.isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text("Registrarse")
                }
            }

            Spacer(Modifier.height(8.dp))
            TextButton(onClick = { navController.popBackStack() }) {
                Text("Cancelar")
            }
        }
    }
}