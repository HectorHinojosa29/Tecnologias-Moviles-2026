package com.hector.conversionmonedas;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        progressBar = findViewById(R.id.progressBar);
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
    public void abrirConversion(View view) {
        ejecutarCargaConversion();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {
            Toast.makeText(this, "Ya estás en el Inicio", Toast.LENGTH_SHORT).show();
            drawerLayout.closeDrawers();
        } 
        else if (id == R.id.nav_conversion) {
            drawerLayout.closeDrawers();
            ejecutarCargaConversion();
        } 
        else if (id == R.id.nav_acerca) {
            Toast.makeText(this, "App de Conversión de Monedas ", Toast.LENGTH_SHORT).show();
            drawerLayout.closeDrawers();
        }

        return true;
    }

    private void ejecutarCargaConversion() {
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Carga completa", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ConversionMonedaActivity.class);
            startActivity(intent);
        }, 1500);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}