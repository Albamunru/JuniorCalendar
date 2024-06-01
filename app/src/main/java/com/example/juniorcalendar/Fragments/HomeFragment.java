package com.example.juniorcalendar.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.juniorcalendar.R;
import com.example.juniorcalendar.consultas.DatosDeHoy;
import com.example.juniorcalendar.consultas.RecuperarContrasenna;
import com.example.juniorcalendar.consultas.TaskCompleted;
import com.example.juniorcalendar.modelo.Educacion;
import com.example.juniorcalendar.modelo.MyData;
import com.example.juniorcalendar.modelo.MyRecyclerViewAdapter;
import com.example.juniorcalendar.vista.MainActivityOlvidarContrasenna;
import com.example.juniorcalendar.vista.MainActivityRegistroFamilia;
import com.example.juniorcalendar.vista.MainActivityRegistroNinno;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class HomeFragment extends Fragment implements TaskCompleted {
    private TextView saludo;
    MyRecyclerViewAdapter adapter;
    private DatosDeHoy datosDeHoy;
    private List<MyData> listado;
    private TextView textoDevuelto;


private ImageButton botonRegistroNinno,botonRegistroFamilia;
  public HomeFragment(){

  }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UsuarioSesion", MODE_PRIVATE);
        String username = sharedPreferences.getString("usuario", null);
        saludo=view.findViewById(R.id.textViewSaludoInicio);
        saludo.setText(username);
        botonRegistroFamilia=view.findViewById(R.id.imageButtonAltaFamilia);
        botonRegistroNinno=view.findViewById(R.id.imageButtonAltaNiño);
        datosDeHoy = new DatosDeHoy(getContext(),this);
        datosDeHoy.execute();
        textoDevuelto=view.findViewById(R.id.editTextdevuelto);
       // RecyclerView recyclerView = view.findViewById(R.id.recicle);
        //recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

       // adapter = new MyRecyclerViewAdapter(this, nombreAnimales);
       // adapter.setClickListener(this);
        //recyclerView.setAdapter(adapter);




  botonRegistroNinno.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          Intent intentRegistro = new Intent(view.getContext(), MainActivityRegistroNinno.class);
          startActivity(intentRegistro);
      }
  });

botonRegistroFamilia.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intentRegistroFamilia = new Intent(view.getContext(), MainActivityRegistroFamilia.class);
        startActivity(intentRegistroFamilia);
    }
});


  }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);





    }

    @Override
    public void onTaskCompleted(String datoEncontrado) throws JSONException {


        if (datoEncontrado != null) {

        JSONObject jsonObject=  new JSONObject(datoEncontrado);
        JSONArray educacion= jsonObject.getJSONArray("educacion");
        JSONArray  ocio= jsonObject.getJSONArray ("ocio");
        JSONArray  consultamedica= jsonObject.getJSONArray("consultamedica");
        JSONArray  solicitudvisita= jsonObject.getJSONArray("solicitudvisita");
        StringBuilder stringBuilder = new StringBuilder();

            LocalDate fechaDeHoy = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                fechaDeHoy = LocalDate.now();
            }
            DateTimeFormatter formatter = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            }

            // Formatear la fecha
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String fechaFormateada = fechaDeHoy.format(formatter);
                stringBuilder.append("").append(fechaFormateada).append("\n"+"\n");
            }
            extracted(educacion, ocio, consultamedica, solicitudvisita, stringBuilder);


            String formattedText = stringBuilder.toString();
            textoDevuelto.setText(formattedText);

        }
    }

    private static void extracted(JSONArray educacion, JSONArray ocio, JSONArray consultamedica, JSONArray solicitudvisita, StringBuilder stringBuilder) throws JSONException {
        stringBuilder.append("Actividades Colegio: ").append("").append("\n"+"\n");
        for (int i = 0; i< educacion.length(); i++) extraerDatosEducacion(educacion, stringBuilder, i);

        stringBuilder.append("Actividades de Ocio: ").append("").append("\n"+"\n");
        for (int i = 0; i< ocio.length(); i++) extraerDatosOcio(ocio, stringBuilder, i);

        stringBuilder.append("Médico: ").append("").append("\n"+"\n");
        for (int i = 0; i< consultamedica.length(); i++) extraerDatosMedico(consultamedica, stringBuilder, i);

        stringBuilder.append("Solicitud de visita: ").append("").append("\n"+"\n");
        for (int i = 0; i< solicitudvisita.length(); i++) extraerVisita(consultamedica, stringBuilder, i);
    }


    private static void extraerVisita(JSONArray visita, StringBuilder stringBuilder, int i) throws JSONException {
        JSONObject json= (JSONObject) visita.get(i);
        String nota = json.getString("nota");



        stringBuilder.append("").append(nota).append("\n");

        stringBuilder.append("\n"); // Agrega una línea en blanco entre cada registro

        Log.i("debug", "Actividad: " + nota);

    }








    private static void extraerDatosMedico(JSONArray consultaMedica, StringBuilder stringBuilder, int i) throws JSONException {
        JSONObject json= (JSONObject) consultaMedica.get(i);
        String especialidad = json.getString("especialidad");
        String ubicacion = json.getString("ubicacion");
        String observacion = json.getString("observaciones");


        stringBuilder.append("").append(especialidad).append("\n");
        stringBuilder.append("").append(ubicacion).append("\n");
        stringBuilder.append("").append(observacion).append("\n");
        stringBuilder.append("\n"); // Agrega una línea en blanco entre cada registro

        Log.i("debug", "Actividad: " + especialidad);
        Log.i("debug", "Ubicación: " + ubicacion);
        Log.i("debug", "Descripción: " + observacion);
    }


    private static void extraerDatosOcio(JSONArray ocio, StringBuilder stringBuilder, int i) throws JSONException {
        JSONObject json= (JSONObject) ocio.get(i);
        String nombreActividad = json.getString("nombre_actividad");
        String ubicacion = json.getString("ubicacion");
        String descripcion = json.getString("descripcion");


        stringBuilder.append("").append(nombreActividad).append("\n");
        stringBuilder.append("").append(ubicacion).append("\n");
        stringBuilder.append("").append(descripcion).append("\n");
        stringBuilder.append("\n"); // Agrega una línea en blanco entre cada registro

        Log.i("debug", "Actividad: " + nombreActividad);
        Log.i("debug", "Ubicación: " + ubicacion);
        Log.i("debug", "Descripción: " + descripcion);
    }











    private static void extraerDatosEducacion(JSONArray educacion, StringBuilder stringBuilder, int i) throws JSONException {
        JSONObject json= (JSONObject) educacion.get(i);
        String tipoEvento = json.getString("tipoevento");
        String ubicacion = json.getString("ubicacion");
        String descripcion = json.getString("descripcion");


        stringBuilder.append("").append(tipoEvento).append("\n");
        stringBuilder.append("").append(ubicacion).append("\n");
        stringBuilder.append("").append(descripcion).append("\n");
        stringBuilder.append("\n"); // Agrega una línea en blanco entre cada registro

        Log.i("debug", "Tipo de Evento: " + tipoEvento);
        Log.i("debug", "Ubicación: " + ubicacion);
        Log.i("debug", "Descripción: " + descripcion);
    }

}