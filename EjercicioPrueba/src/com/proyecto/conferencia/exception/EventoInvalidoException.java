/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.conferencia.exception;

/**
 *
 * @author danielguilcapi
 */
public class EventoInvalidoException extends Exception{
    
    @SuppressWarnings("compatibility:-140331834793898838")
    private static final long serialVersionUID = 1L;

    public EventoInvalidoException(String msg) {
        super(msg);
    }
    
}
