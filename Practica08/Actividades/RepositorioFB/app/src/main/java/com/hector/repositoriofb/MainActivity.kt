package com.hector.repositoriofb

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import android.widget.ArrayAdapter
import android.widget.ListView
class MainActivity : AppCompatActivity() {
    private lateinit var txtTema: EditText
    private lateinit var spinAreas: Spinner
    private lateinit var spinSecciones: Spinner
    private lateinit var btnRegistrar: Button
    private lateinit var btnMostrar: Button
    private lateinit var listaClases: ListView
    private lateinit var listaDatos: ArrayList<String>
    private lateinit var clasesRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        // Ajuste de los WindowInsets requerido por enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v,
                                                                             insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }
        // Referencia al nodo raíz "Clases" en Realtime Database
        clasesRef = FirebaseDatabase.getInstance().getReference("Clases")
        txtTema = findViewById(R.id.editTextText)
        spinAreas = findViewById(R.id.spinarea)
        spinSecciones = findViewById(R.id.spinseccion)
        btnRegistrar = findViewById(R.id.btnregistrar)
        btnMostrar = findViewById(R.id.btnmostrar)
        listaClases = findViewById(R.id.listaclases)
        listaDatos = ArrayList()

        btnRegistrar.setOnClickListener {
            registrarClase()
        }
        btnMostrar.setOnClickListener {
            mostrarClases()
        }
    }

    private fun registrarClase() {
        val seccion = spinSecciones.selectedItem.toString()
        val area = spinAreas.selectedItem.toString()
        val tema = txtTema.text.toString()
        if (tema.isEmpty()) {
            Toast.makeText(this, "Escribe el tema antes de registrar", Toast.LENGTH_SHORT).show()
            return
        }
        // push() genera una clave única para el nuevo nodo
        val id = clasesRef.child("Lecciones").push().key ?: return
        val leccion = Clase(
            claseid = id,
            seccion = seccion,
            area = area,
            tema = tema
        )
        clasesRef.child("Lecciones").child(id).setValue(leccion)
            .addOnSuccessListener {
                Toast.makeText(this, "Clase Registrada", Toast.LENGTH_LONG).show()
                txtTema.text.clear()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun mostrarClases() {

        clasesRef.child("Lecciones")
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    listaDatos.clear()

                    for (claseSnap in snapshot.children) {

                        val clase = claseSnap.getValue(Clase::class.java)

                        val texto =
                            "Sección: ${clase?.seccion}\n" +
                                    "Área: ${clase?.area}\n" +
                                    "Tema: ${clase?.tema}"

                        listaDatos.add(texto)
                    }

                    val adapter = ArrayAdapter(
                        this@MainActivity,
                        android.R.layout.simple_list_item_1,
                        listaDatos
                    )

                    listaClases.adapter = adapter
                }

                override fun onCancelled(error: DatabaseError) {

                    Toast.makeText(
                        this@MainActivity,
                        "Error al leer datos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}