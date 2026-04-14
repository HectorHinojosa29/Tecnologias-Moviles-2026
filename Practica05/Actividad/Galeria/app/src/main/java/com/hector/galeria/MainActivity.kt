package com.hector.galeria

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var recyclerView: RecyclerView

    private val productos = listOf(
        Producto("Proteina Whey Gold Pro", 10, 25.99, R.drawable.pic01),
        Producto("Proteina Whey Gold Standard", 5, 29.99, R.drawable.pic02),
        Producto("Proteina Whey Pure", 8, 29.99 , R.drawable.pic03),
        Producto("Proteina Powder", 8, 28.99, R.drawable.pic04),
        Producto("Proteina Mervick", 11, 21.99, R.drawable.pic05),
        Producto("Proteina Blend", 15, 22.99, R.drawable.pic06),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val adapter = ProductoAdapter(productos) { position ->
            val producto = productos[position]
            // Mostrar info en el ImageView o un Toast
            imageView.setImageResource(producto.imagenResId)
            Toast.makeText(
                this,
                "${producto.nombre}\nCantidad: ${producto.cantidad}\nPrecio: $${producto.precio}",
                Toast.LENGTH_SHORT
            ).show()
        }

        recyclerView.adapter = adapter
    }
}


