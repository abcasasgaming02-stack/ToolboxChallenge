package com.abraham.toolboxchanllenge.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abraham.toolboxchanllenge.data.model.PicsumPhoto
import com.abraham.toolboxchanllenge.data.repository.Repository
import com.abraham.toolboxchanllenge.data.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {

    private val _photosState = MutableStateFlow<RepositoryImpl.Result<List<PicsumPhoto>>>(RepositoryImpl.Result.Loading)
    val photosState: StateFlow<RepositoryImpl.Result<List<PicsumPhoto>>> = _photosState

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            repo.fetchPhotos().collect { _photosState.value = it }
        }
    }

    fun groupByChunk(chunkSize: Int, photos: List<PicsumPhoto>): List<List<PicsumPhoto>> =
        photos.chunked(chunkSize)
}
