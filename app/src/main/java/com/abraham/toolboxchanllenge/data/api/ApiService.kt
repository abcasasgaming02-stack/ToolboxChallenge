package com.abraham.toolboxchanllenge.data.api


import com.abraham.toolboxchanllenge.data.model.PicsumPhoto
import retrofit2.http.GET

interface ApiService {
    @GET("v2/list")
    suspend fun getPhotos(): List<PicsumPhoto>
}
