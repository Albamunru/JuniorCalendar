package com.example.juniorcalendar.consultas;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class EliminarOcio extends AsyncTask<String, Object, String> {

    private TaskCompleted listener;

    public EliminarOcio( TaskCompleted listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        String linea = "";
        String evento=params[0];


        HttpURLConnection urlConnection = null;
        try {

            String query = "?id_actividad=" + URLEncoder.encode(evento, "UTF-8") ;
            URL url = new URL("http://10.0.2.2/EliminarOcio.php"+query );

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


}
