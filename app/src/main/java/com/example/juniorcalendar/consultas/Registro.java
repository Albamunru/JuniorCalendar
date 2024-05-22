package com.example.juniorcalendar.consultas;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class  Registro extends AsyncTask<String, Void, String> {

    private TaskCompleted listener;

    public Registro(TaskCompleted listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {

        String nombre = params[0];
        String apellidos = params[1];
        String dni = params[2];
        String email = params[3];
        String contrasenna = params[4];
        String insercion = "";
        HttpURLConnection urlConnection = null;
        try {
            // Construir la URL con los parámetros de consulta
            String query =
                    "?id_dni=" + URLEncoder.encode(dni, "UTF-8")
                            + "&nombre=" + URLEncoder.encode(nombre, "UTF-8")
                            + "&apellidos=" + URLEncoder.encode(apellidos, "UTF-8")

                            + "&email=" + URLEncoder.encode(email, "UTF-8")
                            + "&contraseña=" + URLEncoder.encode(contrasenna, "UTF-8");
            URL url = new URL("http://10.0.2.2/InsertarUsuario.php" + query);

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

    @Override
    protected void onPostExecute(String result) {
        if (result.equals("Exito")) {
            // La inserción fue exitosa
            try {
                listener.onTaskCompleted(result);  // Informar éxito al listener
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            // La inserción falló
            try {
                listener.onTaskCompleted(result);  // Informar fallo al listener
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

}
