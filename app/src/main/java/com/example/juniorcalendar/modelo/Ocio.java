package com.example.juniorcalendar.modelo;

import java.time.LocalDate;

public class Ocio {

    private int idActividad;
    private String idNinnoActividad;
    private String nombreActividad;
    private String descripcion;
    private LocalDate fechaActividad;
    private LocalDate horaActividad;
    private String ubicacionActividad;

    public Ocio(int idActividad, String idNinnoActividad, String nombreActividad, String descripcion, LocalDate fechaActividad, LocalDate horaActividad, String ubicacionActividad) {
        this.idActividad = idActividad;
        this.idNinnoActividad = idNinnoActividad;
        this.nombreActividad = nombreActividad;
        this.descripcion = descripcion;
        this.fechaActividad = fechaActividad;
        this.horaActividad = horaActividad;
        this.ubicacionActividad = ubicacionActividad;
    }


    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public String getIdNinnoActividad() {
        return idNinnoActividad;
    }

    public void setIdNinnoActividad(String idNinnoActividad) {
        this.idNinnoActividad = idNinnoActividad;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaActividad() {
        return fechaActividad;
    }

    public void setFechaActividad(LocalDate fechaActividad) {
        this.fechaActividad = fechaActividad;
    }

    public LocalDate getHoraActividad() {
        return horaActividad;
    }

    public void setHoraActividad(LocalDate horaActividad) {
        this.horaActividad = horaActividad;
    }

    public String getUbicacionActividad() {
        return ubicacionActividad;
    }

    public void setUbicacionActividad(String ubicacionActividad) {
        this.ubicacionActividad = ubicacionActividad;
    }

    @Override
    public String toString() {
        return "Ocio{" +
                "idActividad=" + idActividad +
                ", idNinnoActividad='" + idNinnoActividad + '\'' +
                ", nombreActividad='" + nombreActividad + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaActividad=" + fechaActividad +
                ", horaActividad=" + horaActividad +
                ", ubicacionActividad='" + ubicacionActividad + '\'' +
                '}';
    }
}
