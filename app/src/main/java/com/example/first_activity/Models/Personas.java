package com.example.first_activity.Models;

import java.net.Inet4Address;

public class Personas {
    private Integer id;
    private String nombres;
    private String apellidos;
    private Integer edades;
    private String correos;

    public Personas() {

    }

    public Personas(Integer id, String nombres, String apellidos, Integer edades, String correos) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.edades = edades;
        this.correos = correos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Integer getEdades() {
        return edades;
    }

    public void setEdades(Integer edades) {
        this.edades = edades;
    }

    public String getCorreos() {
        return correos;
    }

    public void setCorreos(String correos) {
        this.correos = correos;
    }
}
