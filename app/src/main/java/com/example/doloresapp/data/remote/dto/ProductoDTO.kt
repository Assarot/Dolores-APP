package com.example.doloresapp.data.remote.dto

data class ProductoDTO(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val precioOferta: Double?,
    val imagenUrl: String?,
    val stock: Int,
    val categoriaId: Int
)