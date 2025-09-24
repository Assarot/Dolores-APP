package com.example.doloresapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import android.util.Patterns
import com.example.doloresapp.R
import com.example.doloresapp.data.local.TokenStore
import com.example.doloresapp.data.remote.NetworkClient
import com.example.doloresapp.data.remote.LoginApi
import com.example.doloresapp.presentation.viewmodel.AuthRequest
import kotlinx.coroutines.launch
import com.google.android.material.textfield.TextInputEditText
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa almacenamiento de token y navega si ya hay sesión
        TokenStore.init(applicationContext)
        TokenStore.getToken()?.let { existing ->
            if (existing.isNotBlank()) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                return
            }
        }

        // Inicializa red/almacenamiento de token
        NetworkClient.init(applicationContext)

        // Habilita edge-to-edge para ocupar toda la pantalla
        enableEdgeToEdge()
        setContentView(R.layout.login_layout)

        // Aplica padding según los system bars (status/navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login_root)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val emailInput: TextInputEditText = findViewById(R.id.email_input)
        val passwordInput: TextInputEditText = findViewById(R.id.password_input)
        val loginButton: com.google.android.material.button.MaterialButton = findViewById(R.id.acceder_button)

        val api = NetworkClient.createService(LoginApi::class.java)

        loginButton.setOnClickListener {
            val email = emailInput.text?.toString()?.trim().orEmpty()
            val password = passwordInput.text?.toString().orEmpty()

            if (!isValidEmail(email)) {
                emailInput.error = "Correo inválido"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                passwordInput.error = "Ingresa tu contraseña"
                return@setOnClickListener
            }

            loginButton.isEnabled = false
            lifecycleScope.launch {
                try {
                    val resp = api.login(AuthRequest(correo = email, password = password))
                    // Guardar token
                    TokenStore.saveToken(resp.token)
                    Toast.makeText(this@LoginActivity, "Login exitoso", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this@LoginActivity, e.message ?: "Error de autenticación", Toast.LENGTH_LONG).show()
                } finally {
                    loginButton.isEnabled = true
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean =
        email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
