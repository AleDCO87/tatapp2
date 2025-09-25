package com.example.tatapp.ui.screens.productos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tatapp.data.repositorio.ProductosRepository

class ProductosDataViewModelFactory(
    private val repository: ProductosRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductosDataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductosDataViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}