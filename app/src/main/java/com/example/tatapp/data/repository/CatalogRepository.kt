package com.example.tatapp.data.repository

import com.example.tatapp.data.remote.services.CatalogService
import com.example.tatapp.data.remote.services.ProductDto
import com.example.tatapp.domain.mapper.toProductDomain
import com.example.tatapp.domain.model.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CatalogRepository(private val service: CatalogService) {

    private val gson = Gson()
    private val listType = object : TypeToken<List<ProductDto>>() {}.type

    suspend fun getCatalog(): List<Product> {
        return try {
            // 1) Intento normal (si el backend ya manda JSON puro)
            service.getCatalog().map { it.toProductDomain() }
        } catch (e: Exception) {
            // 2) Fallback: leo crudo y parseo manual
            val raw = service.getCatalogRaw()

            // Si viene como "...." (string con las comillas exteriores)
            val trimmed = raw.trim()
            val json = if (trimmed.startsWith("\"") && trimmed.endsWith("\"")) {
                // desescapar comillas dentro de la cadena
                trimmed.substring(1, trimmed.length - 1)
                    .replace("\\\"", "\"")
                    .replace("\\n", "\n")
            } else {
                trimmed
            }

            val list = gson.fromJson<List<ProductDto>>(json, listType)
            list.map { it.toProductDomain() }
        }
    }

    suspend fun getProduct(id: String): Product =
        service.getProduct(id).toProductDomain()
}
