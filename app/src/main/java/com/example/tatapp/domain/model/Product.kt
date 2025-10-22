package com.example.tatapp.domain.model

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val imageUrl: String?,
    val categoryId: String,
    val onSale: Boolean,
    val discount: Double,
    val rating: Double,
    val description: String
)
