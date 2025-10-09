package com.example.tatapp.ui.screens.formRegistro

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tatapp.data.repositorio.AuthRepository
import com.example.tatapp.ui.components.esRutValidoConFuncion
import com.example.tatapp.ui.components.formatearRUT
import kotlinx.coroutines.launch

class FormRegistroViewModel(
    private val authRepo: AuthRepository = AuthRepository()
) : ViewModel() {

    // ----> Campos del formulario <----
    var regNombre by mutableStateOf("")
    var regApellido by mutableStateOf("")
    var regRut by mutableStateOf("")
    var regCorreo by mutableStateOf("")
    var regTelefono by mutableStateOf("")
    var regPassword by mutableStateOf("")
    var regConfirmarPassword by mutableStateOf("")

    // ----> Errores asociados a los campos <----
    var errorNombre by mutableStateOf<String?>(null)
    var errorApellido by mutableStateOf<String?>(null)
    var errorRut by mutableStateOf<String?>(null)
    var errorCorreo by mutableStateOf<String?>(null)
    var errorTelefono by mutableStateOf<String?>(null)
    var errorPassword by mutableStateOf<String?>(null)
    var errorConfirmPassword by mutableStateOf<String?>(null)

    // ----> Flags de UI <----
    var registroExitoso by mutableStateOf(false)
    var isLoading by mutableStateOf(false)

    // ----> Funciones de cambio
    fun onRegNombreChange(value: String) { regNombre = value; errorNombre = null }
    fun onRegApellidoChange(value: String) { regApellido = value; errorApellido = null }
    fun onRegRutChange(value: String) { regRut = value; errorRut = null }
    fun onRegCorreoChange(value: String) { regCorreo = value; errorCorreo = null }
    fun onRegTelefonoChange(value: String) { regTelefono = value; errorTelefono = null }
    fun onRegPasswordChange(value: String) { regPassword = value; errorPassword = null }
    fun onRegConfirmarPasswordChange(value: String) { regConfirmarPassword = value; errorConfirmPassword = null }

    /** Valida los campos localmente */
    private fun validarCampos(): Boolean {
        var valido = true
        errorNombre = if (regNombre.isBlank()) { valido = false; "El nombre es obligatorio" } else null
        errorApellido = if (regApellido.isBlank()) { valido = false; "El apellido es obligatorio" } else null
        errorRut = if (!esRutValidoConFuncion(regRut)) { valido = false; "RUT inválido" } else null
        if (errorRut == null) regRut = formatearRUT(regRut)
        errorCorreo = if (!android.util.Patterns.EMAIL_ADDRESS.matcher(regCorreo).matches()) { valido = false; "Correo inválido" } else null
        errorTelefono = if (regTelefono.length < 8) { valido = false; "Teléfono incorrecto" } else null
        errorPassword = if (regPassword.length < 6) { valido = false; "Contraseña menor a 6 caracteres" } else null
        errorConfirmPassword = if (regPassword != regConfirmarPassword) { valido = false; "Contraseñas no coinciden" } else null
        return valido
    }

    /** Registra el usuario usando AuthRepository */
    fun registrarUsuario() {
        if (!validarCampos()) return

        isLoading = true
        viewModelScope.launch {
            try {
                authRepo.register(
                    nombre = regNombre,
                    apellido = regApellido,
                    email = regCorreo,
                    password = regPassword
                )
                registroExitoso = true
            } catch (e: Exception) {
                when {
                    e.message?.contains("correo") == true -> errorCorreo = e.message
                    e.message?.contains("contraseña") == true -> errorPassword = e.message
                    else -> errorPassword = e.message ?: "Error desconocido"
                }
            } finally {
                isLoading = false
            }
        }
    }
}
