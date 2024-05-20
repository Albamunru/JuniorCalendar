package com.example.juniorcalendar.consultas;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RecuperarContrasenna extends AsyncTask <String, Object, String>{


    private TaskCompleted listener;

    public RecuperarContrasenna(TaskCompleted listener) {
        this.listener = listener;
    }



    @Override
    protected String doInBackground(String... params) {

        String email = params[0];
        String linea = "";

        HttpURLConnection urlConnection = null;
        try {
            // Construir la URL con los parámetros de consulta
            String query = "?email=" + URLEncoder.encode(email, "UTF-8") ;
            URL url = new URL("http://10.0.2.2/BuscarContrasenna.php" + query);

            // Realizar la conexión HTTP y obtener la respuesta
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream is = urlConnection.getInputStream();
            InputStreamReader isReader = new InputStreamReader(is, "UTF-8");
            BufferedReader reader = new BufferedReader(isReader);

            // Leer la respuesta del servidor como un String
            linea = reader.readLine();

            Log.i("debug",linea);
        } catch (Exception e) {
            Log.e("ERROR REQUEST", e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }



        return linea;
    }


    @Override
    protected void onPostExecute(String result) {

        if (listener != null) {
            try {
                listener.onTaskCompleted(result);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }
    }


}
