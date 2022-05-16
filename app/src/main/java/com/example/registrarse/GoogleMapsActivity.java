package com.example.registrarse;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getCameraPosition();
        Antut(googleMap);
        // Add a marker in Sydney and move the camera


    }

    public void Antut (GoogleMap googleMap) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mMap = googleMap;
        LatLng sydney = new LatLng(-11.991458, -77.071099);
        LatLng sydney2 = new LatLng(-12.204071021444019, -77.01677948543332);
        LatLng sydney3 = new LatLng(-12.204688735517395, -77.01292327059066);
        LatLng sydney4 = new LatLng(-12.196945734344695, -77.01180715621365);
        LatLng sydney5 = new LatLng(-12.192346006443644, -77.01975522346697);
        LatLng sydney6 = new LatLng(-12.1870186537087, -77.01285998303116);
        LatLng sydney7 = new LatLng(-12.194425319863292, -77.01263711425045);
        LatLng sydney8 = new LatLng(-12.196168034744055, -76.99807635238729);
        LatLng sydney9 = new LatLng(-12.18186444460022, -76.98226450101357);
        LatLng sydney10 = new LatLng(-12.243801198785869, -76.88530827949855);
        LatLng sydney11 = new LatLng(-12.242148696426042, -76.87191934120287);
        db.collection("Ubicaciones")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                int primero = Math.toIntExact(document.getLong("x"));
                                int segundo = Math.toIntExact(document.getLong("y"));
                                String nombre = document.getString("Ubicacion");
                                LatLng sydney11 = new LatLng(primero, segundo);
                                mMap.addMarker(new MarkerOptions().position(sydney11).title(nombre).icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.reff)));
                            }
                        } else {

                        }
                    }
                });

        mMap.addMarker(new MarkerOptions().position(sydney).title("VETERINARIA PEPIT0").icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pets_2)));
        mMap.addMarker(new MarkerOptions().position(sydney2).title("VETERINARIA KUSI ALKHO").icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pets_2)));
        mMap.addMarker(new MarkerOptions().position(sydney3).title("ANIMAL CLINIC PERU").icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pets_2)));
        mMap.addMarker(new MarkerOptions().position(sydney4).title("VETERINARIA MIS MASKOTAS").icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pets_2)));
        mMap.addMarker(new MarkerOptions().position(sydney5).title("EMERGENCIA ANIMAL").icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pets_2)));
        mMap.addMarker(new MarkerOptions().position(sydney6).title("VETERINARIA CHIP'S EIRL").icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pets_2)));
        mMap.addMarker(new MarkerOptions().position(sydney7).title("EMERGENCIA ANIMAL VETERINARIA").icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pets_2)));
        mMap.addMarker(new MarkerOptions().position(sydney8).title("CLINICA VETERINARIA ESPERANZA").icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_pets_2)));
        mMap.addMarker(new MarkerOptions().position(sydney9).title("ALBERGUE AMOR Y RESCATE").icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.reff)));
        mMap.addMarker(new MarkerOptions().position(sydney10).title("EL REFUGIO PERU - HOSPEDAJE GUARDERIA DE MASCOTAS PERROS Y GATOS EN LIMA").icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.reff)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));
    }



    private BitmapDescriptor bitmapDescriptorFromVector(Context context,int vectorResId){
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0,0,vectorDrawable.getIntrinsicWidth(),vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),vectorDrawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}