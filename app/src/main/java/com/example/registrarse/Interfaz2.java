package com.example.registrarse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.content.pm.PackageManager;
import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.graphics.BitmapFactory;
import android.view.View;
import android.os.Environment;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.MediaStore;

import com.fragments.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.UploadTask;
import com.providers.AuthProvider;
import com.providers.ImageProvider;
import com.providers.UsersProvider;
import com.utils.FileUtil;


import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interfaz2 extends AppCompatActivity {
    //ImageView ivFoto;
    Button btnSeleccionarImagen;
    Button boton;
    File mImageFile;
    ImageView mImageViewPost1;
    ImageProvider mImageProvider;
    Uri imagenUri;

    int TOMAR_FOTO = 100;
    //int SELEC_IMAGEN = 200;

    String CARPETA_RAIZ = "MisFotosApp";
    String CARPETAS_IMAGENES = "imagenes";
    String RUTA_IMAGEN = CARPETA_RAIZ + CARPETAS_IMAGENES;
    String path;
    private DatabaseReference mDato;
    private String bb;
    private String bba;
    private String bbe;
    private String nom;
    private String apell;
    private String fon;
    private String edad;
    private int edadd;
    private Spinner spinner;
    private Spinner spinner2;
    private String mascota;
    private String raza;
    private EditText nombredemascota;
    private EditText nombrederaza;
    private final int GALLERY_REQUEST_CODE = 1;
    AuthProvider mAuthProvider;
    UsersProvider mUsersProvider;
    Button mButtonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaz2);
        mImageProvider = new ImageProvider();
        mDato= FirebaseDatabase.getInstance().getReference();
        bb = getIntent().getStringExtra("dato");
        bba =getIntent().getStringExtra("datob");
        bbe = getIntent().getStringExtra("datobb");
        nom = getIntent().getStringExtra("datobbb");
        apell = getIntent().getStringExtra("datobbbb");
        fon = getIntent().getStringExtra("datobbbbb");
        edad = getIntent().getStringExtra("datobbbbbb");
        edadd = Integer.parseInt(edad);
        initViews();
        mAuthProvider = new AuthProvider();
        mUsersProvider = new UsersProvider();

        nombredemascota = findViewById(R.id.editTextTextPersonName2);
        nombrederaza = findViewById(R.id.editTextRaza);
        mImageViewPost1 = findViewById(R.id.ivFoto);
        boton = findViewById(R.id.textView13);
        //ivFoto = findViewById(R.id.ivFoto);
        btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen);
        mButtonRegister = findViewById(R.id.submit);


        if(ContextCompat.checkSelfPermission(Interfaz2.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Interfaz2.this,
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        btnSeleccionarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionarImagen();
            }
        });
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerr();
            }
        });
    }
    private void initViews(){
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.mascota, android.R.layout.simple_spinner_item);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(staticAdapter);

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> staticAdapters = ArrayAdapter.createFromResource(this, R.array.edad, android.R.layout.simple_spinner_item);
        staticAdapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(staticAdapters);
    }


    public void tomarFoto() {
        String nombreImagen = "";
        File fileImagen = new File(Environment.getExternalStorageDirectory(), RUTA_IMAGEN);
        boolean isCreada = fileImagen.exists();

        if(isCreada == false) {
            isCreada = fileImagen.mkdirs();
            isCreada = fileImagen.mkdirs();
        }

        if(isCreada == true) {
            nombreImagen = (System.currentTimeMillis() / 1000) + ".jpg";
        }

        path = Environment.getExternalStorageDirectory()+File.separator+RUTA_IMAGEN+File.separator+nombreImagen;
        File imagen = new File(path);

        Intent intent = null;
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String authorities = this.getPackageName()+".provider";
            Uri imageUri = FileProvider.getUriForFile(this, authorities, imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }

        startActivityForResult(intent, TOMAR_FOTO);
    }

    private void saveImage(final String nom,final String bb,final String bba,final String apell,final String mascota,final String fon,final String edad,final String nuevo){
        mAuthProvider.register(bb,bba).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mImageProvider.save(Interfaz2.this, mImageFile).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){
                                mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        final String urlProfile = uri.toString();
                                        registrarUsuario(nom,bb,bba,apell,mascota,fon,edad,urlProfile,nuevo);
                                    }
                                });
                                Toast.makeText(Interfaz2.this, "Se almaceno correctamente",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(Interfaz2.this, "El peso de la foto debe estar entre 5 KB - 6 MB", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(getApplicationContext(), "NO SE PUDO REGISTRAR.",
                            Toast.LENGTH_SHORT).show();
                }

                // ...
            }
        });
    }

    private void saveImages(final String nom,final String bb,final String bba,final String apell,final String mascota,final String edad,final String nuevoo){
        mAuthProvider.register(bb,bba).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mImageProvider.save(Interfaz2.this, mImageFile).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){
                                mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        final String urlProfile = uri.toString();
                                        registrarUsuarios(nom,bb,bba,apell,fon,edad,urlProfile,nuevoo);
                                    }
                                });
                                Toast.makeText(Interfaz2.this, "Se almaceno correctamente",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(Interfaz2.this, "Hubo un error en la imagen", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(getApplicationContext(), "NO SE PUDO REGISTRAR.",
                            Toast.LENGTH_SHORT).show();
                }

                // ...
            }
        });
    }



    public void seleccionarImagen() {
        Intent galeria = new Intent(Intent.ACTION_GET_CONTENT);
        galeria.setType("image/*");
        startActivityForResult(galeria, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null) {

            if(resultCode == RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
                try {
                    //imagenUri = data.getData();
                    mImageFile = FileUtil.from(this,data.getData());
                    mImageViewPost1.setImageBitmap(BitmapFactory.decodeFile(mImageFile.getAbsolutePath()));
                    //ivFoto.setImageURI(imagenUri);
                } catch (Exception e) {
                    Log.d("Error","Se produjo un error"+e.getMessage());
                    Toast.makeText(this, "Se produjo un error" + e.getMessage(),Toast.LENGTH_LONG).show();
                }
            } else if(resultCode == RESULT_OK && requestCode == TOMAR_FOTO) {
                MediaScannerConnection.scanFile(Interfaz2.this, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String s, Uri uri) {

                    }
                });

                //Bitmap bitmap = BitmapFactory.decodeFile(path);
                //ivFoto.setImageBitmap(bitmap);
            }
        }
    }
    private void register() {
        mascota = nombredemascota.getText().toString();
        raza = nombrederaza.getText().toString();
        String nuevo = spinner2.getSelectedItem().toString();

        if(mascota.length()>=2 && mascota.length()<=45){
            if(raza.length()>=2 && raza.length()<=45){
                saveImage(nom,bb,bba,apell,mascota,fon,edad,nuevo);
            } else{
                Toast.makeText(this, "La raza de la mascota debe tener entre 2-45 caracteres", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(this, "El nombre debe tener entre 2-45 caracteres", Toast.LENGTH_SHORT).show();
        }
    }

    public void registrarUsuario(final String nom,final String bb,final String bba,final String apell,final String mascota,final String fon,final String edad,final String url,final String nuevo){
                    String id = mAuthProvider.getUid();
                    User user = new User();
                    user.setId(id);
                    user.setEmail(bb);
                    user.setUsername(nom);
                    user.setMascota(mascota);
                    user.setApell(apell);
                    user.setFon(fon);
                    user.setEdad(edad);
                    user.setImageCover(url);
                    user.setDescripcionmascota("-");
                    user.setEdadmascota(nuevo);
                    user.setTimestamp(new Date().getTime());
                    mUsersProvider.create(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Intent i =new Intent(getApplicationContext(), sisis.class);
                                startActivity(i);
                                Toast.makeText(Interfaz2.this,"El usuario se almaceno correctamente en la base de datos uwu", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Interfaz2.this,"El usuario no se pudo registrar correctamente",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
    }
    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]*+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void registrarUsuarios(final String nom,final String bb,final String bba,final String apell,final String fon,final String edad,final String url,final String nuevos){
        String id = mAuthProvider.getUid();
        User user = new User();
        user.setId(id);
        user.setEmail(bb);
        user.setUsername(nom);
        user.setMascota("-");
        user.setApell(apell);
        user.setFon(fon);
        user.setEdad(edad);
        user.setImageCover(url);
        user.setDescripcionmascota("-");
        user.setEdadmascota(nuevos);
        user.setTimestamp(new Date().getTime());
        mUsersProvider.create(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent i =new Intent(getApplicationContext(), sisis.class);
                    startActivity(i);
                    Toast.makeText(Interfaz2.this,"El usuario se almaceno correctamente en la base de datos uwu", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Interfaz2.this,"El usuario no se pudo registrar correctamente",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void registrarUsuariosinfono(final String nom,final String bb,final String bba,final String apell,final String edad){
        mAuthProvider.register(bb,bba).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String id = mAuthProvider.getUid();
                    User user = new User();
                    user.setId(id);
                    user.setEmail(bb);
                    user.setMascota("-");
                    user.setUsername(nom);
                    user.setApell(apell);
                    user.setFon("-");
                    user.setEdad(edad);
                    user.setDescripcionmascota("-");
                    user.setTimestamp(new Date().getTime());
                    mUsersProvider.create(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Interfaz2.this,"El usuario se almaceno correctamente en la base de datos uwu", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Interfaz2.this,"El usuario no se pudo registrar correctamente",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Intent i =new Intent(getApplicationContext(), sisis.class);
                    startActivity(i);
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(getApplicationContext(), "NO SE PUDO REGISTRAR.",
                            Toast.LENGTH_SHORT).show();
                }

                // ...
            }
        });

    }

    public void registrarUsuariosinfonoymascota(final String nom,final String bb,final String bba,final String apell,final String mascota,final String edad){
        mAuthProvider.register(bb,bba).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String id = mAuthProvider.getUid();
                    User user = new User();
                    user.setId(id);
                    user.setEmail(bb);
                    user.setMascota(mascota);
                    user.setUsername(nom);
                    user.setApell(apell);
                    user.setFon("-");
                    user.setEdad(edad);
                    user.setDescripcionmascota("-");
                    user.setTimestamp(new Date().getTime());
                    mUsersProvider.create(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Interfaz2.this,"El usuario se almaceno correctamente en la base de datos uwu", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Interfaz2.this,"El usuario no se pudo registrar correctamente",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Intent i =new Intent(getApplicationContext(), sisis.class);
                    startActivity(i);
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(getApplicationContext(), "NO SE PUDO REGISTRAR.",
                            Toast.LENGTH_SHORT).show();
                }

                // ...
            }
        });

    }

    private void registerr() {
        String nuevos = spinner2.getSelectedItem().toString();
        saveImages(nom,bb,bba,apell,mascota,edad,nuevos);
    }

}
