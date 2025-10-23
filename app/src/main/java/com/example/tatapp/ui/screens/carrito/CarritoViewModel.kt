package com.example.tatapp.ui.screens.carrito

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tatapp.data.modelo.dao.CarritoDao
import com.example.tatapp.data.modelo.entity.CarritoEntity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CarritoViewModel(private val dao: CarritoDao) : ViewModel() {

    private val _carrito = MutableStateFlow<List<CarritoEntity>>(emptyList())
    val carrito: StateFlow<List<CarritoEntity>> = _carrito.asStateFlow()

    val totalEnCarrito: StateFlow<Int> =
        carrito.map { items -> items.sumOf { it.cantidad } }
            .stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    val totalPrecio: StateFlow<Int> =
        carrito.map { items -> items.sumOf { it.precio * it.cantidad } }
            .stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    init { cargarCarrito() }

    private fun cargarCarrito() {
        viewModelScope.launch {
            dao.obtenerCarrito().collect { _carrito.value = it }
        }
    }

    fun aumentarCantidad(producto: CarritoEntity) { viewModelScope.launch {
        dao.actualizarProducto(producto.copy(cantidad = producto.cantidad + 1)) } }

    fun disminuirCantidad(producto: CarritoEntity) { viewModelScope.launch {
        if (producto.cantidad > 1) dao.actualizarProducto(producto.copy(cantidad = producto.cantidad - 1))
        else dao.eliminarProducto(producto)
    } }

    fun eliminarProducto(producto: CarritoEntity) { viewModelScope.launch { dao.eliminarProducto(producto) } }
    fun vaciarCarrito() { viewModelScope.launch { dao.vaciarCarrito() } }

    fun agregarAlCarrito(producto: CarritoEntity) {
        viewModelScope.launch {
            val existente = dao.getById(producto.id)
            if (existente == null) dao.insertarProducto(producto)
            else dao.actualizarProducto(existente.copy(cantidad = existente.cantidad + producto.cantidad))
        }
    }
}
