package com.example.juniorcalendar.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.juniorcalendar.R;
import com.example.juniorcalendar.Utiles.Utiles;
import com.example.juniorcalendar.consultas.RegistroEducacion;
import com.example.juniorcalendar.consultas.RegistroMedico;
import com.example.juniorcalendar.consultas.TaskCompleted;

import org.json.JSONException;

public class MainActivityAnnadirSalud extends AppCompatActivity implements TaskCompleted {

    private EditText fechaMedico,horaMedico,especialidad,observacion,ubicacion;
    private Button botonGuardarMedico;
    private RegistroMedico registroMedico;
    private Utiles utiles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_annadir_salud);

        fechaMedico=findViewById(R.id.editTextFechaSalud);
        horaMedico=findViewById(R.id.editTextHoraMedico);
        especialidad=findViewById(R.id.editTextEspecialidadSalud);
        observacion=findViewById(R.id.editTextObservacionMedico);
        ubicacion=findViewById(R.id.editTextUbicacionMedico);
        botonGuardarMedico=findViewById(R.id.buttonGuardarMedico);
        utiles=new Utiles();


        botonGuardarMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String horaMedicoGuardar = horaMedico.getText().toString();
                String especialidadGuardar = especialidad.getText().toString();
                String fechaGuardar = fechaMedico.getText().toString();
                String observacionGuardar = observacion.getText().toString();
                String ubicacionGuardar = ubicacion.getText().toString();

                String fechaFormateada= utiles.formatearFecha(fechaGuardar);

                if (!horaMedicoGuardar.isEmpty() && !ubicacionGuardar.isEmpty() && !fechaFormateada.isEmpty() && !observacionGuardar.isEmpty() && !especialidadGuardar.isEmpty()) {
                    registroMedico = new RegistroMedico(MainActivityAnnadirSalud.this, new TaskCompleted() {
                        @Override
                        public void onTaskCompleted(String result) {
                            if (result.equals("Exito")) {
                                Intent intent = new Intent(MainActivityAnnadirSalud.this, MainActivityPrincipal.class);
                                startActivity(intent);
                                Toast.makeText(MainActivityAnnadirSalud.this, "Registro insertado correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivityAnnadirSalud.this, "Error al insertar el registro", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    registroMedico.execute(fechaFormateada, horaMedicoGuardar, especialidadGuardar,observacionGuardar, ubicacionGuardar);
                } else {
                    Toast.makeText(MainActivityAnnadirSalud.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });


        vaciarCampos();


    }


    @Override
    public void onTaskCompleted(String respuesta) throws JSONException {
        if (respuesta.equalsIgnoreCase("Exito")){

            Toast.makeText(MainActivityAnnadirSalud.this, "Datos registrados exitosamente", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(MainActivityAnnadirSalud.this, "Error al registrar los datos", Toast.LENGTH_SHORT).show();

        }

    }

    private void vaciarCampos(){
        fechaMedico.setText("");
        ubicacion.setText("");
        horaMedico.setText("");
        especialidad.setText("");
        ubicacion.setText("");




    }




}