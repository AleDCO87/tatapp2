package com.example.tatapp.ui.screens.productos

import kotlinx.serialization.Serializable

@Serializable
enum class CategoriaProducto(val displayName: String) {
    ALIMENTOS("Alimentos"),
    SALUD("Salud"),
    MASCOTAS("Mascotas"),
    HOGAR("Hogar"),
    JARDIN("Jardín"),
    BELLEZA("Belleza"),
    REGALOS("Regalos"),
    CUIDADOS("Cuidados"),
    VIAJES("Viajes"),
    PANORAMAS("Panoramas")
}

@Serializable
data class ClaseProductos(
    val id: String,
    val nombre: String,
    val precio: Int,
    val imagenRes: String,
    val descripcion: String,
    val categoria: CategoriaProducto,
    val subcategoria: String,
    val evaluacion: Float
)

