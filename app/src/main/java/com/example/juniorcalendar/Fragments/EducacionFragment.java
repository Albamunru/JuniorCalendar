package com.example.juniorcalendar.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juniorcalendar.R;
import com.example.juniorcalendar.consultas.EliminarEducacion;
import com.example.juniorcalendar.consultas.MyRecyclerViewAdapter;
import com.example.juniorcalendar.consultas.RecuperarEducacionCalendario;
import com.example.juniorcalendar.consultas.TaskCompleted;
import com.example.juniorcalendar.modelo.Educacion;
import com.example.juniorcalendar.vista.MainActivityAnnadirEducacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class EducacionFragment extends Fragment implements TaskCompleted, MyRecyclerViewAdapter.ItemClickListener {

    private ImageButton annadirEducacion, calendarioEducacion;
    private EditText fecha;

    private RecuperarEducacionCalendario recuperarEducacionCalendario;

    static MyRecyclerViewAdapter adapter;
    private static List<Educacion> listadoEducacion = new ArrayList<>();
    private EliminarEducacion eliminarEducacion;

    public EducacionFragment() {
        // Constructor público requerido
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        annadirEducacion = view.findViewById(R.id.imageButtonAddEducacion);
        calendarioEducacion = view.findViewById(R.id.imageButtonCalendario);
        fecha = view.findViewById(R.id.editTextfecha);


        RecyclerView recyclerView = view.findViewById(R.id.recicle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MyRecyclerViewAdapter(getContext(), listadoEducacion);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        calendarioEducacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veeCalendario(v);
            }
        });

        annadirEducacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegistroEducacion = new Intent(view.getContext(), MainActivityAnnadirEducacion.class);
                startActivity(intentRegistroEducacion);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el diseño para este fragmento
        return inflater.inflate(R.layout.fragment_educacion, container, false);
    }

    public void veeCalendario(View view) {
        showDatePickerDialog();
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = day + "-" + (month + 1) + "-" + year;
                fecha.setText(selectedDate);

                // Ejecutar la tarea después de seleccionar la fecha
                String fechaSeleccionada = fecha.getText().toString();
                if (!fechaSeleccionada.isEmpty()) {
                    recuperarEducacionCalendario = new RecuperarEducacionCalendario(getContext(), EducacionFragment.this);
                    recuperarEducacionCalendario.execute(fechaSeleccionada);
                } else {
                    Toast.makeText(getContext(), "Por favor, seleccione una fecha", Toast.LENGTH_SHORT).show();
                }
            }
        });

        newFragment.show(requireActivity().getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onTaskCompleted(String datoEncontrado) throws JSONException {
        if (datoEncontrado != null) {
            try {
                Object json = new JSONTokener(datoEncontrado).nextValue();
                if (json instanceof JSONObject) {
                    JSONObject jsonObject = (JSONObject) json;
                    JSONArray educacion = jsonObject.getJSONArray("educacion");
                    extracted(educacion);
                } else if (json instanceof JSONArray) {
                    JSONArray educacion = (JSONArray) json;
                    extracted(educacion);
                } else {
                    Toast.makeText(getContext(), "Formato de respuesta no esperado", Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Error procesando datos:", Toast.LENGTH_SHORT).show();

            }
        } else {
            Toast.makeText(getContext(), "Respuesta vacía del servidor:", Toast.LENGTH_SHORT).show();

        }
    }

    private void extracted(JSONArray educacion) throws JSONException {
        listadoEducacion.clear();
        for (int i = 0; i < educacion.length(); i++) {
            JSONObject json = educacion.getJSONObject(i);
            String idNiñoEducacion = json.getString("id_niñoeducacion");
            String fecha = json.getString("fecha");
            LocalDate fechaLo = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                fechaLo = LocalDate.parse(fecha);
            }
            String hora = json.getString("hora");
            LocalTime horaLo = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                horaLo = LocalTime.parse(hora);
            }
            String tipoEvento = json.getString("tipoevento");
            String ubicacion = json.getString("ubicacion");
            String descripcion = json.getString("descripcion");
            int id_evento=json.getInt("id_evento");
            Educacion educacionObj = new Educacion(id_evento,idNiñoEducacion, fechaLo, horaLo, tipoEvento, ubicacion, descripcion);



            listadoEducacion.add(educacionObj);
        }
        adapter.notifyDataSetChanged(); // Notificar cambios al adapter después de añadir todos los datos
    }




    @Override
    public void onItemClick(View view, int position) {

        Educacion item = adapter.getItem(position);
        //Toast.makeText(getContext(), "Elemento clickeado: " + item, Toast.LENGTH_SHORT).show();
        eliminarEducacion=new EliminarEducacion(this);

        AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
        builder.setMessage("¿Desea eliminarlo?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                eliminarEducacion.execute(String.valueOf(item.getIdEvento()));
                listadoEducacion.remove(position);
                adapter.notifyItemRemoved(position);

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
dialog.dismiss();
            }
        });

builder.create().show();






    }
}



