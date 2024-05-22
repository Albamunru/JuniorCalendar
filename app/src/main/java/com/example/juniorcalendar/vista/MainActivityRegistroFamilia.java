package com.example.juniorcalendar.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.juniorcalendar.R;
import com.example.juniorcalendar.consultas.RegistroFamilia;
import com.example.juniorcalendar.consultas.TaskCompleted;

import org.json.JSONException;

public class MainActivityRegistroFamilia extends AppCompatActivity implements TaskCompleted {
private EditText tutorUno,tutorDos,ninno;
private Button botonAceptar;
private RegistroFamilia registroFamilia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_registro_familia);

        tutorUno=findViewById(R.id.editTextTextUsuarioUno);
        int maxLenght=9;
        InputFilter[] filters=new InputFilter[1];
        filters[0]=new InputFilter.LengthFilter(maxLenght);
        tutorUno.setFilters(filters);
        tutorDos=findViewById(R.id.editTextTextUsuarioDos);
        tutorDos.setFilters(filters);
        ninno=findViewById(R.id.editTextTextDniNinnoFamilia);
        ninno.setFilters(filters);
        botonAceptar=findViewById(R.id.buttonAceptarFamilia);



        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dniUnoGuardar = tutorUno.getText().toString().toUpperCase();
                String dniDosGuardar = tutorDos.getText().toString().toUpperCase();
                String dniNinnoGuardar = ninno.getText().toString().toUpperCase();

                if (!dniUnoGuardar.isEmpty() && !dniDosGuardar.isEmpty() && !dniNinnoGuardar.isEmpty()) {

                    registroFamilia = new RegistroFamilia(MainActivityRegistroFamilia.this);
                    registroFamilia.execute(dniUnoGuardar, dniDosGuardar, dniNinnoGuardar);
                    Intent intent = new Intent(MainActivityRegistroFamilia.this, MainActivityPrincipal.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivityRegistroFamilia.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
                }
            }

        });

        vaciarCampos();

    }

    @Override
    public void onTaskCompleted(String respuesta) throws JSONException {
        if (respuesta.equalsIgnoreCase("Exito")){

            Toast.makeText(MainActivityRegistroFamilia.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(MainActivityRegistroFamilia.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();

        }

    }


    private void vaciarCampos(){
        tutorUno.setText("");
        tutorDos.setText("");
        ninno.setText("");


    }


}