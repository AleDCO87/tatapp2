package com.example.tatapp.ui.screens.productos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tatapp.R
import com.example.tatapp.data.modelo.dao.CarritoDao
import com.example.tatapp.data.modelo.entity.CarritoEntity
import com.example.tatapp.ui.components.drawableMap
import kotlinx.coroutines.launch

class ProductosViewModel(
    private val carritoDao: CarritoDao,
    val categoriaSeleccionada: CategoriaProducto?,
    val subcategoriaSeleccionada: String
) : ViewModel() {

    fun filtrarProductos(productos: List<ClaseProductos>): List<ClaseProductos> {
        return productos.filter { producto ->
            (categoriaSeleccionada == null || producto.categoria == categoriaSeleccionada) &&
                    (subcategoriaSeleccionada.isEmpty() || producto.subcategoria == subcategoriaSeleccionada)
        }
    }

    fun agregarAlCarrito(producto: ClaseProductos, cantidad: Int = 1) {
        viewModelScope.launch {
            val drawableId = drawableMap[producto.imagenRes] ?: R.drawable.ej_alim

            val existente = carritoDao.getById(producto.id)
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
                    imagenRes = drawableId // 👈 convertido de String a Int
                )
                carritoDao.insertarProducto(nuevo)
            }
        }
    }
}