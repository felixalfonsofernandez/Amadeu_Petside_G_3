package com.fragments;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;

import com.example.registrarse.Adopcion;
import com.example.registrarse.GoogleMapsActivity;
import com.example.registrarse.Leyenda;
import com.example.registrarse.MainActivity;
import com.example.registrarse.R;
import com.example.registrarse.Recomendaciones;
import com.example.registrarse.apartadoPeliculas;
import com.example.registrarse.eleccionMascota;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class HomeFragment extends Fragment {

    Button btnsalir;
    Button siguiente;
    Button next;
    Button nextt;
    Button nexttt;

    public HomeFragment() {

        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        btnsalir=view.findViewById(R.id.btsalir);


        btnsalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                com.facebook.login.LoginManager.getInstance().logOut();
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });
        siguiente=(Button)view.findViewById(R.id.pelicula);

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), eleccionMascota.class);
                startActivity(i);
            }
        });
        next = view.findViewById(R.id.button);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (getContext(), Leyenda.class);
                startActivity(i);
            }
        });

        nextt = view.findViewById(R.id.button4);

        nextt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (getContext(), Adopcion.class);
                startActivity(i);
            }
        });

        nexttt = view.findViewById(R.id.Pantalla2);

        nexttt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (getContext(), Recomendaciones.class);
                startActivity(i);
            }
        });

        return view;
    }

}




        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false);
