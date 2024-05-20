package com.example.juniorcalendar.modelo;

import java.util.List;

public class MyData {

    private List<Educacion> listaEducacion;
    private List<Ocio> listaOcio;
    private List<ConsultaMedica> listaConsulta;
    private List<SolicitudVisita> listaSolicitudVisita;


    public MyData(List<Educacion> listaEducacion, List<Ocio> listaOcio, List<ConsultaMedica> listaConsulta, List<SolicitudVisita> listaSolicitudVisita) {
        this.listaEducacion = listaEducacion;
        this.listaOcio = listaOcio;
        this.listaConsulta = listaConsulta;
        this.listaSolicitudVisita = listaSolicitudVisita;

    }


    public List<Educacion> getListaEducacion() {
        return listaEducacion;
    }

    public void setListaEducacion(List<Educacion> listaEducacion) {
        this.listaEducacion = listaEducacion;
    }

    public List<Ocio> getListaOcio() {
        return listaOcio;
    }

    public void setListaOcio(List<Ocio> listaOcio) {
        this.listaOcio = listaOcio;
    }

    public List<ConsultaMedica> getListaConsulta() {
        return listaConsulta;
    }

    public void setListaConsulta(List<ConsultaMedica> listaConsulta) {
        this.listaConsulta = listaConsulta;
    }

    public List<SolicitudVisita> getListaSolicitudVisita() {
        return listaSolicitudVisita;
    }

    public void setListaSolicitudVisita(List<SolicitudVisita> listaSolicitudVisita) {
        this.listaSolicitudVisita = listaSolicitudVisita;
    }


    @Override
    public String toString() {
        return "MyData{" +
                "listaEducacion=" + listaEducacion +
                ", listaOcio=" + listaOcio +
                ", listaConsulta=" + listaConsulta +
                ", listaSolicitudVisita=" + listaSolicitudVisita +
                '}';
    }
}
