package com.example.juniorcalendar.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juniorcalendar.R;
import com.example.juniorcalendar.consultas.Login;
import com.example.juniorcalendar.consultas.TaskCompleted;
import com.example.juniorcalendar.modelo.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskCompleted {
    private Button botonRegistro, botonOlvidarContrasenna, botonAcceder;
    private EditText usuarioLog, contrasennaLog;
    private Login login;
    private TextView tvError;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        botonRegistro = findViewById(R.id.buttonRegistrarseInicio);
        botonOlvidarContrasenna = findViewById(R.id.buttonOlvideContrasenna);
        botonAcceder = findViewById(R.id.buttonAccederInicio);
        usuarioLog = findViewById(R.id.EditTextUsuario);
        contrasennaLog = findViewById(R.id.editTextContrasenna);
        tvError=findViewById(R.id.textViewerror);




        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegistro = new Intent(view.getContext(), MainActivityRegistro.class);
                startActivity(intentRegistro);

            }
        });




        botonAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String usuario = usuarioLog.getText().toString();
                String contrasenna = contrasennaLog.getText().toString();

                if (!usuario.isEmpty() && !contrasenna.isEmpty()) {
                    // Crear una instancia de LoginTask y ejecutarla con los datos de usuario y contraseña
                    login = new Login(MainActivity.this);
                    login.execute(usuario, contrasenna);

                    SharedPreferences sharedPreferences=getSharedPreferences("UsuarioSesion",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("usuario",usuario);
                    editor.apply();



                } else {
                    Toast.makeText(MainActivity.this, "Por favor, ingresa usuario y contraseña", Toast.LENGTH_SHORT).show();

                }
            }
        });




        botonOlvidarContrasenna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentOlvidarContrasenna = new Intent(v.getContext(), MainActivityOlvidarContrasenna.class);
                startActivity(intentOlvidarContrasenna);
            }
        });


    }

    private void goToMenu(Usuario usuario){
        startActivity(new Intent(MainActivity.this, MainActivityPrincipal.class));
    }

    @Override
    public void onTaskCompleted(String usuarioString) throws JSONException {


        if (usuarioString != null) {


            try {
                // Aquí parsea la respuesta JSON y crea un objeto Usuario
                JSONArray jsonArray = new JSONArray(usuarioString);

                if (jsonArray.length() > 0) {

                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    String idDni = jsonObject.getString("id_dni");
                    String nombre = jsonObject.getString("nombre");
                    String apellidos = jsonObject.getString("apellidos");
                    String email = jsonObject.getString("email");
                    String contraseña = jsonObject.getString("contraseña");
                    Usuario usuario = new Usuario(idDni, nombre, apellidos, email, contraseña);
                    goToMenu(usuario);
                }else{
                    tvError.setVisibility(View.VISIBLE);
                    new CountDownTimer(3000, 1000) {
                        public void onTick(long millisUntilFinished) {

                        }

                        public void onFinish() {

                            tvError.setVisibility(View.GONE);
                        }
                    }.start();

                }


            } catch (JSONException e) {
                Log.e("JSON PARSE ERROR", e.getMessage());
            }




        } else {
            // Usuario o contraseña incorrectos
            tvError.setText("Usuario o contraseña incorrecto");
        }

    }
}












