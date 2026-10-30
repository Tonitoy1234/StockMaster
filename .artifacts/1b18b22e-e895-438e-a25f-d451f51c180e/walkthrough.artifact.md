# Walkthrough - Stock Master Login System

Implemented a complete Login and User Registration system for Stock Master in Kotlin with Jetpack Compose following exact visual guidelines and navigation flows.

## Changes

### [Models & ViewModels]
#### [NEW] [Usuario.kt](file:///C:/Users/nueva/Downloads/StockMaster/app/src/main/java/com/example/stockmaster/Usuario.kt)
- Defined data class `Usuario` with `nombre`, `correo`, and `clave`.

#### [NEW] [UsuarioViewModel.kt](file:///C:/Users/nueva/Downloads/StockMaster/app/src/main/java/com/example/stockmaster/UsuarioViewModel.kt)
- Managed in-memory user list with `registrar(...)` and `login(...): Boolean`.

### [UI Screens]
#### [NEW] [PantallaLogin.kt](file:///C:/Users/nueva/Downloads/StockMaster/app/src/main/java/com/example/stockmaster/PantallaLogin.kt)
- Login screen using Material 3, Primary Blue (`#1565C0`), Turquoise (`#00ACC1`), and Background (`#F5F7FA`) with rounded cards and validation toasts.

#### [NEW] [PantallaRegistroUsuario.kt](file:///C:/Users/nueva/Downloads/StockMaster/app/src/main/java/com/example/stockmaster/PantallaRegistroUsuario.kt)
- User registration screen with name, email, and password fields, saving to `UsuarioViewModel` and returning to login.

### [Navigation & Main Entry]
#### [NEW] [Navegacion.kt](file:///C:/Users/nueva/Downloads/StockMaster/app/src/main/java/com/example/stockmaster/Navegacion.kt)
- `NavegacionApp()` handling states between `"login"`, `"registroUsuario"`, `"inicio"`, `"registro"`, and `"inventario"`.

#### [MODIFY] [MainActivity.kt](file:///C:/Users/nueva/Downloads/StockMaster/app/src/main/java/com/example/stockmaster/MainActivity.kt)
- Updated `setContent` to launch `NavegacionApp()`.

## Verification Results

### Automated Tests
- Executed `gradle_build("app:assembleDebug")`: **Build finished successfully (0 errors)**.
