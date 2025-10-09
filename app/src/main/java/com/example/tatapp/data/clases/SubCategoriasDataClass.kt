package com.example.tatapp.data.clases

import com.example.tatapp.R

data class SubcategoriaItem(
    val nombre: String,
    val imagenRes: Int
)

val subcategoriasMap = mapOf(
    "Alimentos" to listOf(
        SubcategoriaItem("Carnes", R.drawable.sub_carnes),
        SubcategoriaItem("Frutas y Verduras", R.drawable.sub_frutas_verduras),
        SubcategoriaItem("Cereales", R.drawable.sub_cereales),
        SubcategoriaItem("Lácteos", R.drawable.sub_lacteos)
    ),
    "Salud" to listOf(
        SubcategoriaItem("Medicamentos", R.drawable.sub_medicamentos),
        SubcategoriaItem("Higiene", R.drawable.sub_higiene),
        SubcategoriaItem("Suplementos", R.drawable.sub_suplementos)
    ),
    "Mascotas" to listOf(
        SubcategoriaItem("Gato", R.drawable.sub_gatos),
        SubcategoriaItem("Perro", R.drawable.sub_perros),
        SubcategoriaItem("Otro", R.drawable.sub_mascotas)
    ),
    "Hogar" to listOf(
        SubcategoriaItem("Muebles", R.drawable.sub_muebles),
        SubcategoriaItem("Herramientas", R.drawable.sub_herramientas)
    ),
    "Jardín" to listOf(
        SubcategoriaItem("Plantas", R.drawable.sub_plantas),
        SubcategoriaItem("Accesorios", R.drawable.sub_accesorios),
        SubcategoriaItem("Fertilizantes", R.drawable.sub_fertilizantes)
    ),
    "Belleza" to listOf(
        SubcategoriaItem("Maquillaje", R.drawable.sub_maquillajes),
        SubcategoriaItem("Perfumería", R.drawable.sub_perfumeria)
    ),
    "Regalos" to listOf(
        SubcategoriaItem("Él", R.drawable.sub_reg_hombre),
        SubcategoriaItem("Ella", R.drawable.sub_reg_mujer),
        SubcategoriaItem("Niño", R.drawable.sub_reg_nino),
        SubcategoriaItem("Niña", R.drawable.sub_reg_nina)
    ),
    "Cuidados" to listOf(
        SubcategoriaItem("Terapias", R.drawable.sub_terapias),
        SubcategoriaItem("Servicios", R.drawable.sub_servicios)
    ),
    "Viajes" to listOf(
        SubcategoriaItem("Nacional", R.drawable.sub_viajes_nac),
        SubcategoriaItem("Internacional", R.drawable.sub_viajes_inter)
    ),
    "Panoramas" to listOf(
        SubcategoriaItem("Comidas", R.drawable.sub_comidas),
        SubcategoriaItem("Aire libre", R.drawable.sub_aire_libre)
    )
)