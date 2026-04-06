package com.hector.actividad3

import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    var timePicker: TimePicker? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        timePicker = findViewById<View?>(R.id.timePicker) as TimePicker
        timePicker!!.setIs24HourView(true)
    }

    fun onClick(view: View?) {
        Toast.makeText(
            getBaseContext(), "Hora seleccionada" +
                    timePicker!!.getCurrentHour() + ":" + timePicker!!.getCurrentMinute(),
            Toast.LENGTH_SHORT
        ).show()
    }
}
