package com.example.stockmaster

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaInicio(
    onRegistrar: () -> Unit,
    onInventario: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "◈ Stock Master",
            fontSize = 30.sp,
            color = Color(0xFF1565C0)
        )

        Text(
            text = "Control de inventario",
            fontSize = 15.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(30.dp))

        Icon(
            imageVector = Icons.Default.Inventory2,
            contentDescription = "Inventario",
            modifier = Modifier.size(65.dp),
            tint = Color(0xFF1565C0)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "¡Bienvenido!",
            fontSize = 23.sp
        )

        Text(
            text = "¿Qué deseas hacer?",
            fontSize = 15.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(28.dp))

        // BOTÓN PARA VER EL INVENTARIO
        Button(
            onClick = onInventario,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Inventory2,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text("Ver inventario", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // BOTÓN PARA REGISTRAR PRODUCTOS
        Button(
            onClick = onRegistrar,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF008FA8)
            )
        ) {
            Icon(
                imageVector = Icons.Default.AddBox,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text("Registrar producto", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // BOTÓN PARA CERRAR SESIÓN
        Button(
            onClick = onCerrarSesion,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD32F2F)
            )
        ) {
            Icon(
                imageVector = Icons.Default.Logout,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = "Cerrar sesión",
                fontSize = 16.sp
            )
        }
    }
}