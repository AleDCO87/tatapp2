package com.example.tatapp.domain.mapper

import com.example.tatapp.data.remote.services.ProductDto
import com.example.tatapp.data.remote.services.CartItemDto
import com.example.tatapp.domain.model.Product
import com.example.tatapp.domain.model.CartItem

fun ProductDto.toProductDomain() = Product(
    id = id,
    name = name,
    price = price,
    imageUrl = imageUrl
)

fun CartItemDto.toCartDomain() = CartItem(
    id = id,
    productId = productId,
    name = name,
    price = price,
    quantity = quantity
)
