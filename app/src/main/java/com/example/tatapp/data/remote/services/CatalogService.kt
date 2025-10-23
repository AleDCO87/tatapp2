package com.example.tatapp.data.remote.services

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

data class CatalogoDto(
    @SerializedName("idItem")          val idItem: String?,
    @SerializedName("tipo")            val tipo: String,
    @SerializedName("nombre")          val nombre: String,
    @SerializedName("descripcion")     val descripcion: String,
    @SerializedName("precio")          val precio: Double,
    @SerializedName("enOferta")        val enOferta: Boolean,
    @SerializedName("descuento")       val descuento: Double,
    @SerializedName("idCategoria")     val idCategoria: String,
    @SerializedName("ubicacionX")      val ubicacionX: Double,
    @SerializedName("ubicacionY")      val ubicacionY: Double,
    @SerializedName("reseniaTexto")    val reseniaTexto: String,
    @SerializedName("reseniaPuntaje")  val reseniaPuntaje: Double
)

data class CategoriaDto(
    @SerializedName("nombre") val nombre: String,
    @SerializedName("activa") val activa: Boolean
)
data class ProductDto(
    @SerializedName("id") val id: String,
    @SerializedName("nombre") val name: String,
    @SerializedName("precio") val price: Double,
    // ajusta la key si el backend usa otra (p.ej. "image", "image_url", "img")
    @SerializedName("imagen") val imageUrl: String?
)

interface CatalogService {

    // Todos los items del catálogo
    @Headers("Accept: application/json")
    @GET("catalogo")
    suspend fun getCatalog(): List<CatalogoDto>

    // Detalle por id
    @Headers("Accept: application/json")
    @GET("catalogo/item/{idItem}")
    suspend fun getProduct(@Path("idItem") id: String): CatalogoDto

    // Categorias
    @Headers("Accept: application/json")
    @GET("catalogo/categorias")
    suspend fun getCategories(): List<CategoriaDto>

    // Solo diagnostico por si el server vuelve a dar HTML
    @GET("catalogo") suspend fun getCatalogRaw(): String
    @GET("catalogo/categorias") suspend fun getCategoriesRaw(): String
}
