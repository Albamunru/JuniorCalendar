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
import android.widget.Toast;

import com.example.juniorcalendar.R;
import com.example.juniorcalendar.consultas.EliminarMedico;
import com.example.juniorcalendar.consultas.EliminarOcio;
import com.example.juniorcalendar.consultas.MyRecyclerViewAdapterMedico;
import com.example.juniorcalendar.consultas.MyRecyclerViewAdapterOcio;
import com.example.juniorcalendar.consultas.RecuperarMedicoCalendario;
import com.example.juniorcalendar.consultas.RecuperarOcioCalendario;
import com.example.juniorcalendar.consultas.TaskCompleted;
import com.example.juniorcalendar.modelo.ConsultaMedica;
import com.example.juniorcalendar.modelo.Ocio;
import com.example.juniorcalendar.vista.MainActivityAnnadirOcio;
import com.example.juniorcalendar.vista.MainActivityAnnadirSalud;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


public class OcioFragment extends Fragment implements TaskCompleted, MyRecyclerViewAdapterOcio.ItemClickListener {
    private ImageButton annadirOcio, calendarioOcio;
    private EditText fecha;

    private RecuperarOcioCalendario recuperarOcioCalendario;

    static MyRecyclerViewAdapterOcio adapter;
    private static List<Ocio> listadoOcio = new ArrayList<>();
    private EliminarOcio eliminarOcio;

    public OcioFragment() {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        annadirOcio = view.findViewById(R.id.imageButtonAddOcio);
        calendarioOcio = view.findViewById(R.id.imageButtonCalendarioOcio);
        fecha = view.findViewById(R.id.editTextFechaOcio);

        RecyclerView recyclerView = view.findViewById(R.id.recicleOcio);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MyRecyclerViewAdapterOcio(getContext(), listadoOcio);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        calendarioOcio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veeCalendario(v);
            }
        });

        annadirOcio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegistroOcio = new Intent(view.getContext(), MainActivityAnnadirOcio.class);
                startActivity(intentRegistroOcio);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ocio, container, false);
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

                String fechaSeleccionada = fecha.getText().toString();
                if (!fechaSeleccionada.isEmpty()) {
                    recuperarOcioCalendario = new RecuperarOcioCalendario(getContext(), OcioFragment.this);
                    recuperarOcioCalendario.execute(fechaSeleccionada);
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
                Log.d("OcioFragment", "JSON recibido: " + datoEncontrado);

                Object json = new JSONTokener(datoEncontrado).nextValue();
                if (json instanceof JSONObject) {
                    JSONObject jsonObject = (JSONObject) json;
                    JSONArray ocio = jsonObject.getJSONArray("ocio");
                    extracted(ocio);
                } else if (json instanceof JSONArray) {
                    JSONArray ocio = (JSONArray) json;
                    extracted(ocio);
                } else {
                    Toast.makeText(getContext(), "Formato de respuesta no esperado", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Log.e("OcioFragment", "Error procesando datos: " + e.getMessage());
                e.printStackTrace();
                Toast.makeText(getContext(), "Error procesando datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Respuesta vacía del servidor", Toast.LENGTH_SHORT).show();
        }
    }



// Dentro de la clase OcioFragment

    private void extracted(JSONArray ocio) throws JSONException {
        listadoOcio.clear();
        DateTimeFormatter dateFormatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        }
        DateTimeFormatter timeFormatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        }

        for (int i = 0; i < ocio.length(); i++) {
            JSONObject json = ocio.getJSONObject(i);
            String idNinoActividad = json.getString("id_niñoactividad");
            String fecha = json.getString("fecha_actividad");
            String hora = json.getString("hora");
            LocalDate fechaLo = null;
            LocalTime horaLo = null;

            // Asegúrate de que la versión de Android soporta java.time (API Level 26 y superior)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                try {
                    fechaLo = LocalDate.parse(fecha, dateFormatter);
                } catch (DateTimeParseException e) {
                    Log.e("OcioFragment", "Error parsing fecha: " + e.getMessage());
                    e.printStackTrace();
                }

                try {
                    horaLo = LocalTime.parse(hora, timeFormatter);
                } catch (DateTimeParseException e) {
                    Log.e("OcioFragment", "Error parsing hora: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            String nombreActividad = json.getString("nombre_actividad");
            String descripcion = json.getString("descripcion");
            String ubicacion = json.getString("ubicacion");
            int idActividad = json.getInt("id_actividad");

            Ocio ocioObj = null;
            if (fechaLo != null && horaLo != null) {
                ocioObj = new Ocio(idActividad, idNinoActividad, nombreActividad, descripcion, fechaLo, horaLo, ubicacion);
            } else {
                Log.e("OcioFragment", "Fecha o Hora no válida, no se puede crear el objeto Ocio");
            }

            if (ocioObj != null) {
                listadoOcio.add(ocioObj);
            }
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(View view, int position) {
        Ocio item = adapter.getItem(position);
        eliminarOcio = new EliminarOcio(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("¿Desea eliminarlo?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eliminarOcio.execute(String.valueOf(item.getIdActividad()));
                listadoOcio.remove(position);
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

