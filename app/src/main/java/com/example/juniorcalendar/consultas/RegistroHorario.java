package com.example.juniorcalendar.consultas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.example.juniorcalendar.Utiles.Utiles;

import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;

public class RegistroHorario extends AsyncTask<String, Void, String> {

    private WeakReference<Context> contextRef;
    private ProgressDialog progressDialog;
    private TaskCompleted listener;
    private SharedPreferences sharedPreferences;
    private Utiles utiles=new Utiles();

    public RegistroHorario(Context context, TaskCompleted listener) {
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
        String dni_destinatariovisita = params[0];
        String periodoCambio = params[1];
        String nota= params[2];
        String usuario = sharedPreferences.getString("usuario", null);
        LocalDate fecha= null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             fecha= LocalDate.now();
        }
        String fechaFormato= String.valueOf(fecha);


        String insercion = "";
        HttpURLConnection urlConnection = null;
        try {

            String query =
                    "?id_destinatariovisita=" + URLEncoder.encode(dni_destinatariovisita, "UTF-8")
                            + "&periodo_cambio=" + URLEncoder.encode(periodoCambio, "UTF-8")
                            + "&nota=" + URLEncoder.encode(nota, "UTF-8")
                            + "&fechasolicitud=" + URLEncoder.encode(fechaFormato, "UTF-8")
                            + "&email=" + URLEncoder.encode(usuario, "UTF-8")

                    ;

            URL url = new URL("http://10.0.2.2/InsertarHorario.php" + query);


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
