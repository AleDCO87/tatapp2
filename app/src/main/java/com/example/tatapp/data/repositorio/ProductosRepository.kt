package com.example.tatapp.data.repositorio

import android.content.Context
import com.example.tatapp.ui.screens.productos.ClaseProductos
import com.example.tatapp.ui.screens.productos.CategoriaProducto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

// Instancia Json única y reutilizable
private val JsonParser: Json = Json {
    ignoreUnknownKeys = true
    isLenient = true
    encodeDefaults = true
}

interface ProductosRepository {
    suspend fun getProductos(): List<ClaseProductos>
    suspend fun getProductoById(id: String): ClaseProductos?
    suspend fun getProductosPorCategoria(categoria: CategoriaProducto?, subcategoria: String = ""): List<ClaseProductos>
}

class ProductosRepositoryJson(private val context: Context) : ProductosRepository {

    private var productosCache: List<ClaseProductos> = emptyList()

    private suspend fun cargarProductosJson(): List<ClaseProductos> {
        if (productosCache.isEmpty()) {
            productosCache = withContext(Dispatchers.IO) {
                val jsonString = context.assets.open("productos.json")
                    .bufferedReader()
                    .use { it.readText() }

                JsonParser.decodeFromString(jsonString)
            }
        }
        return productosCache
    }

    override suspend fun getProductos(): List<ClaseProductos> {
        return cargarProductosJson()
    }

    override suspend fun getProductoById(id: String): ClaseProductos? {
        return cargarProductosJson().find { it.id == id }
    }

    override suspend fun getProductosPorCategoria(categoria: CategoriaProducto?, subcategoria: String): List<ClaseProductos> {
        return cargarProductosJson().filter { producto ->
            (categoria == null || producto.categoria == categoria) &&
                    (subcategoria.isEmpty() || producto.subcategoria == subcategoria)
        }
    }
}