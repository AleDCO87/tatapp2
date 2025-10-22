package com.example.tatapp.data.repository

import android.util.Log
import com.example.tatapp.data.remote.services.CatalogService
import com.example.tatapp.domain.mapper.toCategoryDomain
import com.example.tatapp.domain.mapper.toProductDomain
import com.example.tatapp.domain.model.Category
import com.example.tatapp.domain.model.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CatalogRepository(private val service: CatalogService) {

    private val gson = Gson()
    private val listProdType = object : TypeToken<List<com.example.tatapp.data.remote.services.CatalogoDto>>() {}.type
    private val listCatType  = object : TypeToken<List<com.example.tatapp.data.remote.services.CategoriaDto>>() {}.type

    suspend fun getCatalog(): List<Product> {
        return try {
            // 1) ruta normal con Accept: application/json
            val list = service.getCatalog()
            list.map { it.toProductDomain() }
        } catch (e: Exception) {
            Log.w("API", "getCatalog principal falló: ${e.message}")
            // 2) si llega HTML, intenta parsear raw (o devuelve dummy)
            val raw = runCatching { service.getCatalogRaw() }.getOrDefault("")
            val t = raw.trimStart()
            when {
                t.startsWith("<html", true) -> {
                    Log.e("API", "/catalogo devolvió HTML. (Swagger?)")
                    demoProducts()
                }
                t.startsWith("[") -> {
                    val list = gson.fromJson<List<com.example.tatapp.data.remote.services.CatalogoDto>>(t, listProdType)
                    list.map { it.toProductDomain() }
                }
                else -> demoProducts()
            }
        }
    }

    suspend fun getCategories(): List<Category> {
        return try {
            val list = service.getCategories()
            list.map { it.toCategoryDomain() }
        } catch (e: Exception) {
            Log.w("API", "getCategories principal falló: ${e.message}")
            val raw = runCatching { service.getCategoriesRaw() }.getOrDefault("")
            val t = raw.trimStart()
            when {
                t.startsWith("<html", true) -> {
                    Log.e("API", "/catalogo/categorias devolvió HTML.")
                    demoCategories()
                }
                t.startsWith("[") -> {
                    val list = gson.fromJson<List<com.example.tatapp.data.remote.services.CategoriaDto>>(t, listCatType)
                    list.map { it.toCategoryDomain() }
                }
                else -> demoCategories()
            }
        }
    }

    suspend fun getProduct(id: String): Product {
        val dto = service.getProduct(id)
        return dto.toProductDomain()
    }

    private fun demoProducts() = listOf(
        Product("1","Producto demo",9990.0,null,"cat1",false,0.0,4.6,"Demo"),
        Product("2","Pack ofertas",14990.0,null,"cat2",true, 5.0,4.3,"Demo"),
    )

    private fun demoCategories() = listOf(
        Category("Alimentos", true),
        Category("Salud", true),
        Category("Mascotas", true),
        Category("Jardín", true)
    )
}

