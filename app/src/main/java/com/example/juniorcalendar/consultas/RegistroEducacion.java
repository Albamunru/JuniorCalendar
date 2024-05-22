package com.example.juniorcalendar.consultas;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RegistroEducacion extends AsyncTask<String, Void, String> {

    private TaskCompleted listener;


    public RegistroEducacion(TaskCompleted listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {

        String fecha = params[0];
        String hora = params[1];
        String tipoEvento = params[2];
        String ubicacion = params[3];
        String descripcion = params[4];
        String dni=params[5];

        String insercion = "";
        HttpURLConnection urlConnection = null;
        try {
            // Construir la URL con los parámetros de consulta
            String query =
                    "?id_niñoeducacion=" + URLEncoder.encode(dni, "UTF-8")
                            + "&fecha=" + URLEncoder.encode(fecha, "UTF-8")
                            + "&hora=" + URLEncoder.encode(hora, "UTF-8")
                            + "&tipoevento=" + URLEncoder.encode(tipoEvento, "UTF-8")
                            + "&ubicacion=" + URLEncoder.encode(ubicacion, "UTF-8")
                            + "&descripcion=" + URLEncoder.encode(descripcion, "UTF-8");
            URL url = new URL("http://10.0.2.2/InsertarEducacion.php" + query);

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

