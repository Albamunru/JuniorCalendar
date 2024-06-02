package com.example.juniorcalendar.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.juniorcalendar.R;
import com.example.juniorcalendar.Utiles.Utiles;
import com.example.juniorcalendar.consultas.RegistroEducacion;
import com.example.juniorcalendar.consultas.RegistroOcio;
import com.example.juniorcalendar.consultas.TaskCompleted;

import org.json.JSONException;

public class MainActivityAnnadirOcio extends AppCompatActivity implements TaskCompleted {

    private EditText nombreActividad,horaActividad,ubicacionActividad,descripcionActividad,fechaActividad;
    private Button botonGuardarActividad;
    private Utiles utiles;
    private RegistroOcio registroOcio;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_annadir_ocio);

        nombreActividad=findViewById(R.id.editTextNombreActividadOcio);
        horaActividad=findViewById(R.id.editTextHoraOcio);
        ubicacionActividad=findViewById(R.id.editTextUbicacionOcio);
        descripcionActividad=findViewById(R.id.editTextDescripcionOcio);
        fechaActividad=findViewById(R.id.editTextFechaActividad);
        utiles=new Utiles();

        botonGuardarActividad=findViewById(R.id.buttonGuardarOcio);

        botonGuardarActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreGuardar = nombreActividad.getText().toString();
                String horaGuardar = horaActividad.getText().toString();
                String fechaGuardar = fechaActividad.getText().toString();
                String ubicacionGuardar = ubicacionActividad.getText().toString();
                String descripcionGuardar = descripcionActividad.getText().toString();

                String fechaFormateada= utiles.formatearFecha(fechaGuardar);


                if (!nombreGuardar.isEmpty() && !ubicacionGuardar.isEmpty() && !fechaFormateada.isEmpty() && !horaGuardar.isEmpty() && !descripcionGuardar.isEmpty()) {
                    registroOcio = new RegistroOcio(MainActivityAnnadirOcio.this, new TaskCompleted() {
                        @Override
                        public void onTaskCompleted(String result) {
                            if (result.equals("Exito")) {
                                Intent intent = new Intent(MainActivityAnnadirOcio.this, MainActivityPrincipal.class);
                                startActivity(intent);
                                Toast.makeText(MainActivityAnnadirOcio.this, "Registro insertado correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivityAnnadirOcio.this, "Error al insertar el registro", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    registroOcio.execute(fechaFormateada, horaGuardar, nombreGuardar, ubicacionGuardar, descripcionGuardar);
                } else {
                    Toast.makeText(MainActivityAnnadirOcio.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });


        vaciarCampos();


    }

    @Override
    public void onTaskCompleted(String respuesta) throws JSONException {
        if (respuesta.equalsIgnoreCase("Exito")){

            Toast.makeText(MainActivityAnnadirOcio.this, "Datos registrados exitosamente", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(MainActivityAnnadirOcio.this, "Error al registrar los datos", Toast.LENGTH_SHORT).show();

        }

    }

    private void vaciarCampos(){
        nombreActividad.setText("");
        ubicacionActividad.setText("");
        fechaActividad.setText("");
        horaActividad.setText("");
        descripcionActividad.setText("");



    }


}