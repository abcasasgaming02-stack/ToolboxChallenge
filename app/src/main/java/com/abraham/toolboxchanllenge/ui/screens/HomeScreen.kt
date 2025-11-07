package com.abraham.toolboxchanllenge.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abraham.toolboxchanllenge.data.repository.RepositoryImpl
import com.abraham.toolboxchanllenge.ui.components.AlbumCarousel
import com.abraham.toolboxchanllenge.ui.viewmodel.PhotosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(vm: PhotosViewModel, onOpenDetail: (String) -> Unit) {
    val state = vm.photosState.collectAsState().value

    Scaffold(topBar = { TopAppBar(title = { Text("Home") }) }) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (state) {
                is RepositoryImpl.Result.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is RepositoryImpl.Result.Error -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Error: ${state.message}")
                    }
                }
                is RepositoryImpl.Result.Success -> {
                    val grouped = vm.groupByChunk(6, state.data)
                    LazyColumn(modifier = Modifier.fillMaxSize().padding(vertical = 8.dp)) {
                        items(grouped.size) { idx ->
                            AlbumCarousel(title = "Sección ${idx + 1}", photos = grouped[idx], onOpenDetail = onOpenDetail)
                        }
                        item {
                            Spacer(modifier = Modifier.height(80.dp))
                        }
                    }
                }
            }
        }
    }
}
