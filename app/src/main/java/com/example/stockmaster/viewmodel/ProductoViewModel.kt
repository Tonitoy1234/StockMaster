
package com.example.stockmaster.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmaster.data.AppDatabase
import com.example.stockmaster.data.Producto
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductoViewModel(application: Application) :
    AndroidViewModel(application) {

    private val productoDao =
        AppDatabase.getDatabase(application).productoDao()

    val listaProductos: StateFlow<List<Producto>> =
        productoDao.obtenerTodosLosProductos().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun guardarProducto(
        codigo: String,
        nombre: String,
        categoria: String,
        precioStr: String,
        cantidadStr: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (codigo.isBlank() || nombre.isBlank() ||
            categoria.isBlank() || precioStr.isBlank() ||
            cantidadStr.isBlank()
        ) {
            onError("Por favor completa todos los campos")
            return
        }

        val precio = precioStr.toDoubleOrNull()
        val cantidad = cantidadStr.toIntOrNull()

        if (precio == null || cantidad == null ||
            precio < 0 || cantidad < 0
        ) {
            onError("Ingresa un precio y una cantidad válidos")
            return
        }

        viewModelScope.launch {
            try {
                val producto = Producto(
                    codigo = codigo.trim(),
                    nombre = nombre.trim(),
                    categoria = categoria.trim(),
                    precio = precio,
                    cantidad = cantidad
                )

                productoDao.insertarProducto(producto)
                onSuccess()

            } catch (e: Exception) {
                onError("No se pudo guardar el producto")
            }
        }
    }

    fun actualizarProducto(
        producto: Producto,
        nombre: String,
        categoria: String,
        precioStr: String,
        cantidadStr: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (nombre.isBlank() || categoria.isBlank() ||
            precioStr.isBlank() || cantidadStr.isBlank()
        ) {
            onError("Por favor completa todos los campos")
            return
        }

        val precio = precioStr.toDoubleOrNull()
        val cantidad = cantidadStr.toIntOrNull()

        if (precio == null || cantidad == null ||
            precio < 0 || cantidad < 0
        ) {
            onError("Ingresa un precio y una cantidad válidos")
            return
        }

        viewModelScope.launch {
            try {
                val productoActualizado = producto.copy(
                    nombre = nombre.trim(),
                    categoria = categoria.trim(),
                    precio = precio,
                    cantidad = cantidad
                )

                productoDao.actualizarProducto(productoActualizado)
                onSuccess()

            } catch (e: Exception) {
                onError("No se pudo actualizar el producto")
            }
        }
    }

    fun eliminarProducto(
        producto: Producto,
        onSuccess: () -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                productoDao.eliminarProducto(producto)
                onSuccess()
            } catch (e: Exception) {
                // No se pudo eliminar el producto.
            }
        }
    }
}
