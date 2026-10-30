
package com.example.stockmaster.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class Producto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val codigo: String,
    val nombre: String,
    val categoria: String,
    val precio: Double,
    val cantidad: Int
)