package com.example.tatapp.ui.screens.carrito

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tatapp.modelo.dao.CarritoDao
import com.example.tatapp.modelo.entity.CarritoEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CarritoViewModel(private val dao: CarritoDao) : ViewModel() {

    private val _carrito = MutableStateFlow<List<CarritoEntity>>(emptyList())
    val carrito: StateFlow<List<CarritoEntity>> = _carrito.asStateFlow()

    val totalEnCarrito: Int
        get() = _carrito.value.sumOf { it.cantidad }

    val totalPrecio: Int
        get() = _carrito.value.sumOf { it.precio * it.cantidad }

    init {
        cargarCarrito()
    }

    private fun cargarCarrito() {
        viewModelScope.launch {
            dao.obtenerCarrito().collect { _carrito.value = it }
        }
    }

    fun aumentarCantidad(producto: CarritoEntity) {
        viewModelScope.launch {
            dao.actualizarProducto(producto.copy(cantidad = producto.cantidad + 1))
        }
    }

    fun disminuirCantidad(producto: CarritoEntity) {
        viewModelScope.launch {
            if (producto.cantidad > 1) {
                dao.actualizarProducto(producto.copy(cantidad = producto.cantidad - 1))
            } else {
                dao.eliminarProducto(producto)
            }
        }
    }

    fun eliminarProducto(producto: CarritoEntity) {
        viewModelScope.launch {
            dao.eliminarProducto(producto)
        }
    }

    fun vaciarCarrito() {
        viewModelScope.launch {
            dao.vaciarCarrito()
        }
    }
}