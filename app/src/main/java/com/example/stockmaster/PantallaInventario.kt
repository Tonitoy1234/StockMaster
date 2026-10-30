
package com.example.stockmaster

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockmaster.data.Producto
import com.example.stockmaster.viewmodel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInventario(
    viewModel: ProductoViewModel = viewModel(),
    onVolver: () -> Unit
) {
    val context = LocalContext.current

    var textoBusqueda by remember { mutableStateOf("") }
    var categoriaSeleccionada by remember { mutableStateOf("Todos") }
    var productoAEditar by remember { mutableStateOf<Producto?>(null) }
    var productoAEliminar by remember { mutableStateOf<Producto?>(null) }

    val listaProductos by viewModel.listaProductos.collectAsState()

    // Obtener las categorías registradas.
    val categorias = listOf("Todos") + listaProductos
        .map { it.categoria.trim() }
        .filter { it.isNotEmpty() }
        .distinct()
        .sortedBy { it.lowercase() }

    // Buscar por código, nombre o categoría.
    val productosFiltrados = listaProductos.filter { producto ->
        val coincideBusqueda =
            producto.codigo.contains(textoBusqueda, ignoreCase = true) ||
                    producto.nombre.contains(textoBusqueda, ignoreCase = true) ||
                    producto.categoria.contains(textoBusqueda, ignoreCase = true)

        val coincideCategoria =
            categoriaSeleccionada == "Todos" ||
                    producto.categoria.equals(
                        categoriaSeleccionada,
                        ignoreCase = true
                    )

        coincideBusqueda && coincideCategoria
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(20.dp)
    ) {
        Text(
            text = "📦 Stock Master",
            fontSize = 24.sp,
            color = Color(0xFF1565C0)
        )

        Text(
            text = "Inventario de productos",
            fontSize = 16.sp,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(12.dp))

        // RESUMEN DEL INVENTARIO
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "📊 Resumen del inventario",
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "📦 Total de productos: ${productosFiltrados.size}",
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // BARRA DE BÚSQUEDA
        OutlinedTextField(
            value = textoBusqueda,
            onValueChange = { textoBusqueda = it },
            label = { Text("Buscar por código, nombre o categoría...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 58.dp),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF1565C0),
                unfocusedBorderColor = Color.Gray
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(6.dp))

        // CATEGORÍAS
        Text(
            text = "Categorías",
            fontSize = 16.sp,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categorias.forEach { categoria ->
                FilterChip(
                    selected = categoriaSeleccionada == categoria,
                    onClick = {
                        categoriaSeleccionada = categoria
                    },
                    label = {
                        Text(text = categoria)
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF1565C0),
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "📋 Lista de productos",
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        // LISTA DE PRODUCTOS
        if (productosFiltrados.isEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "🔍",
                        fontSize = 45.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = if (listaProductos.isEmpty()) {
                            "Inventario vacío"
                        } else {
                            "Sin resultados"
                        },
                        fontSize = 18.sp
                    )

                    Text(
                        text = if (listaProductos.isEmpty()) {
                            "Aquí aparecerán tus productos."
                        } else {
                            "No se encontraron productos con esos filtros."
                        },
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = productosFiltrados,
                    key = { it.id }
                ) { producto ->

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            // NOMBRE, CÓDIGO Y PRECIO
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = producto.nombre,
                                        fontSize = 18.sp,
                                        color = Color(0xFF1565C0)
                                    )

                                    Text(
                                        text = "Código: ${producto.codigo}",
                                        fontSize = 13.sp,
                                        color = Color.Gray
                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = "$${producto.precio}",
                                    fontSize = 18.sp,
                                    color = Color(0xFF008FA8)
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Categoría: ${producto.categoria}",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            // STOCK Y BOTONES
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Stock: ${producto.cantidad} unidades",
                                    fontSize = 14.sp,
                                    color = Color.DarkGray
                                )

                                Row {
                                    IconButton(
                                        onClick = {
                                            productoAEditar = producto
                                        },
                                        modifier = Modifier.size(36.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "Editar producto",
                                            tint = Color(0xFF1565C0)
                                        )
                                    }

                                    IconButton(
                                        onClick = {
                                            productoAEliminar = producto
                                        },
                                        modifier = Modifier.size(36.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Eliminar producto",
                                            tint = MaterialTheme.colorScheme.error
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onVolver,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1565C0)
            )
        ) {
            Text(
                text = "Volver al inicio",
                fontSize = 16.sp
            )
        }
    }

    // DIÁLOGO PARA EDITAR PRODUCTO
    productoAEditar?.let { producto ->
        DialogEditarProducto(
            producto = producto,
            onDismiss = {
                productoAEditar = null
            },
            onGuardar = { nombre, categoria, precioStr, cantidadStr ->
                viewModel.actualizarProducto(
                    producto = producto,
                    nombre = nombre,
                    categoria = categoria,
                    precioStr = precioStr,
                    cantidadStr = cantidadStr,
                    onSuccess = {
                        Toast.makeText(
                            context,
                            "Producto actualizado correctamente",
                            Toast.LENGTH_SHORT
                        ).show()

                        productoAEditar = null
                    },
                    onError = { mensaje ->
                        Toast.makeText(
                            context,
                            mensaje,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        )
    }

    // DIÁLOGO PARA CONFIRMAR ELIMINACIÓN
    productoAEliminar?.let { producto ->
        AlertDialog(
            onDismissRequest = {
                productoAEliminar = null
            },
            title = {
                Text("Confirmar eliminación")
            },
            text = {
                Text(
                    "¿Estás seguro de que deseas eliminar " +
                            "\"${producto.nombre}\"? Esta acción no se puede deshacer."
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.eliminarProducto(producto) {
                            Toast.makeText(
                                context,
                                "Producto eliminado correctamente",
                                Toast.LENGTH_SHORT
                            ).show()

                            productoAEliminar = null
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = {
                        productoAEliminar = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun DialogEditarProducto(
    producto: Producto,
    onDismiss: () -> Unit,
    onGuardar: (
        nombre: String,
        categoria: String,
        precio: String,
        cantidad: String
    ) -> Unit
) {
    var nombre by remember {
        mutableStateOf(producto.nombre)
    }
    var categoria by remember {
        mutableStateOf(producto.categoria)
    }
    var precio by remember {
        mutableStateOf(producto.precio.toString())
    }
    var cantidad by remember {
        mutableStateOf(producto.cantidad.toString())
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Editar Producto")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del producto") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = categoria,
                    onValueChange = { categoria = it },
                    label = { Text("Categoría") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio ($)") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = cantidad,
                    onValueChange = { cantidad = it },
                    label = { Text("Cantidad en stock") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onGuardar(nombre, categoria, precio, cantidad)
                }
            ) {
                Text("Guardar cambios")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss
            ) {
                Text("Cancelar")
            }
        }
    )
}
