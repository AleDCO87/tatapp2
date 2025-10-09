package com.example.tatapp.data.repositorio

import com.google.firebase.auth.*
import kotlinx.coroutines.tasks.await

class AuthRepository(private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()) {

    // Login con email y contraseña
    suspend fun login(email: String, password: String): FirebaseUser? {
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            return result.user
        } catch (e: FirebaseAuthInvalidUserException) {
            throw Exception("Usuario no registrado")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            throw Exception("Contraseña incorrecta")
        } catch (e: Exception) {
            throw Exception(e.message ?: "Error desconocido al iniciar sesión")
        }
    }

    // Registro con email y contraseña + displayName
    suspend fun register(
        nombre: String,
        apellido: String,
        email: String,
        password: String
    ): FirebaseUser? {
        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user
            // Configurar displayName
            val fullName = "$nombre $apellido"
            user?.updateProfile(
                UserProfileChangeRequest.Builder()
                    .setDisplayName(fullName)
                    .build()
            )?.await()
            return user
        } catch (e: FirebaseAuthUserCollisionException) {
            throw Exception("El correo ya está registrado")
        } catch (e: FirebaseAuthWeakPasswordException) {
            throw Exception("La contraseña es demasiado débil")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            throw Exception("Correo inválido")
        } catch (e: Exception) {
            throw Exception(e.message ?: "Error desconocido al registrar")
        }
    }

    fun currentUser(): FirebaseUser? = firebaseAuth.currentUser

    fun logout() = firebaseAuth.signOut()
}