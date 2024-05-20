package com.example.juniorcalendar.modelo;

import java.time.LocalDate;

public class Ninno {
    private String idDniNinno;
    private String nombreNinno;
    private String apellidosNinno;
    private LocalDate fechaNacimientoNinno;

    public Ninno(String idDniNinno, String nombreNinno, String apellidosNinno, LocalDate fechaNacimientoNinno) {
        this.idDniNinno = idDniNinno;
        this.nombreNinno = nombreNinno;
        this.apellidosNinno = apellidosNinno;
        this.fechaNacimientoNinno = fechaNacimientoNinno;
    }


    public String getIdDniNinno() {
        return idDniNinno;
    }

    public void setIdDniNinno(String idDniNinno) {
        this.idDniNinno = idDniNinno;
    }

    public String getNombreNinno() {
        return nombreNinno;
    }

    public void setNombreNinno(String nombreNinno) {
        this.nombreNinno = nombreNinno;
    }

    public String getApellidosNinno() {
        return apellidosNinno;
    }

    public void setApellidosNinno(String apellidosNinno) {
        this.apellidosNinno = apellidosNinno;
    }

    public LocalDate getFechaNacimientoNinno() {
        return fechaNacimientoNinno;
    }

    public void setFechaNacimientoNinno(LocalDate fechaNacimientoNinno) {
        this.fechaNacimientoNinno = fechaNacimientoNinno;
    }


    @Override
    public String toString() {
        return "ninno{" +
                "idDniNinno='" + idDniNinno + '\'' +
                ", nombreNinno='" + nombreNinno + '\'' +
                ", apellidosNinno='" + apellidosNinno + '\'' +
                ", fechaNacimientoNinno=" + fechaNacimientoNinno +
                '}';
    }
}
