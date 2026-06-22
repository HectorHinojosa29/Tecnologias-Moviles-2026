package com.hector.escanercodigobarras
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class MainActivity : AppCompatActivity() {

    private lateinit var txtResultado: TextView

    private val barcodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result ->

        if (result.contents != null) {
            txtResultado.text = result.contents
        } else {
            txtResultado.text = "Escaneo cancelado"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnEscanear = findViewById<Button>(R.id.btnEscanear)
        txtResultado = findViewById(R.id.txtResultado)

        btnEscanear.setOnClickListener {

            val options = ScanOptions()
            options.setPrompt("Escanee un código de barras")
            options.setBeepEnabled(true)
            options.setOrientationLocked(false)
            barcodeLauncher.launch(options)
        }
    }
}