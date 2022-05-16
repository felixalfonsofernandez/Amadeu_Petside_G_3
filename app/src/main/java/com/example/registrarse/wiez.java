package com.example.registrarse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class wiez extends AppCompatActivity {
    Button btnsalir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiez);
        btnsalir = findViewById(R.id.btnsalir);
    }

    public void irmaps(View view){
        startActivity(new Intent(wiez.this,HomeActivity.class));

    }
}