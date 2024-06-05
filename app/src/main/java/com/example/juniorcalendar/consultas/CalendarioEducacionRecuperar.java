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

public class CalendarioEducacionRecuperar extends AsyncTask<String, Object, String> {

    private TaskCompleted listener;
    public CalendarioEducacionRecuperar(TaskCompleted listener) {
        this.listener = listener;
    }


    @Override
    protected String doInBackground(String... params) {
        String fecha = params[0];
        String linea = "";

        HttpURLConnection urlConnection = null;
        try {

            String query = "?fecha=" + URLEncoder.encode(fecha, "UTF-8") ;
            URL url = new URL("http://10.0.2.2/recuperarEducacion.php" + query);


            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream is = urlConnection.getInputStream();
            InputStreamReader isReader = new InputStreamReader(is, "UTF-8");
            BufferedReader reader = new BufferedReader(isReader);


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
