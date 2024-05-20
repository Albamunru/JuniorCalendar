package com.example.juniorcalendar.consultas;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import com.example.juniorcalendar.modelo.Usuario;
import com.example.juniorcalendar.vista.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class Login extends AsyncTask<String, Object, String> {

    private TaskCompleted listener;

    public Login(TaskCompleted listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        String email = params[0];
        String contraseña = params[1];
        String linea = "";

        HttpURLConnection urlConnection = null;
        try {
            // Construir la URL con los parámetros de consulta
            String query = "?email=" + URLEncoder.encode(email, "UTF-8") + "&contraseña=" + URLEncoder.encode(contraseña, "UTF-8");
            URL url = new URL("http://10.0.2.2/BuscarUsuario.php" + query);

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

        // Devolver la respuesta del servidor como un String
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
