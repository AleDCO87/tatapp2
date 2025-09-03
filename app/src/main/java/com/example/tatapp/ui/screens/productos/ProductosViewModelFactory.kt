package com.example.tatapp.ui.screens.productos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tatapp.modelo.dao.CarritoDao

class ProductosViewModelFactory(
    private val carritoDao: CarritoDao,
    private val categoriaSeleccionada: CategoriaProducto?,
    private val subcategoriaSeleccionada: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductosViewModel(carritoDao, categoriaSeleccionada, subcategoriaSeleccionada) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}