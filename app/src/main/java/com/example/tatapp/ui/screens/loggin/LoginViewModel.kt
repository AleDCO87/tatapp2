package com.example.tatapp.ui.screens.loggin

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tatapp.data.repositorio.AuthRepository
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepo: AuthRepository = AuthRepository()
) : ViewModel() {

    var email by mutableStateOf("")
    var emailError by mutableStateOf<String?>(null)

    var password by mutableStateOf("")
    var passwordError by mutableStateOf<String?>(null)

    var isLoading by mutableStateOf(false)
    var success by mutableStateOf(false)

    // Nuevo: para almacenar el displayName del usuario
    var displayName by mutableStateOf<String?>(null)

    fun onEmailChange(value: String) {
        email = value
        emailError = null
    }

    fun onPasswordChange(value: String) {
        password = value
        passwordError = null
    }

    fun resetSuccess() {
        success = false
        displayName = null
    }

    private fun validarCampos(): Boolean {
        var valido = true
        emailError = null
        passwordError = null

        if (email.isBlank()) {
            emailError = "El correo es obligatorio"
            valido = false
        } else if (!email.contains("@")) {
            emailError = "Correo inválido"
            valido = false
        }

        if (password.isBlank()) {
            passwordError = "La contraseña es obligatoria"
            valido = false
        }

        return valido
    }

    fun login() {
        if (!validarCampos()) return

        isLoading = true
        viewModelScope.launch {
            try {
                val user = authRepo.login(email, password)
                displayName = user?.displayName
                success = true
            } catch (e: Exception) {
                when (e) {
                    is FirebaseAuthInvalidUserException -> emailError = "Usuario no registrado"
                    is FirebaseAuthInvalidCredentialsException -> passwordError = "Contraseña incorrecta"
                    else -> passwordError = e.message
                }
            } finally {
                isLoading = false
            }
        }
    }

    fun logout() {
        authRepo.logout()
    }
}