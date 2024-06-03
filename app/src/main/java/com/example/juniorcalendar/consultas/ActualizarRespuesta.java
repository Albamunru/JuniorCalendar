package com.example.juniorcalendar.consultas;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ActualizarRespuesta  extends AsyncTask<String, Object, String> {


    private TaskCompleted listener;


    private Context context;
    private String username;

    public ActualizarRespuesta(TaskCompleted listener) {
        this.context = context;
        this.listener = listener;

    }


    @Override
    protected String doInBackground(String... params) {
        String idVisita = params[0];
        String respuesta = params[1];

        String insercion = "";
        HttpURLConnection urlConnection = null;
        try {
            // Construir la URL con los parámetros de consulta
            String query =
                    "?id_visita=" + URLEncoder.encode(idVisita, "UTF-8")
                            + "&respuesta=" + URLEncoder.encode(respuesta, "UTF-8");
            URL url = new URL("http://10.0.2.2/ActualizarRespuestaHorario.php" + query);

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

        if (listener != null) {
            try {
                listener.onTaskCompleted(result);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }
    }

}
