package com.example.juniorcalendar.consultas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.juniorcalendar.Utiles.Utiles;
import com.example.juniorcalendar.vista.MainActivityAnnadirEducacion;

import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.juniorcalendar.Utiles.Utiles;

import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RegistroEducacion extends AsyncTask<String, Void, String> {

    private TaskCompleted listener;
    private WeakReference<Context> contextRef;
    private String username;
    private ProgressDialog progressDialog;


    public RegistroEducacion(Context context, TaskCompleted listener) {
        this.contextRef = new WeakReference<>(context);
        this.listener = listener;
        SharedPreferences sharedPreferences = context.getSharedPreferences("UsuarioSesion", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("usuario", null);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Context context = contextRef.get();
        if (context != null && !((Activity) context).isFinishing()) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Guardando datos...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        if (username == null) {
            return "Usuario no encontrado";
        }

        String fecha = params[0];
        String hora = params[1];
        String tipoEvento = params[2];
        String ubicacion = params[3];
        String descripcion = params[4];



        String insercion = "";
        HttpURLConnection urlConnection = null;
        try {
            String query = "?fecha=" + URLEncoder.encode(fecha, "UTF-8")
                    + "&hora=" + URLEncoder.encode(hora, "UTF-8")
                    + "&tipoevento=" + URLEncoder.encode(tipoEvento, "UTF-8")
                    + "&ubicacion=" + URLEncoder.encode(ubicacion, "UTF-8")
                    + "&descripcion=" + URLEncoder.encode(descripcion, "UTF-8")
                    + "&email=" + URLEncoder.encode(username, "UTF-8");
            URL url = new URL("http://10.0.2.2/InsertarEducacion.php" + query);

            urlConnection = (HttpURLConnection) url.openConnection();
            Log.i("url", urlConnection.toString());
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                insercion = "Exito";
            } else {
                insercion = "fallo";
            }

        } catch (Exception e) {
            Log.e("ERROR REQUEST", e.getMessage());
            insercion = "Error: " + e.getMessage();

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return insercion;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Context context = contextRef.get();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (context != null && !((Activity) context).isFinishing()) {
            try {
                listener.onTaskCompleted(result);
            } catch (JSONException e) {
                Toast.makeText(context, "Error al procesar la respuesta", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}



    

