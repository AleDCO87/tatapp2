// com/example/tatapp/core/net/ErrorMapper.kt
package com.example.tatapp.core.net

import retrofit2.HttpException
import java.io.IOException

fun Throwable.toUserMessage(): String = when (this) {
    is IOException -> "Problema de conexión. Revisa tu internet."
    is HttpException -> when (this.code()) {   // <-- usa code()
        400 -> "Solicitud inválida (400)."
        401 -> "No autorizado (401)."
        404 -> "Recurso no encontrado (404)."
        500 -> "Error del servidor (500)."
        else -> "Error HTTP (${this.code()})."
    }
    else -> this.message ?: "Ocurrió un error inesperado."
}
