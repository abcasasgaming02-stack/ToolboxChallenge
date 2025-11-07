package com.abraham.toolboxchanllenge.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.abraham.toolboxchanllenge.data.model.PicsumPhoto

@Composable
fun AlbumCarousel(
    title: String,
    photos: List<PicsumPhoto>,
    onOpenDetail: ((String) -> Unit)? = null
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(text = title, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(horizontal = 16.dp))
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(photos) { photo ->
                Card(modifier = Modifier.size(width = 180.dp, height = 140.dp).clickable { onOpenDetail?.invoke(photo.id) }) {
                    Image(
                        painter = rememberAsyncImagePainter(photo.download_url),
                        contentDescription = photo.author,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}
