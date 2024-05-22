package com.example.juniorcalendar.Fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;

import com.example.juniorcalendar.R;
import com.example.juniorcalendar.vista.MainActivityAnnadirEducacion;
import com.example.juniorcalendar.vista.MainActivityRegistroNinno;


public class EducacionFragment extends Fragment {

    private ImageButton annadirEducacion,calendarioEducacion;

public EducacionFragment(){

}
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

annadirEducacion=view.findViewById(R.id.imageButtonAddEducacion);
calendarioEducacion=view.findViewById(R.id.imageButtonCalendario);



annadirEducacion.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intentRegistroEduvacion = new Intent(view.getContext(), MainActivityAnnadirEducacion.class);
        startActivity(intentRegistroEduvacion);
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
        return inflater.inflate(R.layout.fragment_educacion, container, false);







    }




    /*private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                et1.setText(selectedDate);Recuperar();
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }*/





}