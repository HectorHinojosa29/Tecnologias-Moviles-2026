package com.hector.gridview

import android.os.Bundle
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var gridView: GridView
    private lateinit var listaPizzas: List<Pizza>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridView = findViewById(R.id.gridViewPizzas)

        listaPizzas = listOf(
            Pizza("Pizza Hawaiana", R.drawable.pizza_hawaiana),
            Pizza("Pizza Pepperoni", R.drawable.pizza_pepperoni),
            Pizza("Pizza Americana", R.drawable.pizza_americana),
            Pizza("Pizza Vegetariana", R.drawable.pizza_vegetariana),
            Pizza("Pizza Suprema", R.drawable.pizza_suprema),
            Pizza("Pizza Cuatro Quesos", R.drawable.pizza_cuatro_quesos)
        )

        val adapter = PizzaAdapter(this, listaPizzas)
        gridView.adapter = adapter

        gridView.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(this, listaPizzas[position].nombre, Toast.LENGTH_SHORT).show()
        }
    }
}