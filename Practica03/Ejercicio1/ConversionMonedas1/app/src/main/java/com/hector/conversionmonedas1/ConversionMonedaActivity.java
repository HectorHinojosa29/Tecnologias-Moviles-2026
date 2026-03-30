package com.hector.conversionmonedas1;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import java.util.Locale;

public class ConversionMonedaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private EditText editTextCantidad;
    private TextView textViewResultado;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion_moneda);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editTextCantidad = findViewById(R.id.editTextCantidad);
        textViewResultado = findViewById(R.id.textViewResultado);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }
    public void convertirADolares(View view) {
        realizarConversion(3.80, "Dólares");
    }
    public void convertirAEuros(View view) {
        realizarConversion(4.10, "Euros");
    }
    private void realizarConversion(double tasa, String moneda) {
        if (editTextCantidad == null) return;
        String cantidadStr = editTextCantidad.getText().toString();
        if (!cantidadStr.isEmpty()) {
            try {
                double soles = Double.parseDouble(cantidadStr);
                double resultado = soles / tasa;
                String textoResultado = String.format(Locale.getDefault(), "%s: %.2f", moneda, resultado);
                textViewResultado.setText(textoResultado);
            } catch (NumberFormatException e) {
                textViewResultado.setText("Valor inválido");
            }
        } else {
            textViewResultado.setText("Ingrese un valor");
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_inicio) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_conversion) {
            drawerLayout.closeDrawers();
        } else if (id == R.id.nav_acerca) {
            Toast.makeText(this, "App de Conversión de Monedas", Toast.LENGTH_SHORT).show();
            drawerLayout.closeDrawers();
        }
        return true;
    }
}