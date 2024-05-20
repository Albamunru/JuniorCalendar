package com.example.juniorcalendar.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.juniorcalendar.R;
import com.example.juniorcalendar.consultas.Login;
import com.example.juniorcalendar.consultas.RecuperarContrasenna;
import com.example.juniorcalendar.consultas.TaskCompleted;
import com.example.juniorcalendar.modelo.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivityOlvidarContrasenna extends AppCompatActivity implements TaskCompleted {
private EditText usuarioBuscar,contrasennaEncontrada;
private Button botonBuscar;
private RecuperarContrasenna recuperarContrasenna;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_olvidar_contrasenna);
        usuarioBuscar=findViewById(R.id.editTextTextContrasennaRecuperacion);
        contrasennaEncontrada=findViewById(R.id.editTextContrasennaEncontrada);
        contrasennaEncontrada.setEnabled(false);
        botonBuscar=findViewById(R.id.buttonBuscarContrasenna);

    botonBuscar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String emailIntroducido=usuarioBuscar.getText().toString();
        if (!emailIntroducido.isEmpty()) {
            // Crear una instancia de LoginTask y ejecutarla con los datos de usuario y contraseña
            recuperarContrasenna = new RecuperarContrasenna(MainActivityOlvidarContrasenna.this);
            recuperarContrasenna.execute(emailIntroducido);
        } else {
            Toast.makeText(MainActivityOlvidarContrasenna.this, "Por favor, rellene el campo ", Toast.LENGTH_SHORT).show();

        }
    }

    });

    }

    @Override
    public void onTaskCompleted(String datoEncontrado) throws JSONException {


        if (datoEncontrado != null) {


            try {
                // Aquí parsea la respuesta JSON y crea un objeto Usuario
                JSONArray jsonArray = new JSONArray(datoEncontrado);

                if (jsonArray.length() > 0) {
JSONObject jsonObject=jsonArray.getJSONObject(0);

                    String contrasenna = jsonObject.getString("contraseña");
                    contrasennaEncontrada.setText(contrasenna);
                    contrasennaEncontrada.setVisibility(View.VISIBLE);


                    new CountDownTimer(3000, 1000) {
                        public void onTick(long millisUntilFinished) {

                        }

                        public void onFinish() {

                            contrasennaEncontrada.setVisibility(View.GONE);
                        }
                    }.start();

                }else{
                    contrasennaEncontrada.setText("Datos no encontrados");
                    contrasennaEncontrada.setVisibility(View.VISIBLE);

                    // Configurar un temporizador para ocultar el TextView después de 3 segundos
                    new CountDownTimer(3000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            // No es necesario hacer nada en cada tick
                        }

                        public void onFinish() {
                            // Ocultar el TextView cuando el temporizador finaliza
                            contrasennaEncontrada.setVisibility(View.GONE);
                        }
                    }.start();

                }


            } catch (JSONException e) {
                Log.e("JSON PARSE ERROR", e.getMessage());
            }




        } else {
            contrasennaEncontrada.setText("Datos no encontrados");
            contrasennaEncontrada.setVisibility(View.VISIBLE);

            // Configurar un temporizador para ocultar el TextView después de 3 segundos
            new CountDownTimer(3000, 1000) {
                public void onTick(long millisUntilFinished) {
                    // No es necesario hacer nada en cada tick
                }

                public void onFinish() {
                    // Ocultar el TextView cuando el temporizador finaliza
                    contrasennaEncontrada.setVisibility(View.GONE);
                }
            }.start();
        }
        }



    }
