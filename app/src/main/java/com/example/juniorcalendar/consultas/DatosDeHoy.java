package com.example.juniorcalendar.consultas;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class DatosDeHoy  extends AsyncTask<String, Object, String> {


    private TaskCompleted listener;


    private Context context;
    private String username;

    public DatosDeHoy(Context context, TaskCompleted listener) {
        this.context = context;
        this.listener = listener;
        SharedPreferences sharedPreferences = context.getSharedPreferences("UsuarioSesion", Context.MODE_PRIVATE);
        this.username = sharedPreferences.getString("usuario", null);
    }



    @Override
    protected String doInBackground(String... strings) {
        String linea = "";
        String email= username;

        HttpURLConnection urlConnection = null;
        try {
            // Construir la URL con los parámetros de consulta
           String query = "?email=" + URLEncoder.encode(email, "UTF-8") ;
            URL url = new URL("http://10.0.2.2/ActividadesDiarias.php"+query );

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
