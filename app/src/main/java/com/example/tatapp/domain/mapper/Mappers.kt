package com.example.tatapp.domain.mapper

import com.example.tatapp.domain.model.Product
import com.example.tatapp.domain.model.CartItem
import com.example.tatapp.domain.model.Category
import com.example.tatapp.data.remote.services.CatalogoDto
import com.example.tatapp.data.remote.services.CategoriaDto
import com.example.tatapp.data.remote.services.ProductDto
import com.example.tatapp.data.remote.services.CartItemDto

/* -------------------- Product (desde el DTO REAL del backend) -------------------- */
fun CatalogoDto.toProductDomain(): Product = Product(
    id          = idItem ?: "${idCategoria}_${nombre}", // si no viene id único en listado
    name        = nombre,
    price       = precio,
    imageUrl    = null,             // cuando el backend exponga imagen, mapear aquí
    categoryId  = idCategoria,
    onSale      = enOferta,
    discount    = descuento,
    rating      = reseniaPuntaje,
    description = descripcion
)

/* --------- Product (compat para el DTO antiguo para no romper compilación) -------- */
fun ProductDto.toProductDomain(): Product = Product(
    id          = id,
    name        = name,
    price       = price,
    imageUrl    = imageUrl,
    categoryId  = "",        // valores por defecto si ese DTO no los trae
    onSale      = false,
    discount    = 0.0,
    rating      = 0.0,
    description = ""
)

/* ----------------------------- Category (DTO -> dominio) ----------------------------- */
fun CategoriaDto.toCategoryDomain(): Category = Category(
    name   = nombre,
    active = activa
)

/* --------------------------------- CartItem ------------------------------------- */
fun CartItemDto.toCartDomain(): CartItem = CartItem(
    id        = id,
    productId = productId,
    name      = name,
    price     = price,
    quantity  = quantity
)