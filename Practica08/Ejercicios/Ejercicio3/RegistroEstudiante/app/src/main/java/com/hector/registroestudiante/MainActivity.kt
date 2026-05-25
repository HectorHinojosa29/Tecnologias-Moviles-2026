package com.hector.registroestudiante

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.content.Intent

class MainActivity : AppCompatActivity() {

    private lateinit var txtNombre: EditText
    private lateinit var txtCarrera: EditText
    private lateinit var txtCurso: EditText
    private lateinit var btnGuardar: Button

    private lateinit var estudiantesRef: DatabaseReference

    private lateinit var btnVerLista: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        txtNombre = findViewById(R.id.txtnombre)
        txtCarrera = findViewById(R.id.txtcarrera)
        txtCurso = findViewById(R.id.txtcurso)
        btnGuardar = findViewById(R.id.btnguardar)

        estudiantesRef = FirebaseDatabase
            .getInstance()
            .getReference("Estudiantes")

        btnGuardar.setOnClickListener {

            guardarEstudiante()
        }

        btnVerLista = findViewById(R.id.btnVerLista)
        btnVerLista.setOnClickListener {
            startActivity(Intent(this, ListaActivity::class.java))
        }
    }

    private fun guardarEstudiante() {

        val nombre = txtNombre.text.toString()
        val carrera = txtCarrera.text.toString()
        val curso = txtCurso.text.toString()

        if (nombre.isEmpty() || carrera.isEmpty() || curso.isEmpty()) {

            Toast.makeText(
                this,
                "Complete todos los campos",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val id = estudiantesRef.push().key ?: return

        val estudiante = Estudiante(
            id,
            nombre,
            carrera,
            curso
        )

        estudiantesRef.child(id)
            .setValue(estudiante)
            .addOnSuccessListener {

                Toast.makeText(
                    this,
                    "Estudiante guardado",
                    Toast.LENGTH_SHORT
                ).show()

                txtNombre.text.clear()
                txtCarrera.text.clear()
                txtCurso.text.clear()
            }
            .addOnFailureListener {

                Toast.makeText(
                    this,
                    "Error al guardar",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}