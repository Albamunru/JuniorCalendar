package com.example.juniorcalendar.consultas;

import android.os.AsyncTask;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RegistroNinno extends AsyncTask<String, Void, String> { private TaskCompleted listener;

    public RegistroNinno(TaskCompleted listener) {
        this.listener = listener;
    }
    @Override
    protected String doInBackground(String... params) {

        String nombre = params[0];
        String apellidos = params[1];
        String fechaNacimiento = params[2];
        String dni = params[3];
        String insercion = "";
        HttpURLConnection urlConnection = null;
        try {

            String query =
                    "?id_dniniño=" + URLEncoder.encode(dni, "UTF-8")
                            + "&nombre_niño=" + URLEncoder.encode(nombre, "UTF-8")
                            + "&apellidos_niños=" + URLEncoder.encode(apellidos, "UTF-8")

                            + "&fechanacimiento_niño=" + URLEncoder.encode(fechaNacimiento, "UTF-8");
            URL url = new URL("http://10.0.2.2/InsertarNinno.php" + query);



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


        return insercion;



    }
}
