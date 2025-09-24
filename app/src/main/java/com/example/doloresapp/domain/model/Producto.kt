package com.example.doloresapp.domain.model

data class Producto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val precioOferta: Double?,
    val imagenUrl: String?,
    val stock: Int,
    val categoriaId: Int
)

data class Categoria(
    val id: Int,
    val nombre: String
)

interface ProductoRepository {
    suspend fun getProductos(): List<Producto>
    suspend fun getCategorias(): List<Categoria>
    suspend fun searchProductos(query: String): List<Producto>
}
