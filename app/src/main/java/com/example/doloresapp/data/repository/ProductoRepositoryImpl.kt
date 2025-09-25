package com.example.doloresapp.data.repository

import com.example.doloresapp.data.remote.service.ProductoApiService
import com.example.doloresapp.domain.model.Categoria
import com.example.doloresapp.domain.model.Producto
import com.example.doloresapp.domain.model.toDomain
import com.example.doloresapp.domain.repository.ProductoRepository

class ProductoRepositoryImpl(private val apiService: ProductoApiService) : ProductoRepository {
    override suspend fun getProductos(): List<Producto> {
        val productosDto = apiService.getProductos()  // Devuelve una lista de DTOs
        return productosDto.map { it.toDomain() }  // Convertir los DTOs a modelos de dominio
    }

    override suspend fun getCategorias(): List<Categoria> {
        val categoriasDto = apiService.getCategorias()  // Devuelve una lista de DTOs
        return categoriasDto.map { it.toDomain() }  // Convertir los DTOs a modelos de dominio
    }

    override suspend fun searchProductos(query: String): List<Producto> {
        val productosDto = apiService.getProductos()  // Obtienes todos los productos
        return productosDto.filter { it.nombre.contains(query, ignoreCase = true) }
            .map { it.toDomain() }  // Filtra y convierte a modelos de dominio
    }
}