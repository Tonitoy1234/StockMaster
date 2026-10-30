package com.example.stockmaster.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmaster.data.AppDatabase
import com.example.stockmaster.data.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val usuarioDao = AppDatabase.getDatabase(application).usuarioDao()

    fun registrar(
        nombre: String,
        correo: String,
        clave: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (nombre.isBlank() || correo.isBlank() || clave.isBlank()) {
            onError("Por favor completa todos los campos")
            return
        }

        viewModelScope.launch {
            val existente = withContext(Dispatchers.IO) {
                usuarioDao.buscarCorreo(correo)
            }
            if (existente != null) {
                onError("El correo ya está registrado")
            } else {
                withContext(Dispatchers.IO) {
                    usuarioDao.insertar(Usuario(nombre = nombre, correo = correo, clave = clave))
                }
                onSuccess()
            }
        }
    }

    fun login(
        correo: String,
        clave: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (correo.isBlank() || clave.isBlank()) {
            onError("Por favor completa todos los campos")
            return
        }

        viewModelScope.launch {
            val usuario = withContext(Dispatchers.IO) {
                usuarioDao.login(correo, clave)
            }
            if (usuario != null) {
                onSuccess()
            } else {
                onError("Correo o contraseña incorrectos")
            }
        }
    }
}
