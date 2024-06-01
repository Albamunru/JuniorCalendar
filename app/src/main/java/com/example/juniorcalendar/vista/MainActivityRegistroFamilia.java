package com.example.juniorcalendar.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
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
        ninno=findViewById(R.id.editTextTextDniNinnoFamilia);
        botonAceptar=findViewById(R.id.buttonAceptarFamilia);



        if (tutorUno != null && ninno != null) {
            int maxLenght = 9;
            InputFilter[] filters = new InputFilter[1];
            filters[0] = new InputFilter.LengthFilter(maxLenght);
            tutorUno.setFilters(filters);
            ninno.setFilters(filters);
        } else {
            // Maneja el caso donde los EditText no fueron encontrados
            Log Log = null;
            Log.e("MainActivityRegistroFamilia", "Uno o ambos EditText no pudieron ser encontrados");
            Toast.makeText(this, "Error al inicializar los campos de texto", Toast.LENGTH_SHORT).show();
            return; // Sale del método onCreate si los EditText no son encontrados
        }



        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dniUnoGuardar = tutorUno.getText().toString().toUpperCase();
                String dniNinnoGuardar = ninno.getText().toString().toUpperCase();

                if (!dniUnoGuardar.isEmpty()  && !dniNinnoGuardar.isEmpty()) {

                    registroFamilia = new RegistroFamilia(MainActivityRegistroFamilia.this);
                    registroFamilia.execute(dniUnoGuardar, dniNinnoGuardar);
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
        ninno.setText("");


    }


}