package com.example.juniorcalendar.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.juniorcalendar.R;
import com.example.juniorcalendar.consultas.ActualizarRespuesta;
import com.example.juniorcalendar.consultas.EliminarHorario;
import com.example.juniorcalendar.consultas.EliminarOcio;
import com.example.juniorcalendar.consultas.MyRecyclerViewAdapterHorario;
import com.example.juniorcalendar.consultas.MyRecyclerViewAdapterOcio;
import com.example.juniorcalendar.consultas.RecuperarHorarioCalendario;
import com.example.juniorcalendar.consultas.RecuperarOcioCalendario;
import com.example.juniorcalendar.consultas.TaskCompleted;
import com.example.juniorcalendar.modelo.Ocio;
import com.example.juniorcalendar.modelo.SolicitudVisita;
import com.example.juniorcalendar.vista.MainActivityAnnadirHorario;
import com.example.juniorcalendar.vista.MainActivityAnnadirOcio;

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


public class HorarioFragment extends Fragment implements TaskCompleted, MyRecyclerViewAdapterHorario.ItemClickListener {


    private ImageButton annadirHorario, calendarioHorario;
    private EditText fecha;

    private RecuperarHorarioCalendario recuperarHorarioCalendario;
    private ActualizarRespuesta actualizarRespuesta;

    static MyRecyclerViewAdapterHorario adapter;
    private static List<SolicitudVisita> listadoHorario = new ArrayList<>();
    private EliminarHorario eliminarHorario;


    public HorarioFragment() {
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        annadirHorario = view.findViewById(R.id.imageButtonAddHorario);
        calendarioHorario = view.findViewById(R.id.imageButtonCalendarioHorario);
        fecha = view.findViewById(R.id.editTextFechaHorario);

        RecyclerView recyclerView = view.findViewById(R.id.recicleHorario);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MyRecyclerViewAdapterHorario(getContext(), listadoHorario);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        calendarioHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                veeCalendario(v);
            }
        });

        annadirHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegistroHorario = new Intent(view.getContext(), MainActivityAnnadirHorario.class);
                startActivity(intentRegistroHorario);
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
        return inflater.inflate(R.layout.fragment_horario, container, false);
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
                    recuperarHorarioCalendario = new RecuperarHorarioCalendario(getContext(), HorarioFragment.this);
                    recuperarHorarioCalendario.execute(fechaSeleccionada);
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
                Log.d("HorarioFragment", "JSON recibido: " + datoEncontrado);

                Object json = new JSONTokener(datoEncontrado).nextValue();
                if (json instanceof JSONObject) {
                    JSONObject jsonObject = (JSONObject) json;
                    JSONArray horario = jsonObject.getJSONArray("solicitud_visita");
                    extracted(horario);
                } else if (json instanceof JSONArray) {
                    JSONArray horario = (JSONArray) json;
                    extracted(horario);
                } else {
                    //Toast.makeText(getContext(), "Formato de respuesta no esperado", Toast.LENGTH_SHORT).show();
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



    private void extracted(JSONArray horario) throws JSONException {
        listadoHorario.clear();
        DateTimeFormatter dateFormatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        }
        DateTimeFormatter timeFormatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        }

        for (int i = 0; i < horario.length(); i++) {
            JSONObject json = horario.getJSONObject(i);
            LocalDate fechaLo = null;
            int idVisita = json.getInt("id_visita");
            String idNinoVisita = json.getString("id_niñovisita");
            String idSolicitanteVisita = json.getString("id_solicitantevisita");
            String idDestinatarioVisita = json.getString("id_destinatariovisita");
            String periodoCambio = json.getString("periodo_cambio");
            String fecha = json.getString("fechasolicitud");
            String respuesta = json.getString("respuesta");
            String nota=json.getString("nota");




            // Asegúrate de que la versión de Android soporta java.time (API Level 26 y superior)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
                    fechaLo = LocalDate.parse(fecha, dateFormatter);
                } catch (DateTimeParseException e) {
                    Log.e("OcioFragment", "Error parsing fecha: " + e.getMessage());
                    e.printStackTrace();
                }

            }


            SolicitudVisita ocioObj = null;
            if (fechaLo != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ocioObj = new SolicitudVisita(idVisita, idNinoVisita, idSolicitanteVisita,idDestinatarioVisita,periodoCambio, fechaLo, respuesta, nota);
                }
            } else {
                Log.e("OcioFragment", "Fecha o Hora no válida, no se puede crear el objeto Ocio");
            }

            if (ocioObj != null) {
                listadoHorario.add(ocioObj);
            }
        }
        adapter.notifyDataSetChanged();
    }



    @Override
    public void onItemClick(View view, int position) {

            SolicitudVisita item = adapter.getItem(position);
            eliminarHorario = new EliminarHorario(this);

            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage("¿Qué desea hacer?")
                    .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            eliminarHorario.execute(String.valueOf(item.getIdVisita()));
                            listadoHorario.remove(position);
                            adapter.notifyItemRemoved(position);
                        }
                    })
                    .setNeutralButton("Responder", null)
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();

            // Agregar botón "Responder No"
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(view.getContext())
                            .setMessage("¿Responder Sí o No?")
                            .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    actualizarRespuesta(item.getIdVisita(), "Si");
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    actualizarRespuesta(item.getIdVisita(), "No");
                                }
                            })
                            .show();
                }
            });
        }

        private void actualizarRespuesta(int idVisita, String respuesta) {
        new ActualizarRespuesta(this).execute(String.valueOf(idVisita), respuesta);

        }







    }




