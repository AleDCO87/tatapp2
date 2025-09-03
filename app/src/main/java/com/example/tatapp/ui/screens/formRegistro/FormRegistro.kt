package com.example.tatapp.ui.screens.formRegistro

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tatapp.ui.components.PasswordField
import com.example.tatapp.ui.components.esRutValidoConFuncion
import com.example.tatapp.ui.components.formatearRUT
import com.example.tatapp.ui.components.rutCompleto
import kotlinx.coroutines.delay

@Composable
fun FormRegistro(
    navController: NavController,
    vm: FormRegistroViewModel = viewModel()
) {
    val focusManager = LocalFocusManager.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()

    // Mostrar mensajes generales
    /*LaunchedEffect(vm.mensaje) {
        vm.mensaje?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            vm.mensaje = null
        }
    }*/

    // Confirmación y navegación
    LaunchedEffect(vm.registroExitoso) {
        if (vm.registroExitoso) {
            snackbarHostState.showSnackbar("Registro Completado ✅")
            delay(800)
            navController.navigate("homeProductosScreen") {
                popUpTo("registro") { inclusive = true }
            }
            vm.registroExitoso = false
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Crear cuenta",
                style = MaterialTheme.typography.displayMedium
            )

            Spacer(Modifier.height(16.dp))

            // Nombre
            OutlinedTextField(
                value = vm.regNombre,
                onValueChange = { vm.onRegNombreChange(it) },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = vm.errorNombre != null,
                supportingText = {
                    vm.errorNombre?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
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
                supportingText = {
                    vm.errorApellido?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
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
                    } else {
                        vm.errorRut = null
                    }
                },
                label = { Text("RUT (12.345.678-9)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = vm.errorRut != null,
                supportingText = {
                    vm.errorRut?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
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
                supportingText = {
                    vm.errorCorreo?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
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
                supportingText = {
                    vm.errorTelefono?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                )
            )

            Spacer(Modifier.height(8.dp))

            // Contraseña
            PasswordField(
                value = vm.regPassword,
                onValueChange = {
                    vm.regPassword = it
                    vm.errorPassword = null
                },
                errorText = vm.errorPassword,
                confirmValue = vm.regConfirmarPassword,
                minLength = 6,
                modifier = Modifier.fillMaxWidth(),
                label = "Contraseña",

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
                supportingText = {
                    vm.errorConfirmPassword?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                trailingIcon = {
                    TextButton(onClick = { confirmReveal = !confirmReveal }) {
                        Text(if (confirmReveal) "Ocultar" else "Mostrar")
                    }
                }
            )

            Spacer(Modifier.height(16.dp))

            // Botón Registrar
            Button(
                onClick = { vm.validarFormulario() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrarse")
            }

            Spacer(Modifier.height(8.dp))

            TextButton(onClick = { navController.popBackStack() }) {
                Text("Cancelar")
            }
        }
    }
}