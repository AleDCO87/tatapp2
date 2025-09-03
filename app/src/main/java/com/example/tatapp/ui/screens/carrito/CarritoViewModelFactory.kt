package com.example.tatapp.ui.screens.carrito

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tatapp.modelo.dao.CarritoDao

class CarritoViewModelFactory(private val dao: CarritoDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarritoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CarritoViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
