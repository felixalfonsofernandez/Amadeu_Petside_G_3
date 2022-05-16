package com.example.registrarse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class apartadoPeliculas extends AppCompatActivity {
    Button bee;
    Button damyvag;
    Button hachiko;
    Button madag;
    Button pets;
    Button panch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartado_peliculas);
        bee=(Button)findViewById(R.id.botonbeethoven);
        bee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(apartadoPeliculas.this, beethoven.class);
                startActivity(i);
            }
        });

        damyvag=(Button)findViewById(R.id.botondamayvag);
        damyvag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(apartadoPeliculas.this, ladamayelvagabundo.class);
                startActivity(i);
            }
        });

        hachiko=(Button)findViewById(R.id.botonhachiko);
        hachiko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(apartadoPeliculas.this, hachikop.class);
                startActivity(i);
            }
        });

        madag=(Button)findViewById(R.id.botonmadagascar);
        madag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(apartadoPeliculas.this, madagascar.class);
                startActivity(i);
            }
        });

        pets=(Button)findViewById(R.id.botonlifeofpets);
        pets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(apartadoPeliculas.this, lifeofpets.class);
                startActivity(i);
            }
        });

        panch=(Button)findViewById(R.id.botonpancho);
        panch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(apartadoPeliculas.this, pancho.class);
                startActivity(i);
            }
        });
    }
}