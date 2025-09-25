package com.example.tatapp.ui.screens.homeProductos

import com.example.tatapp.R

data class CategoriaItem(
    val nombreCat: String,
    val iconoCat: Int
)

val categoriasProductos = listOf(
    CategoriaItem("Alimentos", R.drawable.alimentos),
    CategoriaItem("Salud", R.drawable.salud),
    CategoriaItem("Mascotas", R.drawable.mascotas),
    CategoriaItem("Hogar", R.drawable.hogar),
    CategoriaItem("Jardín", R.drawable.jardin),
    CategoriaItem("Belleza", R.drawable.belleza),
    CategoriaItem("Regalos", R.drawable.regalos)
)

val categoriasServicios = listOf(
    CategoriaItem("Cuidados", R.drawable.outline_store),
    CategoriaItem("Viajes", R.drawable.outline_store),
    CategoriaItem("Panoramas", R.drawable.outline_store)
)