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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.juniorcalendar.R;
import com.example.juniorcalendar.consultas.EliminarEducacion;
import com.example.juniorcalendar.consultas.EliminarMedico;
import com.example.juniorcalendar.consultas.MyRecyclerViewAdapter;
import com.example.juniorcalendar.consultas.MyRecyclerViewAdapterMedico;
import com.example.juniorcalendar.consultas.RecuperarEducacionCalendario;
import com.example.juniorcalendar.consultas.RecuperarMedicoCalendario;
import com.example.juniorcalendar.consultas.TaskCompleted;
import com.example.juniorcalendar.modelo.ConsultaMedica;
import com.example.juniorcalendar.modelo.Educacion;
import com.example.juniorcalendar.vista.MainActivityAnnadirEducacion;
import com.example.juniorcalendar.vista.MainActivityAnnadirSalud;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class SaludFragment extends Fragment implements TaskCompleted, MyRecyclerViewAdapterMedico.ItemClickListener {

    private ImageButton annadirSalud, calendarioSalud;
    private EditText fecha;

    private RecuperarMedicoCalendario recuperarMedicoCalendario;

    static MyRecyclerViewAdapterMedico adapter;
    private static List<ConsultaMedica> listadoConsultaMedica = new ArrayList<>();
    private EliminarMedico eliminarConsulta;



    public SaludFragment() {
    }


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    annadirSalud=view.findViewById(R.id.imageButtonAddSalud);
    calendarioSalud=view.findViewById(R.id.imageButtonCalendarioSalud);
    fecha=view.findViewById(R.id.editTextFechaMedico);

        RecyclerView recyclerView = view.findViewById(R.id.recicleSalud);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MyRecyclerViewAdapterMedico(getContext(), listadoConsultaMedica);
        adapter.setClickListener((MyRecyclerViewAdapterMedico.ItemClickListener) this);
        recyclerView.setAdapter(adapter);


        calendarioSalud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veeCalendario(v);
            }
        });

        annadirSalud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegistroSalud = new Intent(view.getContext(), MainActivityAnnadirSalud.class);
                startActivity(intentRegistroSalud);
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

        return inflater.inflate(R.layout.fragment_salud, container, false);


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
                    recuperarMedicoCalendario = new RecuperarMedicoCalendario(getContext(), SaludFragment.this);
                    recuperarMedicoCalendario.execute(fechaSeleccionada);
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
                    JSONArray salud = jsonObject.getJSONArray("consultamedica");
                    extracted(salud);
                } else if (json instanceof JSONArray) {
                    JSONArray salud = (JSONArray) json;
                    extracted(salud);
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

    private void extracted(JSONArray salud) throws JSONException {
        listadoConsultaMedica.clear();
        for (int i = 0; i < salud.length(); i++) {
            JSONObject json = salud.getJSONObject(i);
            String idNinoMedico = json.getString("id_niñomedico");
            String fecha = json.getString("fechamedico");
            String hora = json.getString("horamedico");
            LocalDate fechaLo = null;
            LocalTime horaLo = null;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                fechaLo = LocalDate.parse(fecha);
                horaLo = LocalTime.parse(hora);
            }

            String especialidad = json.getString("especialidad");
            String observaciones = json.getString("observaciones");
            String ubicacion = json.getString("ubicacion");
            int idConsultaMedica = json.getInt("id_consultamedica");

            ConsultaMedica consultaMedicaObj = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                consultaMedicaObj = new ConsultaMedica(idConsultaMedica, idNinoMedico, fechaLo, horaLo, especialidad, observaciones, ubicacion);
            }


            if (consultaMedicaObj != null) {
                listadoConsultaMedica.add(consultaMedicaObj);
            }
        }
        adapter.notifyDataSetChanged();
    }






    @Override
    public void onItemClick(View view, int position) {

        ConsultaMedica item = adapter.getItem(position);
        //Toast.makeText(getContext(), "Elemento clickeado: " + item, Toast.LENGTH_SHORT).show();
        eliminarConsulta=new EliminarMedico(this);

        AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
        builder.setMessage("¿Desea eliminarlo?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                eliminarConsulta.execute(String.valueOf(item.getIdConsultaMedica()
                ));
                listadoConsultaMedica.remove(position);
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

