package com.example.tatapp.ui.screens.productos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ProductosDataViewModel(application: Application) : AndroidViewModel(application) {

    private val _productos = MutableStateFlow<List<ClaseProductos>>(emptyList())
    val productos: StateFlow<List<ClaseProductos>> = _productos

    init {
        cargarProductos()
    }

    private fun cargarProductos() {
        viewModelScope.launch {
            val context = getApplication<Application>().applicationContext
            val json = context.assets.open("productos.json")
                .bufferedReader()
                .use { it.readText() }

            _productos.value = Json { ignoreUnknownKeys = true }
                .decodeFromString(json)
        }
    }

    fun getProductoById(id: String): ClaseProductos? {
        return _productos.value.find { it.id == id }
    }
}