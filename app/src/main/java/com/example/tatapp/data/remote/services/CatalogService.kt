package com.example.tatapp.data.remote.services

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Path

data class ProductDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Double,
    // ajusta la key si el backend usa otra (p.ej. "image", "image_url", "img")
    @SerializedName("imageUrl") val imageUrl: String?
)

interface CatalogService {
    @GET("catalogo")
    suspend fun getCatalog(): List<ProductDto>

    @GET("catalogo/{id}")
    suspend fun getProduct(@Path("id") id: String): ProductDto

    @GET("catalogo")
    suspend fun getCatalogRaw(): String
}
