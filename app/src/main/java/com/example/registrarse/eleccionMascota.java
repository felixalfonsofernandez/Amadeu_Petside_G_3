package com.example.registrarse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class eleccionMascota extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleccion_mascota);
    }
    public void Felino (View view){
        Intent felino = new Intent(this, Felino.class);
        startActivity(felino);
    }

    public void Canino (View view){
        Intent canino = new Intent(this, Canino.class);
        startActivity(canino);
    }
}