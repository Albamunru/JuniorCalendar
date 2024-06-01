package com.example.juniorcalendar.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.juniorcalendar.R;

public class MainActivityAnnadirSalud extends AppCompatActivity {

    private EditText fechaMedico,horaMedico,especialidad,observacion,ubicacion;
    private Button guardarMedico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_annadir_salud);

        fechaMedico=findViewById(R.id.editTextFechaSalud);
        horaMedico=findViewById(R.id.editTextHoraMedico);
        especialidad=findViewById(R.id.editTextEspecialidadSalud);
        observacion=findViewById(R.id.editTextObservacionMedico);
        ubicacion=findViewById(R.id.editTextUbicacionMedico);
        guardarMedico=findViewById(R.id.buttonGuardarMedico);
    }
}