package com.example.juniorcalendar.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.juniorcalendar.R;
import com.example.juniorcalendar.consultas.Login;
import com.example.juniorcalendar.consultas.Registro;
import com.example.juniorcalendar.consultas.TaskCompleted;

import org.json.JSONException;

public class MainActivityRegistro extends AppCompatActivity implements TaskCompleted {
    private Button botonAceptar;
    private EditText nombreRe,apellidosRe,dniRe,emailRe,contrasennaRe;
    private Registro registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_registro);
        botonAceptar=findViewById(R.id.buttonAceptarRegistro);
        nombreRe= findViewById(R.id.editTextNombreRegistro);
        apellidosRe=findViewById(R.id.editTextApellidosRegistro);
        dniRe=findViewById(R.id.editTextDniRegistro);
        int maxLenght=9;
        InputFilter[] filters=new InputFilter[1];
        filters[0]=new InputFilter.LengthFilter(maxLenght);
        dniRe.setFilters(filters);
        emailRe=findViewById(R.id.editTextEmailRegistro);
        contrasennaRe=findViewById(R.id.editTextContrasennaRegistro);



botonAceptar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        String dni = dniRe.getText().toString().toUpperCase();
        String nombre = nombreRe.getText().toString();
        String apellidos = apellidosRe.getText().toString();
        String email = emailRe.getText().toString();
        String contrasenna = contrasennaRe.getText().toString().toUpperCase();

        if (!dni.isEmpty() && !nombre.isEmpty()&& !apellidos.isEmpty()&& !email.isEmpty()&& !contrasenna.isEmpty()) {

            registro = new Registro(MainActivityRegistro.this);
            registro.execute(nombre,apellidos,dni,email, contrasenna);
            Intent intent = new Intent(MainActivityRegistro.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivityRegistro.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
        }
    }



});


        vaciarCampos();

    }


    @Override
    public void onTaskCompleted(String respuesta) throws JSONException {
    if (respuesta.equalsIgnoreCase("Exito")){

    Toast.makeText(MainActivityRegistro.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();

    } else {

    Toast.makeText(MainActivityRegistro.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();

}





    }
    private void vaciarCampos(){
        dniRe.setText("");
        nombreRe.setText("");
        apellidosRe.setText("");
        emailRe.setText("");
        contrasennaRe.setText("");

    }



}

