package com.example.doloresapp.di

import com.example.doloresapp.data.remote.service.ProductoApiService
import com.example.doloresapp.data.repository.ProductoRepositoryImpl
import com.example.doloresapp.domain.repository.ProductoRepository
import com.example.doloresapp.domain.usecase.GetCategoriasUseCase
import com.example.doloresapp.domain.usecase.GetProductosUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {
    // TODO: Cambia esta baseUrl a la de tu API cuando esté lista
    private const val BASE_URL = "http://10.0.2.2:8080/"

    @Volatile
    private var retrofit: Retrofit? = null

    @Volatile
    private var apiService: ProductoApiService? = null

    @Volatile
    private var repository: ProductoRepository? = null

    fun getRetrofit(): Retrofit {
        return retrofit ?: synchronized(this) {
            retrofit ?: Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .also { retrofit = it }
        }
    }

    fun getApiService(): ProductoApiService {
        return apiService ?: synchronized(this) {
            apiService ?: getRetrofit().create(ProductoApiService::class.java).also { apiService = it }
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
