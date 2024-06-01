package com.example.juniorcalendar.modelo;

public class Relacion {
    private int idRelacion;
  /*  private String idUsuarioUno;
    private String idUsuarioDos;*/
    private String idDniNinno;
    private String idUsuario;
    //private int idParejaRelacion;

    public Relacion(int idRelacion, String idUsuario, String idDniNinno) {
        this.idRelacion = idRelacion;
        this.idUsuario = idUsuario;
        this.idDniNinno = idDniNinno;

    }


    public int getIdRelacion() {
        return idRelacion;
    }

    public void setIdRelacion(int idRelacion) {
        this.idRelacion = idRelacion;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }



    public String getIdDniNinno() {
        return idDniNinno;
    }

    public void setIdDniNinno(String idDniNinno) {
        this.idDniNinno = idDniNinno;
    }



    @Override
    public String toString() {
        return "Relacion{" +
                "idRelacion=" + idRelacion +
                ", idUsuarioUno='" + idUsuario + '\'' +
                ", idDniNinno='" + idDniNinno + '\'' +
                '}';
    }
}
