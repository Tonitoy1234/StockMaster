
package com.example.stockmaster

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun NavegacionApp() {

    var pantalla by rememberSaveable {
        mutableStateOf("login")
    }

    when (pantalla) {

        "login" -> PantallaLogin(
            onLogin = {
                pantalla = "inicio"
            },
            onRegistro = {
                pantalla = "registroUsuario"
            }
        )

        "registroUsuario" -> PantallaRegistroUsuario(
            onVolver = {
                pantalla = "login"
            }
        )

        "inicio" -> PantallaInicio(
            onRegistrar = {
                pantalla = "registro"
            },
            onInventario = {
                pantalla = "inventario"
            },
            onCerrarSesion = {
                pantalla = "login"
            }
        )

        "registro" -> PantallaRegistro(
            onVolver = {
                pantalla = "inicio"
            }
        )

        "inventario" -> PantallaInventario(
            onVolver = {
                pantalla = "inicio"
            }
        )
    }
}
