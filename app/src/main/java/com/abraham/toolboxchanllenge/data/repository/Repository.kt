package com.abraham.toolboxchanllenge.data.repository

import com.abraham.toolboxchanllenge.data.model.PicsumPhoto
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun fetchPhotos(): Flow<RepositoryImpl.Result<List<PicsumPhoto>>>
}