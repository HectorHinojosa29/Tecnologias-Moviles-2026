package com.hector.inventariotaller

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hector.inventariotaller.data.AppDatabase
import com.hector.inventariotaller.data.model.Material
import com.hector.inventariotaller.data.repository.MaterialRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MaterialAdapter
    private lateinit var repository: MaterialRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 🔑 La Activity SOLO conoce al Repository, no al DAO
        val dao = AppDatabase.getInstance(this).materialDao()
        repository = MaterialRepository(dao)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etCategoria = findViewById<EditText>(R.id.etCategoria)
        val etStock = findViewById<EditText>(R.id.etStock)
        val etPrecio = findViewById<EditText>(R.id.etPrecio)
        val etProveedor = findViewById<EditText>(R.id.etProveedor)
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)
        val rv = findViewById<RecyclerView>(R.id.rvMateriales)

        adapter = MaterialAdapter(emptyList()) { material ->
            lifecycleScope.launch { repository.eliminar(material) }
            Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show()
        }
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        btnAgregar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            if (nombre.isEmpty()) {
                Toast.makeText(this, "Falta el nombre", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val material = Material(
                nombre = nombre,
                categoria = etCategoria.text.toString().trim(),
                stock = etStock.text.toString().toIntOrNull() ?: 0,
                precioUnitario = etPrecio.text.toString().toDoubleOrNull() ?: 0.0,
                proveedor = etProveedor.text.toString().trim()
            )
            lifecycleScope.launch { repository.insertar(material) }

            listOf(etNombre, etCategoria, etStock, etPrecio, etProveedor)
                .forEach { it.text.clear() }
        }

        // Observamos el Flow expuesto por el Repository
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                repository.materiales.collect { lista ->
                    adapter.actualizar(lista)
                }
            }
        }
    }
}