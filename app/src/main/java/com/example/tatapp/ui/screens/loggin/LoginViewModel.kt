package com.example.tatapp.ui.screens.loggin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class LoginViewModel : ViewModel() {

    var email by mutableStateOf("")
    var emailError by mutableStateOf<String?>(null)

    var password by mutableStateOf("")
    var passwordError by mutableStateOf<String?>(null)

    var isLoading by mutableStateOf(false)
    var success by mutableStateOf(false)

    //private val firebaseAuth = FirebaseAuth.getInstance()

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
    }

    fun login() {
        // Validación simple
        if (email.isBlank()) {
            emailError = "El correo es obligatorio"
            return
        }
        if (!email.contains("@")) {
            emailError = "Correo inválido"
            return
        }
        if (password.isBlank()) {
            passwordError = "La contraseña es obligatoria"
            return
        }

        isLoading = true
        viewModelScope.launch {
            try {
                // Simulación de login
                delay(1200)

                // Login con Firebase (descomentar cuando quieras usarlo)
                // firebaseAuth.signInWithEmailAndPassword(email, password).await()

                isLoading = false
                success = true
            } catch (e: Exception) {
                isLoading = false
                passwordError = e.message
            }
        }
    }
}
