package com.example.doloresapp.di

import com.example.doloresapp.data.remote.service.ProductoApiService
import com.example.doloresapp.data.repository.ProductoRepositoryImpl
import com.example.doloresapp.domain.repository.ProductoRepository
import com.example.doloresapp.domain.usecase.GetCategoriasUseCase
import com.example.doloresapp.domain.usecase.GetProductosUseCase
import com.example.doloresapp.data.remote.NetworkClient

object ServiceLocator {
    @Volatile
    private var apiService: ProductoApiService? = null

    @Volatile
    private var repository: ProductoRepository? = null

    fun getApiService(): ProductoApiService {
        return apiService ?: synchronized(this) {
            apiService ?: NetworkClient.createService(ProductoApiService::class.java).also { apiService = it }
        }
    }

    fun getRepository(): ProductoRepository {
        return repository ?: synchronized(this) {
            repository ?: ProductoRepositoryImpl(getApiService()).also { repository = it }
        }
    }

    fun getProductosUseCase(): GetProductosUseCase = GetProductosUseCase(getRepository())

    fun getCategoriasUseCase(): GetCategoriasUseCase = GetCategoriasUseCase(getRepository())
}
