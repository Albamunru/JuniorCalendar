package com.example.juniorcalendar.modelo;

import java.time.LocalDate;

public class ConsultaMedica {

private int idConsultaMedica;
private String idNinnoMedico;
private LocalDate fechaMedico;
private LocalDate horaMedico;
private String especialidad;
private String observaciones;
private String ubicacion;

    public ConsultaMedica(int idConsultaMedica, String idNinnoMedico, LocalDate fechaMedico, LocalDate horaMedico, String especialidad, String observaciones, String ubicacion) {
        this.idConsultaMedica = idConsultaMedica;
        this.idNinnoMedico = idNinnoMedico;
        this.fechaMedico = fechaMedico;
        this.horaMedico = horaMedico;
        this.especialidad = especialidad;
        this.observaciones = observaciones;
        this.ubicacion = ubicacion;
    }


    public int getIdConsultaMedica() {
        return idConsultaMedica;
    }

    public void setIdConsultaMedica(int idConsultaMedica) {
        this.idConsultaMedica = idConsultaMedica;
    }

    public String getIdNinnoMedico() {
        return idNinnoMedico;
    }

    public void setIdNinnoMedico(String idNinnoMedico) {
        this.idNinnoMedico = idNinnoMedico;
    }

    public LocalDate getFechaMedico() {
        return fechaMedico;
    }

    public void setFechaMedico(LocalDate fechaMedico) {
        this.fechaMedico = fechaMedico;
    }

    public LocalDate getHoraMedico() {
        return horaMedico;
    }

    public void setHoraMedico(LocalDate horaMedico) {
        this.horaMedico = horaMedico;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }


    @Override
    public String toString() {
        return "ConsultaMedica{" +
                "idConsultaMedica=" + idConsultaMedica +
                ", idNinnoMedico='" + idNinnoMedico + '\'' +
                ", fechaMedico=" + fechaMedico +
                ", horaMedico=" + horaMedico +
                ", especialidad='" + especialidad + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }
}
