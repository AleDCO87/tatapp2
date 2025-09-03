package com.example.tatapp.modelo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tatapp.modelo.dao.CarritoDao
import com.example.tatapp.modelo.entity.CarritoEntity

@Database(
    entities = [CarritoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun carritoDao(): CarritoDao
}