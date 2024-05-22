package com.example.juniorcalendar.consultas;

import android.os.AsyncTask;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RegistroFamilia extends AsyncTask<String, Void, String> {



    private TaskCompleted listener;

    public RegistroFamilia(TaskCompleted listener) {
        this.listener = listener;
    }


    @Override
    protected String doInBackground(String... params) {

        String dniUno = params[0];
        String dniDos = params[1];
        String dniNinno = params[2];

        String insercion = "";
        HttpURLConnection urlConnection = null;
        try {
            // Construir la URL con los parámetros de consulta
            String query =
                    "?id_usuariouno=" + URLEncoder.encode(dniUno, "UTF-8")
                            + "&id_usuariodos=" + URLEncoder.encode(dniDos, "UTF-8")
                            + "&id_dniniño=" + URLEncoder.encode(dniNinno, "UTF-8");
            URL url = new URL("http://10.0.2.2/InsertarFamilia.php" + query);

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
