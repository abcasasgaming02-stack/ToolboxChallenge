package com.abraham.toolboxchanllenge.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.abraham.toolboxchanllenge.data.repository.RepositoryImpl
import com.abraham.toolboxchanllenge.ui.viewmodel.PhotosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    photoId: String,
    vm: PhotosViewModel,
    onBack: () -> Unit
) {
    val state = vm.photosState.collectAsState().value
    val photo = if (state is RepositoryImpl.Result.Success)
        state.data.firstOrNull { it.id == photoId }
    else null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(photo?.author ?: "Detalle") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            photo?.let {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(it.download_url),
                        contentDescription = it.author,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = it.author,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Foto id=${it.id} • Fuente: Picsum")

                    Spacer(modifier = Modifier.height(24.dp))

                    VideoPlayer(
                        videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                    )
                }
            } ?: run {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No encontrada")
                }
            }
        }
    }
}
