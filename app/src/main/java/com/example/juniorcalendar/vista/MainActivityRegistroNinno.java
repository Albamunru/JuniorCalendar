package com.example.juniorcalendar.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.juniorcalendar.R;

public class MainActivityRegistroNinno extends AppCompatActivity {
private Button botonGuardar;
private EditText nombre,apellidos, fechaNacimiento,dni;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_registro_ninno);


        botonGuardar=findViewById(R.id.buttonGuardarNinno);
        nombre=findViewById(R.id.editTextNombreNinno);
        apellidos=findViewById(R.id.editTextTextApellidosNinno);
        fechaNacimiento=findViewById(R.id.editTextTextFechaNacimientoNinno);
        dni=findViewById(R.id.editTextTextDniNinno);

    }
}