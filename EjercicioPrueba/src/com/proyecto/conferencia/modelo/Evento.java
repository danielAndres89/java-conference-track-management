/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.conferencia.modelo;

/**
 *
 * @author danielguilcapi
 */
public class Evento implements Comparable{
    
    String titulo;
    String nombre;
    int tiempoDuracion;
    boolean programado = false;
    String tiempoProgramado;

    /**
         * Constructor para Evento.
         * @param titulo
         * @param nombre
         * @param tiempoDuracion
         */
    public Evento(String titulo, String nombre, int tiempoDuracion) {
        this.titulo = titulo;
        this.nombre = nombre;
        this.tiempoDuracion = tiempoDuracion;

    }

    public boolean isProgramado() {
        return programado;
    }

    public void setProgramado(boolean programado) {
        this.programado = programado;
    }

    public String getTiempoProgramado() {
        return tiempoProgramado;
    }

    public void setTiempoProgramado(String tiempoProgramado) {
        this.tiempoProgramado = tiempoProgramado;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getTiempoDuracion() {
        return tiempoDuracion;
    }
    


    @Override
    public int compareTo(Object o) {
        
        Evento evento = (Evento)o;
        if(this.tiempoDuracion > evento.tiempoDuracion)
            return -1;
        else if(this.tiempoDuracion < evento.tiempoDuracion)
            return 1;
        else
            return 0;
    }
    
    
    
}
