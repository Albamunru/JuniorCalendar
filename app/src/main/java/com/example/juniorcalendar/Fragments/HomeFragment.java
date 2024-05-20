package com.example.juniorcalendar.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.List;


public class HomeFragment extends Fragment implements TaskCompleted {
    private TextView saludo;
    MyRecyclerViewAdapter adapter;
    private DatosDeHoy datosDeHoy;
    private List<MyData> listado;


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
        datosDeHoy = new DatosDeHoy(HomeFragment.this);
        datosDeHoy.execute();
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



for (int i=0; i<educacion.length();i++){
    JSONObject json= (JSONObject) educacion.get(i);
    json.get("tipoevento");
    Log.i("debug", json.get("tipoevento").toString());
}


        }
    }

}