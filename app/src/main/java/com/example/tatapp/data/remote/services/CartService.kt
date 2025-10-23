package com.example.tatapp.data.remote.services

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class AddCartItemRequest(
    val productId: String,
    val quantity: Int
)

data class CartItemDto(
    val id: String,
    val productId: String,
    val name: String,
    val price: Double,
    val quantity: Int
)

interface CartService {
    @POST("api/cart/items")
    suspend fun addItem(@Body body: AddCartItemRequest): CartItemDto

    @GET("api/cart/items")
    suspend fun getItems(): List<CartItemDto>
}
