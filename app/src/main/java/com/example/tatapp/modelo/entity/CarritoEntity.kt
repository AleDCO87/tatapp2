package com.example.tatapp.modelo.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carrito")
data class CarritoEntity(
    @PrimaryKey val id: String,
    val nombre: String,
    val precio: Int,
    val cantidad: Int,
    val imagenRes: Int
)