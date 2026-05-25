package com.hector.registroestudiante

import android.app.AlertDialog
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
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

        adapter = EstudianteAdapter(
            listaEstudiantes,
            onEditar = { estudiante -> mostrarDialogoEditar(estudiante) },
            onEliminar = { estudiante -> confirmarEliminar(estudiante) }
        )
        recyclerView.adapter = adapter

        estudiantesRef = FirebaseDatabase
            .getInstance()
            .getReference("Estudiantes")

        leerEstudiantes()
    }

    private fun leerEstudiantes() {
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

    private fun mostrarDialogoEditar(estudiante: Estudiante) {

        // Campos precargados con los datos actuales
        val inputNombre = EditText(this).apply {
            hint = "Nombre"
            setText(estudiante.nombre)
        }
        val inputCarrera = EditText(this).apply {
            hint = "Carrera"
            setText(estudiante.carrera)
        }
        val inputCurso = EditText(this).apply {
            hint = "Curso"
            setText(estudiante.curso)
        }

        val contenedor = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 24, 48, 0)
            addView(inputNombre)
            addView(inputCarrera)
            addView(inputCurso)
        }

        AlertDialog.Builder(this)
            .setTitle("Editar estudiante")
            .setView(contenedor)
            .setPositiveButton("Actualizar") { _, _ ->

                val nuevoNombre = inputNombre.text.toString()
                val nuevaCarrera = inputCarrera.text.toString()
                val nuevoCurso = inputCurso.text.toString()

                if (nuevoNombre.isEmpty() ||
                    nuevaCarrera.isEmpty() ||
                    nuevoCurso.isEmpty()
                ) {
                    Toast.makeText(
                        this,
                        "Complete todos los campos",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setPositiveButton
                }

                val cambios = mapOf<String, Any>(
                    "nombre" to nuevoNombre,
                    "carrera" to nuevaCarrera,
                    "curso" to nuevoCurso
                )

                estudiantesRef.child(estudiante.id)
                    .updateChildren(cambios)
                    .addOnSuccessListener {
                        Toast.makeText(
                            this,
                            "Estudiante actualizado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            this,
                            "Error al actualizar",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun confirmarEliminar(estudiante: Estudiante) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar estudiante")
            .setMessage("¿Desea eliminar a ${estudiante.nombre}?")
            .setPositiveButton("Eliminar") { _, _ ->
                estudiantesRef.child(estudiante.id)
                    .removeValue()
                    .addOnSuccessListener {
                        Toast.makeText(
                            this,
                            "Estudiante eliminado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            this,
                            "Error al eliminar",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}