package com.example.tatapp.ui.screens.productos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tatapp.data.repositorio.ProductosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductosDataViewModel(private val repository: ProductosRepository) : ViewModel() {

    private val _productos = MutableStateFlow<List<ClaseProductos>>(emptyList())
    val productos: StateFlow<List<ClaseProductos>> = _productos

    init {
        cargarProductos()
    }

    private fun cargarProductos() {
        viewModelScope.launch {
            _productos.value = repository.getProductos()
        }
    }

    fun getProductoById(id: String): ClaseProductos? {
        return _productos.value.find { it.id == id }
    }

    fun filtrarPorCategoria(categoria: CategoriaProducto?, subcategoria: String = ""): List<ClaseProductos> {
        return _productos.value.filter { producto ->
            (categoria == null || producto.categoria == categoria) &&
                    (subcategoria.isEmpty() || producto.subcategoria == subcategoria)
        }
    }
}