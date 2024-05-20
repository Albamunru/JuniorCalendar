package com.example.juniorcalendar.modelo;

import java.time.LocalDate;

public class Educacion {

    private int idEvento;
    private String idNinnoEducacion;
    private LocalDate fecha;
    private LocalDate hora;
    private String tipoEvento;
    private String ubicacion;
    private String descripcion;

    public Educacion(int idEvento, String idNinnoEducacion, LocalDate fecha, LocalDate hora, String tipoEvento, String ubicacion, String descripcion) {
        this.idEvento = idEvento;
        this.idNinnoEducacion = idNinnoEducacion;
        this.fecha = fecha;
        this.hora = hora;
        this.tipoEvento = tipoEvento;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getIdNinnoEducacion() {
        return idNinnoEducacion;
    }

    public void setIdNinnoEducacion(String idNinnoEducacion) {
        this.idNinnoEducacion = idNinnoEducacion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDate getHora() {
        return hora;
    }

    public void setHora(LocalDate hora) {
        this.hora = hora;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    @Override
    public String toString() {
        return "Educacion{" +
                "idEvento=" + idEvento +
                ", idNinnoEducacion='" + idNinnoEducacion + '\'' +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", tipoEvento='" + tipoEvento + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
