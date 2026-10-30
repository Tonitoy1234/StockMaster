
package com.example.stockmaster

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockmaster.viewmodel.ProductoViewModel

@Composable
fun PantallaRegistro(
    viewModel: ProductoViewModel = viewModel(),
    onVolver: () -> Unit = {}
) {
    val context = LocalContext.current

    var codigo by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Registro de productos",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = codigo,
            onValueChange = { codigo = it },
            label = { Text("Código del producto") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del producto") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = categoria,
            onValueChange = { categoria = it },
            label = { Text("Categoría") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Precio ($)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = cantidad,
            onValueChange = { cantidad = it },
            label = { Text("Cantidad en stock") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = {
                viewModel.guardarProducto(
                    codigo = codigo,
                    nombre = nombre,
                    categoria = categoria,
                    precioStr = precio,
                    cantidadStr = cantidad,
                    onSuccess = {
                        Toast.makeText(
                            context,
                            "Producto guardado correctamente",
                            Toast.LENGTH_SHORT
                        ).show()

                        codigo = ""
                        nombre = ""
                        categoria = ""
                        precio = ""
                        cantidad = ""
                    },
                    onError = { mensaje ->
                        Toast.makeText(
                            context,
                            mensaje,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Guardar producto")
        }

        OutlinedButton(
            onClick = onVolver,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Volver al inicio")
        }
    }
}
