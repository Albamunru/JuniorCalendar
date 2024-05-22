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
import com.example.juniorcalendar.consultas.RegistroNinno;
import com.example.juniorcalendar.consultas.TaskCompleted;

import org.json.JSONException;

public class MainActivityRegistroNinno extends AppCompatActivity implements TaskCompleted {
private Button botonGuardar;
private EditText nombre,apellidos, fechaNacimiento,dni;
private RegistroNinno registroNinno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_registro_ninno);


        botonGuardar=findViewById(R.id.buttonGuardarNinno);
        nombre=findViewById(R.id.editTextNombreNinno);
        apellidos=findViewById(R.id.editTextTextApellidosNinno);
        fechaNacimiento=findViewById(R.id.editTextTextFechaNacimientoNinno);
        dni=findViewById(R.id.editTextTextDniNinno);
        int maxLenght=9;
        InputFilter[] filters=new InputFilter[1];
        filters[0]=new InputFilter.LengthFilter(maxLenght);
        dni.setFilters(filters);


        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreGuardar=nombre.getText().toString();
                String apellidosGuardar=apellidos.getText().toString();
                String fechaNacimientoGuardar=fechaNacimiento.getText().toString();
                String dniGuardar=dni.getText().toString().toUpperCase();
                if (!nombreGuardar.isEmpty() && !apellidosGuardar.isEmpty()&& !fechaNacimientoGuardar.isEmpty()&& !dniGuardar.isEmpty()) {

                    registroNinno = new RegistroNinno(MainActivityRegistroNinno.this);
                    registroNinno.execute(nombreGuardar,apellidosGuardar,fechaNacimientoGuardar,dniGuardar);
                    Intent intent = new Intent(MainActivityRegistroNinno.this, MainActivityPrincipal.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivityRegistroNinno.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
                }
            }



        });
        vaciarCampos();



}

    @Override
    public void onTaskCompleted(String respuesta) throws JSONException {
        if (respuesta.equalsIgnoreCase("Exito")){

            Toast.makeText(MainActivityRegistroNinno.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(MainActivityRegistroNinno.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();

        }

    }


    private void vaciarCampos(){
        nombre.setText("");
        apellidos.setText("");
        fechaNacimiento.setText("");
        dni.setText("");

    }



}