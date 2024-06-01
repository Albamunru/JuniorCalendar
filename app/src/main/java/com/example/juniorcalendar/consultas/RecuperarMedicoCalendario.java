package com.example.juniorcalendar.consultas;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.juniorcalendar.Utiles.Utiles;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RecuperarMedicoCalendario extends AsyncTask<String, Void, String> {

    private TaskCompleted listener;
    private Context context;
    private Exception exception;
    private Utiles utiles= new Utiles();
    private String username;

    public RecuperarMedicoCalendario(Context context, TaskCompleted listener) {
        this.context = context;
        this.listener = listener;
        SharedPreferences sharedPreferences = context.getSharedPreferences("UsuarioSesion", Context.MODE_PRIVATE);
        this.username = sharedPreferences.getString("usuario", null);
    }

    @Override
    protected String doInBackground(String... params) {
        String fecha = params[0];
        String email=username;

        String fechaConvertida= utiles.formatearFecha(fecha);


        String linea = "";
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            // Construir la URL con los parámetros de consulta
            String query = "?fechamedico=" + URLEncoder.encode(fechaConvertida, "UTF-8")
                    + "&email=" + URLEncoder.encode(email, "UTF-8");
            URL url = new URL("http://10.0.2.2/RecuperarMedicoCalendario.php" + query);

            // Realizar la conexión HTTP y obtener la respuesta
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000); // Timeout de conexión en milisegundos
            urlConnection.setReadTimeout(5000); // Timeout de lectura en milisegundos

            InputStream is = urlConnection.getInputStream();
            InputStreamReader isReader = new InputStreamReader(is, "UTF-8");
            reader = new BufferedReader(isReader);

            // Leer la respuesta del servidor como un String
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            linea = stringBuilder.toString();

            Log.i("debug", linea);
        } catch (Exception e) {
            Log.e("ERROR REQUEST", e.getMessage(), e);
            exception = e;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("ERROR", "Error closing BufferedReader", e);
                }
            }
        }

        return linea;
    }

    @Override
    protected void onPostExecute(String result) {
        if (listener != null) {
            try {
                if (exception != null) {
                    listener.onTaskCompleted("Error: " + exception.getMessage());
                } else {
                    listener.onTaskCompleted(result);
                }
            } catch (JSONException e) {
                Log.e("ERROR", "Error processing JSON", e);
                throw new RuntimeException(e);
            }
        }
    }
}



