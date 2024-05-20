package com.example.juniorcalendar.modelo;

public class Relacion {
    private int idRelacion;
    private String idUsuarioUno;
    private String idUsuarioDos;
    private String idDniNinno;

    public Relacion(int idRelacion, String idUsuarioUno, String idUsuarioDos, String idDniNinno) {
        this.idRelacion = idRelacion;
        this.idUsuarioUno = idUsuarioUno;
        this.idUsuarioDos = idUsuarioDos;
        this.idDniNinno = idDniNinno;
    }


    public int getIdRelacion() {
        return idRelacion;
    }

    public void setIdRelacion(int idRelacion) {
        this.idRelacion = idRelacion;
    }

    public String getIdUsuarioUno() {
        return idUsuarioUno;
    }

    public void setIdUsuarioUno(String idUsuarioUno) {
        this.idUsuarioUno = idUsuarioUno;
    }

    public String getIdUsuarioDos() {
        return idUsuarioDos;
    }

    public void setIdUsuarioDos(String idUsuarioDos) {
        this.idUsuarioDos = idUsuarioDos;
    }

    public String getIdDniNinno() {
        return idDniNinno;
    }

    public void setIdDniNinno(String idDniNinno) {
        this.idDniNinno = idDniNinno;
    }

    @Override
    public String toString() {
        return "relacion{" +
                "idRelacion=" + idRelacion +
                ", idUsuarioUno='" + idUsuarioUno + '\'' +
                ", idUsuarioDos='" + idUsuarioDos + '\'' +
                ", idDniNinno='" + idDniNinno + '\'' +
                '}';
    }
}
