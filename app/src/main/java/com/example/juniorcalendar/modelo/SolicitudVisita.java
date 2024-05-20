package com.example.juniorcalendar.modelo;

import java.time.LocalDate;

public class SolicitudVisita {
    private int idVisita;
    private String idNinnoVisita;
    private String idSolicitanteVisita;

    private String periodoCambio;
    private LocalDate fechaSolicitud;
    private boolean respuesta;
    private String nota;


    public SolicitudVisita(int idVisita, String idNinnoVisita, String idSolicitanteVisita, String periodoCambio, LocalDate fechaSolicitud, boolean respuesta, String nota) {
        this.idVisita = idVisita;
        this.idNinnoVisita = idNinnoVisita;
        this.idSolicitanteVisita = idSolicitanteVisita;
        this.periodoCambio = periodoCambio;
        this.fechaSolicitud = fechaSolicitud;
        this.respuesta = respuesta;
        this.nota = nota;
    }


    public int getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(int idVisita) {
        this.idVisita = idVisita;
    }

    public String getIdNinnoVisita() {
        return idNinnoVisita;
    }

    public void setIdNinnoVisita(String idNinnoVisita) {
        this.idNinnoVisita = idNinnoVisita;
    }

    public String getIdSolicitanteVisita() {
        return idSolicitanteVisita;
    }

    public void setIdSolicitanteVisita(String idSolicitanteVisita) {
        this.idSolicitanteVisita = idSolicitanteVisita;
    }

    public String getPeriodoCambio() {
        return periodoCambio;
    }

    public void setPeriodoCambio(String periodoCambio) {
        this.periodoCambio = periodoCambio;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public boolean isRespuesta() {
        return respuesta;
    }

    public void setRespuesta(boolean respuesta) {
        this.respuesta = respuesta;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "solicitudVisita{" +
                "idVisita=" + idVisita +
                ", idNinnoVisita='" + idNinnoVisita + '\'' +
                ", idSolicitanteVisita='" + idSolicitanteVisita + '\'' +
                ", periodoCambio='" + periodoCambio + '\'' +
                ", fechaSolicitud=" + fechaSolicitud +
                ", respuesta=" + respuesta +
                ", nota='" + nota + '\'' +
                '}';
    }
}
