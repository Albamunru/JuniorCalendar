package com.example.juniorcalendar.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.juniorcalendar.R;
import com.example.juniorcalendar.consultas.RegistroEducacion;
import com.example.juniorcalendar.consultas.RegistroFamilia;
import com.example.juniorcalendar.consultas.TaskCompleted;

import org.json.JSONException;

public class MainActivityAnnadirEducacion extends AppCompatActivity implements TaskCompleted {

    private EditText evento,ubicacion,fecha,hora,descripcion,dni;
    private Button botonGuardar;
    private RegistroEducacion registroEducacion;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_annadir_educacion);
            evento=findViewById(R.id.editTextEvento);
            ubicacion=findViewById(R.id.editTextUbicacionEducacion);
            fecha=findViewById(R.id.editTextFechaEducacion);
            hora=findViewById(R.id.editTextHoraEducacion);
            descripcion=findViewById(R.id.editTextDescripcionEducacion);
            botonGuardar=findViewById(R.id.buttonGuardarEducacion);
            dni=findViewById(R.id.editTextdnininnoeducacion);
            int maxLenght=9;
            InputFilter[] filters=new InputFilter[1];
            filters[0]=new InputFilter.LengthFilter(maxLenght);
            dni.setFilters(filters);


            botonGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String dniGuardar=dni.getText().toString().toUpperCase();
                    String eventoGuardar=evento.getText().toString();
                    String ubicacionGuardar=ubicacion.getText().toString();
                    String fechaGuardar=fecha.getText().toString();
                    String horaGuardar=hora.getText().toString();
                    String descripcionGuardar=descripcion.getText().toString();
                    if (!eventoGuardar.isEmpty() && !ubicacionGuardar.isEmpty() && !fechaGuardar.isEmpty()&& !horaGuardar.isEmpty() && !descripcionGuardar.isEmpty() && !dniGuardar.isEmpty()) {

                        registroEducacion = new RegistroEducacion(MainActivityAnnadirEducacion.this);
                        registroEducacion.execute(fechaGuardar,horaGuardar,eventoGuardar,ubicacionGuardar,descripcionGuardar,dniGuardar);
                        Intent intent = new Intent(MainActivityAnnadirEducacion.this, MainActivityPrincipal.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivityAnnadirEducacion.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
                    }
                }

            });

        vaciarCampos();



    }

    @Override
    public void onTaskCompleted(String respuesta) throws JSONException {
        if (respuesta.equalsIgnoreCase("Exito")){

            Toast.makeText(MainActivityAnnadirEducacion.this, "Datos registrados exitosamente", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(MainActivityAnnadirEducacion.this, "Error al registrar los datos", Toast.LENGTH_SHORT).show();

        }

    }

    private void vaciarCampos(){
        evento.setText("");
        ubicacion.setText("");
        fecha.setText("");
        hora.setText("");
        descripcion.setText("");
        dni.setText("");


    }
}