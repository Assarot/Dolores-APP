package com.example.doloresapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.util.Log
import com.example.doloresapp.domain.model.Categoria
import com.example.doloresapp.domain.model.Producto
import com.example.doloresapp.domain.usecase.GetCategoriasUseCase
import com.example.doloresapp.domain.usecase.GetProductosUseCase
import kotlinx.coroutines.launch

class ProductosViewModel(
    private val getProductosUseCase: GetProductosUseCase,
    private val getCategoriasUseCase: GetCategoriasUseCase
    ) : ViewModel() {

        val productos = MutableLiveData<List<Producto>>()
        val categorias = MutableLiveData<List<Categoria>>()

        fun loadProductos() {
            viewModelScope.launch {
                try {
                    productos.value = getProductosUseCase.execute()  // Obtener productos
                } catch (e: Exception) {
                    Log.e("ProductosViewModel", "Error cargando productos", e)
                    productos.value = emptyList()
                }
            }
        }

        fun loadCategorias() {
            viewModelScope.launch {
                try {
                    categorias.value = getCategoriasUseCase.execute()  // Obtener categorías
                } catch (e: Exception) {
                    Log.e("ProductosViewModel", "Error cargando categorías", e)
                    categorias.value = emptyList()
                }
            }
        }
}