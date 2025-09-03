package com.example.tatapp.ui.screens.subcategorias

data class SubcategoriaItem(
    val nombre: String
)

val subcategoriasMap = mapOf(
    "Alimentos" to listOf("Carnes", "Frutas y Verduras", "Cereales", "Lácteos"),
    "Salud" to listOf("Medicamentos", "Higiene", "Suplementos"),
    "Mascotas" to listOf("Gato", "Perro", "Otro"),
    "Hogar" to listOf("Muebles", "Herramientas"),
    "Jardín" to listOf("Plantas", "Accesorios", "Fertilizantes"),
    "Belleza" to listOf("Maquillaje", "Perfumería"),
    "Regalos" to listOf("Él", "Ella", "Niño", "Niña"),
    "Cuidados" to listOf("Terapias", "Servicios"),
    "Viajes" to listOf("Nacional", "Internacional"),
    "Panoramas" to listOf("Comidas", "Aire libre")
)