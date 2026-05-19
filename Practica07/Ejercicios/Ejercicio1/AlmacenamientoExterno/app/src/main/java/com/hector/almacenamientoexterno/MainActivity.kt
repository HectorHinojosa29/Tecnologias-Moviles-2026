package com.hector.almacenamientoexterno

import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class MainActivity : AppCompatActivity() {

    private val NOMBRE_ARCHIVO = "miArchivo.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val etContenido = findViewById<EditText>(R.id.etContenido)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnLeer = findViewById<Button>(R.id.btnLeer)
        val tvResultado = findViewById<TextView>(R.id.tvResultado)

        val carpeta = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)

        btnGuardar.setOnClickListener {
            val texto = etContenido.text.toString()
            if (texto.isNotEmpty()) {
                guardarArchivo(texto)
                Toast.makeText(this, "Archivo guardado", Toast.LENGTH_SHORT).show()
                etContenido.text.clear()
            } else {
                Toast.makeText(this, "Escribe algo primero", Toast.LENGTH_SHORT).show()
            }
        }
        btnLeer.setOnClickListener {
            val contenido = leerArchivo()
            tvResultado.text = if (contenido.isNotEmpty()) {
                "Contenido leído:\n$contenido"
            } else {
                "El archivo está vacío o no existe."
            }
        }
    }

    private fun guardarArchivo(contenido: String) {
        try {
            val carpeta = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            val archivo = File(carpeta, NOMBRE_ARCHIVO)

            archivo.writeText(contenido)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error al guardar: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun leerArchivo(): String {
        return try {
            val carpeta = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            val archivo = File(carpeta, NOMBRE_ARCHIVO)

            if (archivo.exists()) {
                archivo.readText()
            } else {
                ""
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Error al leer: ${e.message}"
        }
    }
}