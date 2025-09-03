package com.example.tatapp.ui.screens.formRegistro

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.tatapp.ui.components.esRutValidoConFuncion
import com.example.tatapp.ui.components.formatearRUT

class FormRegistroViewModel : ViewModel() {

    // ----> Campos del formulario <----
    var regNombre by mutableStateOf("")
    fun onRegNombreChange(value: String) { regNombre = value }

    var regApellido by mutableStateOf("")
    fun onRegApellidoChange(value: String) { regApellido = value }

    var regRut by mutableStateOf("")
    fun onRegRutChange(value: String) { regRut = value }

    var regCorreo by mutableStateOf("")
    fun onRegCorreoChange(value: String) { regCorreo = value }

    var regTelefono by mutableStateOf("")
    fun onRegTelefonoChange(value: String) { regTelefono = value }

    var regPassword by mutableStateOf("")
    fun onRegPasswordChange(value: String) { regPassword = value }

    var regConfirmarPassword by mutableStateOf("")
    fun onRegConfirmarPasswordChange(value: String) { regConfirmarPassword = value }


    // Mensaje general
    //var mensaje by mutableStateOf<String?>(null)


    // ----> Errores asociados a los campos <----
    var errorNombre by mutableStateOf<String?>(null)
    var errorApellido by mutableStateOf<String?>(null)
    var errorRut by mutableStateOf<String?>(null)
    var errorCorreo by mutableStateOf<String?>(null)
    var errorTelefono by mutableStateOf<String?>(null)
    var errorPassword by mutableStateOf<String?>(null)
    var errorConfirmPassword by mutableStateOf<String?>(null)

    // ----> Flag de registro exitoso <----
    var registroExitoso by mutableStateOf(false)

    fun validarFormulario() {
        // Reset errores
        errorNombre = null
        errorApellido = null
        errorRut = null
        errorCorreo = null
        errorTelefono = null
        errorPassword = null
        errorConfirmPassword = null

        var valido = true

        if (regNombre.isBlank()) {
            errorNombre = "El nombre es obligatorio"
            valido = false
        }

        if (regApellido.isBlank()) {
            errorApellido = "El apellido es obligatorio"
            valido = false
        }

        if (!esRutValidoConFuncion(regRut)) {
            errorRut = "El rut es inválido"
            valido = false
        } else {
            regRut = formatearRUT(regRut)
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(regCorreo).matches()) {
            errorCorreo = "El correo es inválido"
            valido = false
        }

        if (regTelefono.length < 8) {
            errorTelefono = "El teléfono es incorrecto"
            valido = false
        }

        if (regPassword.length < 6) {
            errorPassword = "La contraseña debe tener al menos 6 caracteres"
            valido = false
        }

        if (regPassword != regConfirmarPassword) {
            errorConfirmPassword = "Las contraseñas no coinciden"
            valido = false
        }

        if (valido) {
            registroExitoso = true
        }
    }
}