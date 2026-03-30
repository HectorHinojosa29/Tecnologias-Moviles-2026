package com.hector.conversion_monedas

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextCantidad: EditText
    private lateinit var spinnerOrigen: Spinner
    private lateinit var spinnerDestino: Spinner
    private lateinit var txtResultado: TextView

    private val monedas = arrayOf(
        "Soles", "Dólares", "Euro", "Libra", "Rupia",
        "Real", "Peso", "Yuan", "Yen"
    )

    private val tasas = mapOf(
        "Soles" to 1.0,
        "Dólares" to 3.65,
        "Euro" to 3.95,
        "Libra" to 4.60,
        "Rupia" to 0.044,
        "Real" to 0.74,
        "Peso" to 0.21,
        "Yuan" to 0.50,
        "Yen" to 0.025
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextCantidad = findViewById(R.id.editTextCantidad)
        spinnerOrigen = findViewById(R.id.spinnerOrigen)
        spinnerDestino = findViewById(R.id.spinnerDestino)
        txtResultado = findViewById(R.id.txtResultado)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, monedas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerOrigen.adapter = adapter
        spinnerDestino.adapter = adapter
    }

    fun convertirMoneda(view: View) {
        val cantidadTexto = editTextCantidad.text.toString()

        if (cantidadTexto.isEmpty()) {
            txtResultado.text = "Ingrese una cantidad válida"
            return
        }

        val cantidad = cantidadTexto.toDouble()

        val origen = spinnerOrigen.selectedItem.toString()
        val destino = spinnerDestino.selectedItem.toString()

        val tasaOrigen = tasas[origen]!!
        val tasaDestino = tasas[destino]!!

        val enSoles = cantidad * tasaOrigen
        val resultado = enSoles / tasaDestino

        txtResultado.text = "$cantidad $origen = %.2f $destino".format(resultado)
    }
}