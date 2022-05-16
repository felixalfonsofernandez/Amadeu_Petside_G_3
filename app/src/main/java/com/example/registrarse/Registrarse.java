package com.example.registrarse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registrarse extends AppCompatActivity {
    //Button submit;
    //EditText editText;
    //EditText nombre;
    private EditText correo;
    private EditText contraseña;
    private EditText contraseñaConfirmacion;
    private EditText nombre;
    private EditText apellido;
    private EditText telefono;
    private EditText edad;
    private String correoo;
    private String contraseñaa;
    private String contraseñaConfirmacionn;
    private String nombree;
    private String apellidoo;
    private String telefonoo;
    private String edadd;
    private int edaddd;
    Button next;
    EditText etDate;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        /*etDate = findViewById(R.id.et_date);
        Calendar calendar = Calendar.getInstance();*/
        //nombre = (EditText) findViewById(R.id.nombre);
        //editText = findViewById(R.id.editText);
        //submit = findViewById(R.id.submit);


        correo = findViewById(R.id.editText);
        contraseña = findViewById(R.id.editTextTextPassword);
        contraseñaConfirmacion = findViewById(R.id.contraseñaConfirmacion);
        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.editTextTextPersonName);
        telefono = findViewById(R.id.textInputPhone);
        edad = findViewById(R.id.editTextEdad);

        next = findViewById(R.id.submit);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar();
            }
        });

        /*final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);*/

        /*submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nombre.length()==0){
                    nombre.setError("Digite un nombre");
                }
                else if(editText.length()==0){
                    editText.setError("Digite un email");
                }
                else{
                    Toast.makeText(Registrarse.this,"Vuelve a digitar",Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        /*etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Registrarse.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1 ;
                        String date = day + "/" + month + "/" + year;
                        etDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });*/


    }


    public void validar() {
        correoo = correo.getText().toString();
        contraseñaa = contraseña.getText().toString();
        contraseñaConfirmacionn = contraseñaConfirmacion.getText().toString();
        nombree = nombre.getText().toString();
        apellidoo = apellido.getText().toString();
        telefonoo = telefono.getText().toString();
        edadd = edad.getText().toString();
        if(!edadd.isEmpty()){
            edaddd = Integer.parseInt(edadd);
        }
        if (!nombree.isEmpty() && !correoo.isEmpty() && !contraseñaa.isEmpty() && !contraseñaConfirmacionn.isEmpty() && !apellidoo.isEmpty() && !telefonoo.isEmpty() && !edadd.isEmpty()) {
            if (nombree.length() >= 2 && nombree.length() <=45) {
                if (isEmailValid(correoo)) {
                    if (contraseñaa.equals(contraseñaConfirmacionn)) {
                        if (apellidoo.length() >= 2 && apellidoo.length()<=60) {
                            if (telefonoo.length() == 9 && telefonoo.charAt(0) == '9') {
                                if (edaddd >= 18 && edaddd <= 75) {
                                    if (contraseñaa.length() >= 6 && contraseñaa.length() <= 30) {
                                        iralla();
                                    } else {
                                        Toast.makeText(this, "La contraseña debe tener entre 6-30 caracteres", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(this, "Usted debe tener entre 18-75 años para acceder a esta aplicación", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(this, "Ingresar correctamente el número de celular", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "El apellido debe tener entre 2-60 caracteres", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Correo inválido", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "El nombre debe tener entre 2-45 caracteres", Toast.LENGTH_SHORT).show();
            }
        } else if (!nombree.isEmpty() && !correoo.isEmpty() && !contraseñaa.isEmpty() && !contraseñaConfirmacionn.isEmpty() && !apellidoo.isEmpty() && telefonoo.isEmpty() && !edadd.isEmpty()) {
            if (nombree.length() >= 2 && nombree.length() <=45) {
                if (isEmailValid(correoo)) {
                    if (contraseñaa.equals(contraseñaConfirmacionn)) {
                        if (apellidoo.length() >= 2 && apellidoo.length()<=60) {
                            if (edaddd >= 18 && edaddd <= 75) {
                                if (contraseñaa.length() >= 6 && contraseñaa.length() <= 30) {
                                    iralla();
                                } else {
                                    Toast.makeText(this, "La contraseña debe tener entre 6-30 caracteres", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(this, "Usted debe tener entre 18-75 años para acceder a esta aplicación", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "El apellido debe tener entre 2-60 caracteres", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Las contraseña no coinciden", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Correo inválido", Toast.LENGTH_LONG).show();
                }
            }  else {
                Toast.makeText(this, "El nombre debe tener entre 2-45 caracteres", Toast.LENGTH_SHORT).show();
            }
        } else if (nombree.isEmpty() && !correoo.isEmpty() && !contraseñaa.isEmpty() && !contraseñaConfirmacionn.isEmpty() && !apellidoo.isEmpty() && !telefonoo.isEmpty() && !edadd.isEmpty()) {
            Toast.makeText(this, "Ingresar el nombre para registrarse en esta aplicación", Toast.LENGTH_SHORT).show();
        } else if (!nombree.isEmpty() && !correoo.isEmpty() && !contraseñaa.isEmpty() && !contraseñaConfirmacionn.isEmpty() && apellidoo.isEmpty() && !telefonoo.isEmpty() && !edadd.isEmpty()) {
            Toast.makeText(this, "Ingresar el apellido para registrarse en esta aplicación", Toast.LENGTH_SHORT).show();
        } else if (!nombree.isEmpty() && !correoo.isEmpty() && !contraseñaa.isEmpty() && !contraseñaConfirmacionn.isEmpty() && !apellidoo.isEmpty() && !telefonoo.isEmpty() && edadd.isEmpty()) {
            Toast.makeText(this, "Ingresar la edad para registrarse en esta aplicación", Toast.LENGTH_SHORT).show();
        } else if (!nombree.isEmpty() && correoo.isEmpty() && !contraseñaa.isEmpty() && !contraseñaConfirmacionn.isEmpty() && !apellidoo.isEmpty() && !telefonoo.isEmpty() && !edadd.isEmpty()) {
            Toast.makeText(this, "Ingresar el correo electrónico para registrarse en esta aplicación", Toast.LENGTH_SHORT).show();
        } else if (!nombree.isEmpty() && !correoo.isEmpty() && contraseñaa.isEmpty() && !contraseñaConfirmacionn.isEmpty() && !apellidoo.isEmpty() && !telefonoo.isEmpty() && !edadd.isEmpty()) {
            Toast.makeText(this, "Ingresar la contraseña", Toast.LENGTH_SHORT).show();
        } else if (!nombree.isEmpty() && !correoo.isEmpty() && !contraseñaa.isEmpty() && contraseñaConfirmacionn.isEmpty() && !apellidoo.isEmpty() && !telefonoo.isEmpty() && !edadd.isEmpty()) {
            Toast.makeText(this, "Ingresar nuevamente la contraseña", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Para continuar inserta todos los campos (opcional el telefono)", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isEmailValid(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public void iralla(){
        Intent i = new Intent(this,Interfaz2.class);
        i.putExtra("dato",correo.getText().toString());
        i.putExtra("datob",contraseña.getText().toString());
        i.putExtra("datobb",contraseñaConfirmacion.getText().toString());
        i.putExtra("datobbb",nombre.getText().toString());
        i.putExtra("datobbbb",apellido.getText().toString());
        i.putExtra("datobbbbb",telefono.getText().toString());
        i.putExtra("datobbbbbb",edad.getText().toString());
        startActivity(i);
    }

}