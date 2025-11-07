package com.abraham.toolboxchanllenge.data.repository

import com.abraham.toolboxchanllenge.data.api.ApiService
import com.abraham.toolboxchanllenge.data.model.PicsumPhoto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: ApiService
): Repository {

    sealed class Result<out T> {
        object Loading : Result<Nothing>()
        data class Success<T>(val data: T) : Result<T>()
        data class Error(val message: String) : Result<Nothing>()
    }

    override fun fetchPhotos(): Flow<Result<List<PicsumPhoto>>> = flow {
        emit(Result.Loading)
        try {
            val photos = api.getPhotos()
            val mapped = photos.map { p ->
                p.copy(download_url = "https://picsum.photos/id/${p.id}/600/400")
            }
            emit(Result.Success(mapped))
        } catch (e: IOException) {
            emit(Result.Error("Network error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage ?: "Unknown error"))
        }
    }
}
