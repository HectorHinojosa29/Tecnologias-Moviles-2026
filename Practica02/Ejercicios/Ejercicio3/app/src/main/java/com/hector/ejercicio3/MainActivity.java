package com.hector.ejercicio3;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    RadioButton radioDolares, radioSoles;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        radioDolares = findViewById(R.id.radio0);
        radioSoles = findViewById(R.id.radio1);
        btn = findViewById(R.id.btnConvertir);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertir();
            }
        });
    }

    private void convertir() {
        String texto = editText.getText().toString();

        if (texto.isEmpty()) {
            Toast.makeText(this, "Ingrese un valor", Toast.LENGTH_SHORT).show();
            return;
        }

        double cantidad = Double.parseDouble(texto);
        double tipoCambio = 3.65;

        if (radioDolares.isChecked()) {
            double soles = cantidad * tipoCambio;
            Toast.makeText(this, cantidad + " dólares = " + soles + " soles", Toast.LENGTH_LONG).show();
        } else {
            double dolares = cantidad / tipoCambio;
            Toast.makeText(this, cantidad + " soles = " + dolares + " dólares", Toast.LENGTH_LONG).show();
        }
    }
}