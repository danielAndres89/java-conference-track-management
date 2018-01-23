/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.conferencia.modelo;

import com.proyecto.conferencia.exception.EventoInvalidoException;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author danielguilcapi
 */
public class ControladorArchivo {

    public ControladorArchivo() {
    }
    
    /**
     * Carga una lista de eventos desde un archivo.
     * @param nombreArchivo
     * @throws EventoInvalidoException
     */
    public static List<String> obtenerListaEventos(String nombreArchivo) throws EventoInvalidoException{
        
        
        List<String> lstEventos = new ArrayList<String>();
        try{
          // Abre el archivo.
          FileInputStream fstream = new FileInputStream(nombreArchivo);
          // Se obtiene el objeto DataInputStream
          DataInputStream in = new DataInputStream(fstream);
          BufferedReader br = new BufferedReader(new InputStreamReader(in));
          String strLine = br.readLine();
          //Lee el archivo linea por linea
          while (strLine != null)   {
            lstEventos.add(strLine);
            strLine = br.readLine();
          }
          //Cerrrar input stream
          in.close();
        }catch (Exception e){//Capturamos la excepcion
          System.err.println("Error: " + e.getMessage());
        }
        
        return lstEventos;
        
    }
    
       /**
     * Valida y crea una lista de eventos, revisa el tiempo para cada evento e inicializa el Objetivo Evento.
     * @param listaEventos
     * @throws Exception
     */
    public static List<Evento> crearEventos(List<String> listaEventos) throws Exception
    {
        // Si la lista de eventos es null la excepcion me devuelve que la lista de eventos es incorrecta.
        if(listaEventos == null)
            throw new EventoInvalidoException("Lista de Eventos Vacia");
        
        List<Evento> lstEventosValidos = new ArrayList<Evento>();
        int contador = -1;
        String minSufijo = "min";
        String lightningSufijo = "lightning";
        
        // Itera la lista de eventos y valida tiempos
        for(String evento : listaEventos)
        {
            int ultimoIndice = evento.lastIndexOf(" ");
            // Si el evento no tiene ningun espacio, significa que falta el titulo o la hora
            if(ultimoIndice == -1)
                throw new EventoInvalidoException("Evento invalido, " + evento + ". Se debe especificar el tiempo de duracion del evento.");
            
            String nombre = evento.substring(0, ultimoIndice);
            String tiempoStr = evento.substring(ultimoIndice + 1);
            // Si no tiene el titulo o esta en blanco
            if(nombre == null || "".equals(nombre.trim()))
                throw new EventoInvalidoException("Nombre del evento invalido, " + evento);
            // Si el tiempo de duracion no termina con min o lightning.
            else if(!tiempoStr.endsWith(minSufijo) && !tiempoStr.endsWith(lightningSufijo))
                throw new EventoInvalidoException("Tiempo del evento invalido, " + evento + ". El tiempo del evento debe terminar en min o en lightning");
            
            contador++;
            int tiempo = 0;
            // Parseamos a enteros el String tiempoStr.
            try{
                if(tiempoStr.endsWith(minSufijo)) {
                    tiempo = Integer.parseInt(tiempoStr.substring(0, tiempoStr.indexOf(minSufijo)));
                }
                else if(tiempoStr.endsWith(lightningSufijo)) {
                    String lightningTime = tiempoStr.substring(0, tiempoStr.indexOf(lightningSufijo));
                    if("".equals(lightningTime))
                        tiempo = 5;
                    else
                        tiempo = Integer.parseInt(lightningTime) * 5;
                }
            }catch(NumberFormatException nfe) {
                throw new EventoInvalidoException("Incapaz de parsear a tiempo " + tiempoStr + " para el evento " + evento);
            }
            
            // Añadimos el evento a la lista de eventos validos
            lstEventosValidos.add(new Evento(evento, nombre, tiempo));
        }
        
        return lstEventosValidos;
    }
    
}
