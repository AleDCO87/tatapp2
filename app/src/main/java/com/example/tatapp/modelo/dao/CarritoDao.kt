package com.example.tatapp.modelo.dao

import androidx.room.*
import com.example.tatapp.modelo.entity.CarritoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CarritoDao {

    @Query("SELECT * FROM carrito")
    fun obtenerCarrito(): Flow<List<CarritoEntity>>

    @Query("SELECT * FROM carrito WHERE id = :id")
    suspend fun getById(id: String): CarritoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarProducto(producto: CarritoEntity)

    @Update
    suspend fun actualizarProducto(producto: CarritoEntity)

    @Delete
    suspend fun eliminarProducto(producto: CarritoEntity)

    @Query("DELETE FROM carrito")
    suspend fun vaciarCarrito()
}