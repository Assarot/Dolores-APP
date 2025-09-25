package com.example.doloresapp.data.remote.service

import com.example.doloresapp.data.remote.dto.CategoriaDTO
import com.example.doloresapp.data.remote.dto.ProductoDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductoApiService {
    @GET("productos")
    suspend fun getProductos(): List<ProductoDTO>

    @GET("categorias")
    suspend fun getCategorias(): List<CategoriaDTO>

    @GET("productos/{id}")
    suspend fun getProducto(@Path("id") id: Int): ProductoDTO
}
