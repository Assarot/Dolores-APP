package com.example.doloresapp.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.doloresapp.R
import com.example.doloresapp.presentation.viewmodel.ProductosViewModel
import com.example.doloresapp.presentation.adapters.ProductosAdapter
import com.example.doloresapp.di.ServiceLocator
import com.example.doloresapp.presentation.viewmodel.ProductosViewModelFactory

class ProductosFragment : Fragment(R.layout.listproducts_layout) {
    private val productosViewModel: ProductosViewModel by viewModels {
        ProductosViewModelFactory(
            ServiceLocator.getProductosUseCase(),
            ServiceLocator.getCategoriasUseCase()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configuración del RecyclerView
        val adapter = ProductosAdapter(emptyList())
        val recyclerView: RecyclerView = view.findViewById(R.id.products_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Observar los productos
        productosViewModel.productos.observe(viewLifecycleOwner) { productos ->
            adapter.submitList(productos)  // Actualizar la lista de productos
        }

        // Observar las categorías si es necesario
        productosViewModel.categorias.observe(viewLifecycleOwner) { categorias ->
            // Aquí puedes manejar las categorías si las estás utilizando para algún filtro
        }

        // Llamar a los métodos para cargar productos y categorías
        productosViewModel.loadProductos()  // Cargar productos
        productosViewModel.loadCategorias()  // Cargar categorías
    }
}