package com.example.doloresapp.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.doloresapp.R

class HomeFragment : Fragment(R.layout.home_screen) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Click en "Comprar Productos" -> navegar a ProductosFragment (lista de productos)
        view.findViewById<View>(R.id.cardComprar)?.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, ProductosFragment())
                .addToBackStack(null)
                .commit()
        }

        // Click en "Mis Pedidos" -> navegar al CartFragment (carrito)
        view.findViewById<View>(R.id.cardPedidos)?.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, CartFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
