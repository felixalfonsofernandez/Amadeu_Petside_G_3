package com.example.registrarse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Recomendaciones extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendaciones);
    }
    public void Limpieza (View view){
        setContentView(R.layout.button2);
    }

    public void Suciedad (View view){
        setContentView(R.layout.button3);
    }

    public void Olores (View view){
        setContentView(R.layout.button4);
    }

    public void Bucal (View view){
        setContentView(R.layout.button5);
    }

    public void Pulgas (View view){
        setContentView(R.layout.button6);
    }
}