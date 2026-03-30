package com.hector.conversionmonedas;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ConversionMonedaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private EditText editTextCantidad;
    private TextView textViewResultado;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion_moneda);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editTextCantidad = findViewById(R.id.editTextCantidad);
        textViewResultado = findViewById(R.id.textViewResultado);
        progressBar = findViewById(R.id.progressBarConversion);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open_drawer,
                R.string.close_drawer
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }
    public void convertirADolares(View view) {
        ejecutarConversion("Dolares");
    }

    public void convertirAEuros(View view) {
        ejecutarConversion("Euros");
    }

    private void ejecutarConversion(String tipo) {
        String cantidadStr = editTextCantidad.getText().toString();
        if (cantidadStr.isEmpty()) {
            textViewResultado.setText("Ingrese un valor");
            return;
        }

        double soles = Double.parseDouble(cantidadStr);
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Carga completa", Toast.LENGTH_SHORT).show();

            if (tipo.equals("Dolares")) {
                double dolares = soles / 3.80;
                textViewResultado.setText("Dólares: " + String.format("%.2f", dolares));
            } else {
                double tipoCambioEuro = 4.10;
                double euros = soles / tipoCambioEuro;
                textViewResultado.setText("Euros: " + String.format("%.2f", euros));
            }
        }, 1500);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } 
        else if (id == R.id.nav_conversion) {
            drawerLayout.closeDrawers();
            Toast.makeText(this, "Ya estás en Conversión", Toast.LENGTH_SHORT).show();
        } 
        else if (id == R.id.nav_acerca) {
            Toast.makeText(this, "App de Conversión de Monedas ", Toast.LENGTH_SHORT).show();
            drawerLayout.closeDrawers();
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}