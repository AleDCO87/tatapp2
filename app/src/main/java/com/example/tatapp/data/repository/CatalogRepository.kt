package com.example.tatapp.data.repository

import com.example.tatapp.data.remote.services.CatalogService
import com.example.tatapp.domain.mapper.toProductDomain
import com.example.tatapp.domain.model.Product

class CatalogRepository(private val service: CatalogService) {
    suspend fun getCatalog(): List<Product> = service.getCatalog().map { it.toProductDomain() }
    suspend fun getProduct(id: String): Product = service.getProduct(id).toProductDomain()
}
