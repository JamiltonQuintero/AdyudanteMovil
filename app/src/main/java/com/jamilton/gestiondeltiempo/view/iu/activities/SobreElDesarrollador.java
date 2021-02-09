package com.jamilton.gestiondeltiempo.view.iu.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;

import com.jamilton.gestiondeltiempo.R;

public class SobreElDesarrollador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre_el_desarrollador);

        Toolbar toolbar = findViewById(R.id.miToll);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Acerca del desarollador");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);


    }

    @Override
    public boolean onSupportNavigateUp() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        return super.onSupportNavigateUp();
    }


}