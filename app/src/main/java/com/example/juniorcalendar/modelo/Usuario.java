package com.example.juniorcalendar.modelo;

public class Usuario {
private  String idDni;
private String nombre;
private String apellidos;
private String email;
private String contrasenna;

    public Usuario() {

    }

    public Usuario(String idDni, String nombre, String apellidos, String email, String contrasenna) {
        this.idDni = idDni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.contrasenna = contrasenna;

    }




    public Usuario(String email, String contrasenna) {
    }


    public String getId_dni() {
        return idDni;
    }

    public void setId_dni(String id_dni) {
        this.idDni = id_dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenna() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }

    public String getIdDni() {
        return idDni;
    }

    public void setIdDni(String idDni) {
        this.idDni = idDni;
    }


    @Override
    public String toString() {
        return "Usuario{" +
                "idDni='" + idDni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", contrasenna='" + contrasenna + '\'' +

                '}';
    }
}
