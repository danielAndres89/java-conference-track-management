/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.conferencia.principal;

import com.proyecto.conferencia.exception.EventoInvalidoException;
import com.proyecto.conferencia.modelo.Conferencia;

/**
 *
 * @author danielguilcapi
 */
public class Principal {

    /**
     * Metodo Principal para ejecutar el programa.
     *
     * @param args
     */
    public static void main(String[] args) {

//El archivo debe estar en la misma carpeta edl proyecto
        String fileName = "datosConferencia.txt";
        Conferencia Conferencia = new Conferencia();
        try {
            Conferencia.programarConferencia(fileName);
        } catch (EventoInvalidoException ite) {
            ite.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
