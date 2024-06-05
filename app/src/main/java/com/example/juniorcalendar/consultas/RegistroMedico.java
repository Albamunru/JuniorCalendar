package com.example.juniorcalendar.consultas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import android.util.Log;

import org.json.JSONException;

public class RegistroMedico extends AsyncTask<String, Void, String> {

    private WeakReference<Context> contextRef;
    private ProgressDialog progressDialog;
    private TaskCompleted listener;
    private SharedPreferences sharedPreferences;

    public RegistroMedico(Context context, TaskCompleted listener) {
        this.contextRef = new WeakReference<>(context);
        this.sharedPreferences = context.getSharedPreferences("UsuarioSesion", Context.MODE_PRIVATE);
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Context context = contextRef.get();
        if (context != null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Registrando...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        String fechaMedico = params[0];
        String horaMedico = params[1];
        String especialidad = params[2];
        String observaciones = params[3];
        String ubicacion = params[4];
        String usuario = sharedPreferences.getString("usuario", null);

        String insercion = "";
        HttpURLConnection urlConnection = null;
        try {

            String query =
                    "?fechamedico=" + URLEncoder.encode(fechaMedico, "UTF-8")
                            + "&horamedico=" + URLEncoder.encode(horaMedico, "UTF-8")
                            + "&especialidad=" + URLEncoder.encode(especialidad, "UTF-8")
                            + "&observaciones=" + URLEncoder.encode(observaciones, "UTF-8")
                            + "&ubicacion=" + URLEncoder.encode(ubicacion, "UTF-8")
                            + "&email=" + URLEncoder.encode(usuario, "UTF-8");

            URL url = new URL("http://10.0.2.2/InsertarMedico.php" + query);


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
            insercion = "error";
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
