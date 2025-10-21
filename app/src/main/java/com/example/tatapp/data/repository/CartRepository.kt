package com.example.tatapp.data.repository

import com.example.tatapp.data.remote.services.AddCartItemRequest
import com.example.tatapp.data.remote.services.CartService
import com.example.tatapp.domain.mapper.toCartDomain
import com.example.tatapp.domain.model.CartItem

class CartRepository(private val service: CartService) {
    suspend fun addItem(productId: String, quantity: Int): CartItem =
        service.addItem(AddCartItemRequest(productId, quantity)).toCartDomain()

    suspend fun getItems(): List<CartItem> =
        service.getItems().map { it.toCartDomain() }
}
