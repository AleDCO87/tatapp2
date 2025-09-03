package com.example.tatapp.ui.screens.productos

import java.util.UUID

// Enum para categorías generales
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

data class ClaseProductos(
    val id: String = UUID.randomUUID().toString(),
    val nombre: String,
    val precio: Int,
    val imagenRes: Int,
    val descripcion: String,
    val categoria: CategoriaProducto,
    val subcategoria: String,
    val evaluacion: Float
)

