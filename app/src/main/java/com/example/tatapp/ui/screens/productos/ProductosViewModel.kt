package com.example.tatapp.ui.screens.productos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tatapp.modelo.dao.CarritoDao
import com.example.tatapp.modelo.entity.CarritoEntity
import kotlinx.coroutines.launch

class ProductosViewModel(
    private val carritoDao: CarritoDao,
    val categoriaSeleccionada: CategoriaProducto?,
    val subcategoriaSeleccionada: String
) : ViewModel() {

    val productosFiltrados: List<ClaseProductos>
        get() = productosBase.filter { producto ->
            (categoriaSeleccionada == null || producto.categoria == categoriaSeleccionada) &&
                    (subcategoriaSeleccionada.isEmpty() || producto.subcategoria == subcategoriaSeleccionada)
        }

    fun agregarAlCarrito(producto: ClaseProductos, cantidad: Int = 1) {
        viewModelScope.launch {
            val existente = carritoDao.getById(producto.id) // Buscamos por ID único
            if (existente != null) {
                carritoDao.actualizarProducto(
                    existente.copy(cantidad = existente.cantidad + cantidad)
                )
            } else {
                val nuevo = CarritoEntity(
                    id = producto.id,
                    nombre = producto.nombre,
                    precio = producto.precio,
                    cantidad = cantidad,
                    imagenRes = producto.imagenRes
                )
                carritoDao.insertarProducto(nuevo)
            }
        }
    }
}