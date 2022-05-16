package com.example.registrarse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Leyenda extends AppCompatActivity {
    Button btnsalir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leyenda);
        btnsalir = findViewById(R.id.btnsalir);

    }
    public void irmaps(View view){
        startActivity(new Intent(Leyenda.this,GoogleMapsActivity.class));

    }

}