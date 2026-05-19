package com.hector.inventariotaller

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hector.inventariotaller.data.AppDatabase
import com.hector.inventariotaller.data.model.Material
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dao = AppDatabase.getInstance(this).materialDao()

        lifecycleScope.launch {
            // CREATE
            dao.insertar(Material(
                nombre = "Tela algodón blanco",
                categoria = "Tela",
                stock = 50,
                precioUnitario = 12.50,
                proveedor = "Textiles Arequipa SAC"
            ))

            val lista = dao.listarTodos()
            lista.forEach { Log.d("CRUD", "📦 ${it.nombre} - Stock: ${it.stock}") }

            lista.firstOrNull()?.let {
                dao.actualizar(it.copy(stock = 45))
            }

        }
    }
}