package com.example.juniorcalendar.consultas;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RegistroMedico extends AsyncTask<String, Void, String> {

    private TaskCompleted listener;
    private SharedPreferences sharedPreferences;

    public RegistroMedico(Context context, TaskCompleted listener) {
        this.sharedPreferences = context.getSharedPreferences("UsuarioSesion", Context.MODE_PRIVATE);
        this.listener = listener;
    }
    @Override
    protected String doInBackground(String... params) {
        String fechaMedico = params[0];
        String horamedico = params[1];
        String especialidad = params[3];
        String observaciones = params[4];
        String ubicacion = params[4];
        String usuario = sharedPreferences.getString("usuario", null);

        String insercion = "";
        HttpURLConnection urlConnection = null;
        try {
            // Construir la URL con los parámetros de consulta
            String query =
                    "?fechamedico=" + URLEncoder.encode(fechaMedico, "UTF-8")
                            + "&horamedico=" + URLEncoder.encode(horamedico, "UTF-8")
                            + "&especialidad=" + URLEncoder.encode(especialidad, "UTF-8")
                            + "&observaciones=" + URLEncoder.encode(observaciones, "UTF-8")
                            + "&ubicacion=" + URLEncoder.encode(ubicacion, "UTF-8")
                            + "&email=" + URLEncoder.encode(usuario, "UTF-8");

            URL url = new URL("http://10.0.2.2/InsertarMedico.php" + query);

            // Realizar la conexión HTTP y obtener la respuesta

            urlConnection = (HttpURLConnection) url.openConnection();
            Log.i("url",urlConnection.toString());
            int responseCode = urlConnection.getResponseCode();
            if(responseCode== HttpURLConnection.HTTP_OK){
                insercion = "Exito";
            }else{
                insercion = "fallo";
            }





        } catch (Exception e) {
            Log.e("ERROR REQUEST", e.getMessage());

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        // Devolver la respuesta del servidor como un String
        return insercion;



    }
    }

