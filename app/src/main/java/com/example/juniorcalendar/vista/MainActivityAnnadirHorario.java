package com.example.juniorcalendar.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.juniorcalendar.R;
import com.example.juniorcalendar.consultas.RegistroEducacion;
import com.example.juniorcalendar.consultas.RegistroHorario;
import com.example.juniorcalendar.consultas.TaskCompleted;

import org.json.JSONException;

public class MainActivityAnnadirHorario extends AppCompatActivity implements TaskCompleted {
    private EditText dniDestinatario,petecion,nota;
    private Button botonGuardar;
    private RegistroHorario registroHorario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_annadir_horario);

        dniDestinatario=findViewById(R.id.editTextDestinatarioHorario);
        petecion=findViewById(R.id.editTextTextPeriodoCambio);
        nota=findViewById(R.id.editTextNotaHorario);
        botonGuardar=findViewById(R.id.buttonGuardarHorario);


        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dniDestinatarioGuardar = dniDestinatario.getText().toString().toUpperCase();
                String peticionGuardar = petecion.getText().toString();
                String notaGuardar = nota.getText().toString();

                if (!dniDestinatarioGuardar.isEmpty() && !peticionGuardar.isEmpty() && !notaGuardar.isEmpty()){
                    registroHorario = new RegistroHorario(MainActivityAnnadirHorario.this, new TaskCompleted() {
                        @Override
                        public void onTaskCompleted(String result) {
                            if (result.equals("Exito")) {
                                Intent intent = new Intent(MainActivityAnnadirHorario.this, MainActivityPrincipal.class);
                                startActivity(intent);
                                Toast.makeText(MainActivityAnnadirHorario.this, "Registro insertado correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivityAnnadirHorario.this, "Error al insertar el registro", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    registroHorario.execute(dniDestinatarioGuardar, peticionGuardar, notaGuardar);
                } else {
                    Toast.makeText(MainActivityAnnadirHorario.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });


        vaciarCampos();

    }






    @Override
    public void onTaskCompleted(String respuesta) throws JSONException {

        if (respuesta.equalsIgnoreCase("Exito")){

            Toast.makeText(MainActivityAnnadirHorario.this, "Datos registrados exitosamente", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(MainActivityAnnadirHorario.this, "Error al registrar los datos", Toast.LENGTH_SHORT).show();

        }



    }


    private void vaciarCampos(){
        dniDestinatario.setText("");
        nota.setText("");
        petecion.setText("");

    }


}