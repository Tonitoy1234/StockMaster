
package com.example.stockmaster.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarProducto(producto: Producto): Long

    @Update
    suspend fun actualizarProducto(producto: Producto)

    @Delete
    suspend fun eliminarProducto(producto: Producto)

    @Query("UPDATE productos SET codigo = :codigo WHERE id = :id")
    suspend fun actualizarCodigo(id: Int, codigo: String)

    @Query("SELECT * FROM productos ORDER BY id DESC")
    fun obtenerTodosLosProductos(): Flow<List<Producto>>
}
