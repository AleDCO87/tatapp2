package com.example.tatapp.ui.screens.homeProductos

import com.example.tatapp.R

data class CategoriaItem(
    val nombreCat: String,
    val iconoCat: Int
)

val categoriasProductos = listOf(
    CategoriaItem("Alimentos", R.drawable.outline_store),
    CategoriaItem("Salud", R.drawable.outline_store),
    CategoriaItem("Mascotas", R.drawable.outline_store),
    CategoriaItem("Hogar", R.drawable.outline_store),
    CategoriaItem("Jardín", R.drawable.outline_store),
    CategoriaItem("Belleza", R.drawable.outline_store),
    CategoriaItem("Regalos", R.drawable.outline_store)
)

val categoriasServicios = listOf(
    CategoriaItem("Cuidados", R.drawable.outline_store),
    CategoriaItem("Viajes", R.drawable.outline_store),
    CategoriaItem("Panoramas", R.drawable.outline_store)
)