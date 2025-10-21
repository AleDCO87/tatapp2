package com.example.tatapp.data.remote.services

import retrofit2.http.GET
import retrofit2.http.Path

data class ProductDto(
    val id: String,
    val name: String,
    val price: Double,
    val imageUrl: String?
)

interface CatalogService {
    @GET("api/catalog")
    suspend fun getCatalog(): List<ProductDto>

    @GET("api/catalog/{id}")
    suspend fun getProduct(@Path("id") id: String): ProductDto
}
