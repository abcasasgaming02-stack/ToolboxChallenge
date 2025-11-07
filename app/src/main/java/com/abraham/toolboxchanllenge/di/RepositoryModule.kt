package com.abraham.toolboxchanllenge.di

import com.abraham.toolboxchanllenge.data.api.ApiService
import com.abraham.toolboxchanllenge.data.repository.Repository
import com.abraham.toolboxchanllenge.data.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePhotosRepository(
        api: ApiService
    ): Repository = RepositoryImpl(api)

}