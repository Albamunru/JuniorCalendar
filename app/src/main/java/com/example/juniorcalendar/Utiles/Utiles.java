package com.example.juniorcalendar.Utiles;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utiles {

    public String formatearFecha(String fecha) {
        String fechaOriginal = fecha;
        String formatoOriginal = "dd-MM-yyyy";
        String formatoDestino = "yyyy-MM-dd";

        String fechaConvertida = null;
        try {

            SimpleDateFormat sdfOriginal = new SimpleDateFormat(formatoOriginal);


            Date date = sdfOriginal.parse(fechaOriginal);


            SimpleDateFormat sdfDestino = new SimpleDateFormat(formatoDestino);


            fechaConvertida = sdfDestino.format(date);

            System.out.println("Fecha convertida: " + fechaConvertida);
        } catch (ParseException e) {
            // Manejar la excepción si la fecha original no es válida
            System.out.println("Error: La fecha original no es válida.");
            e.printStackTrace();
        }


        return fechaConvertida;

    }

}
