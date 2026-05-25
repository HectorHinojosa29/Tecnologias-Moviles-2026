package com.hector.registroestudiante

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListaActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EstudianteAdapter

    private val listaEstudiantes = mutableListOf<Estudiante>()

    private lateinit var estudiantesRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lista)

        recyclerView = findViewById(R.id.recyclerEstudiantes)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = EstudianteAdapter(listaEstudiantes)
        recyclerView.adapter = adapter

        estudiantesRef = FirebaseDatabase
            .getInstance()
            .getReference("Estudiantes")

        leerEstudiantes()
    }

    private fun leerEstudiantes() {
        // ValueEventListener -> lectura en tiempo real de TODO el nodo.
        // Cada vez que cambia algo en "Estudiantes", onDataChange se vuelve a llamar.
        estudiantesRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                listaEstudiantes.clear()
                for (hijo in snapshot.children) {
                    val estudiante = hijo.getValue(Estudiante::class.java)
                    if (estudiante != null) {
                        listaEstudiantes.add(estudiante)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@ListaActivity,
                    "Error al leer: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}