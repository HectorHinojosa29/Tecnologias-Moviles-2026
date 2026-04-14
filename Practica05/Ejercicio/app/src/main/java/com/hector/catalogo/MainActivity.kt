package com.hector.catalogo

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etCantidad: EditText
    private lateinit var etPrecio: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnCargar: Button
    private lateinit var recyclerProductos: RecyclerView

    private lateinit var prefs: SharedPreferences
    private val listaProductos = ArrayList<Producto>()
    private lateinit var adapter: ProductoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNombre = findViewById(R.id.etNombre)
        etCantidad = findViewById(R.id.etCantidad)
        etPrecio = findViewById(R.id.etPrecio)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnCargar = findViewById(R.id.btnCargar)
        recyclerProductos = findViewById(R.id.recyclerProductos)

        prefs = getSharedPreferences("productos_pref", MODE_PRIVATE)

        adapter = ProductoAdapter(listaProductos)
        recyclerProductos.layoutManager = LinearLayoutManager(this)
        recyclerProductos.adapter = adapter

        btnGuardar.setOnClickListener {
            guardarProducto()
        }

        btnCargar.setOnClickListener {
            cargarProductos()
        }

    }

    private fun guardarProducto() {
        val nombre = etNombre.text.toString().trim()
        val cantidadTexto = etCantidad.text.toString().trim()
        val precioTexto = etPrecio.text.toString().trim()

        if (nombre.isEmpty() || cantidadTexto.isEmpty() || precioTexto.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val cantidad = cantidadTexto.toInt()
        val precio = precioTexto.toDouble()

        val producto = Producto(nombre, cantidad, precio)
        listaProductos.add(producto)
        adapter.notifyDataSetChanged()

        guardarEnSharedPreferences()

        etNombre.text.clear()
        etCantidad.text.clear()
        etPrecio.text.clear()

        Toast.makeText(this, "Producto guardado", Toast.LENGTH_SHORT).show()
    }

    private fun guardarEnSharedPreferences() {
        val editor = prefs.edit()

        editor.putInt("total_productos", listaProductos.size)

        for (i in listaProductos.indices) {
            editor.putString("nombre_$i", listaProductos[i].nombre)
            editor.putInt("cantidad_$i", listaProductos[i].cantidad)
            editor.putString("precio_$i", listaProductos[i].precio.toString())
        }

        editor.apply()
    }

    private fun cargarProductos() {
        listaProductos.clear()

        val total = prefs.getInt("total_productos", 0)

        for (i in 0 until total) {
            val nombre = prefs.getString("nombre_$i", "") ?: ""
            val cantidad = prefs.getInt("cantidad_$i", 0)
            val precio = prefs.getString("precio_$i", "0.0")?.toDouble() ?: 0.0

            listaProductos.add(Producto(nombre, cantidad, precio))
        }

        adapter.notifyDataSetChanged()

        Toast.makeText(this, "Productos cargados", Toast.LENGTH_SHORT).show()
    }
}