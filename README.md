# ToolboxChallenge

Aplicación móvil Android desarrollada con **Kotlin + Jetpack Compose + Hilt**, que consume una API de fotos (de [Picsum](https://picsum.photos)) y muestra una lista de imágenes.

---

##  Funcionalidades

- Obtiene fotos desde la API (`getPhotos()` de `ApiService`).
- Realiza un mapeo para modificar la URL de descarga:  
  `"https://picsum.photos/id/{id}/600/400"`.
- Muestra estados de carga:
  - 🌀 **Loading**
  - ✅ **Success** (datos cargados)
  - ❌ **Error** (mensaje de error)
- `PhotosViewModel` expone un `StateFlow` de dichos estados.
- Función `groupByChunk(chunkSize, photos: List<PicsumPhoto>)` para agrupar la lista en “chunks” (útil para UI de carrusel o grillas).
- Arquitectura modular con **Hilt** para inyección de dependencias, **Retrofit/OkHttp** para red, **Coil** para carga de imágenes, y **Media3** para reproducción de medios.

---

## Stack técnico

-  **Kotlin + Jetpack Compose**
-  **androidx.lifecycle.ViewModel** + `StateFlow`
-  **Hilt** para inyección de dependencias
-  **Retrofit + OkHttp + Gson** para red
-  **Coil Compose** para carga de imágenes
-  **Media3 (ExoPlayer)** para módulo de medios (si aplica)
-  **Coroutines + Flow** para asincronía
-  **Unit tests:** `JUnit4`, `Mockito-Kotlin`, `kotlinx.coroutines.test`


## ⚙️ Cómo correr el proyecto

1. **Clona el repositorio**
   ```bash
   git clone https://github.com/abcasasgaming02-stack/ToolboxChallenge.git
Abre con Android Studio

Compatible con AGP 8.x y Kotlin 2.0.x.

Ejecuta la aplicación

Sincroniza Gradle.

Corre la app en un emulador o dispositivo con Android 24+.

Ejecutar tests unitarios

En la ventana “Run”, selecciona PhotosViewModelTest o Run All Tests.

Tests incluidos
RepositoryImplTest
Verifica los estados:

Loading → Success

Loading → Error
Usando un mock de ApiService.

PhotosViewModelTest
Valida que:

PhotosViewModel emite los estados correspondientes.

groupByChunk() agrupa correctamente las listas.

Buenas prácticas aplicadas
Separación interfaz / implementación para facilitar el mocking (Repository).

Uso de Dispatchers.setMain() y StandardTestDispatcher en tests.

Limitación de emisiones de StateFlow con take(n).toList() para evitar bloqueos.

Uso de sealed class Result para representar estados de UI de forma segura y tipada.

MIT License © 2025 [Abraham Casas]
