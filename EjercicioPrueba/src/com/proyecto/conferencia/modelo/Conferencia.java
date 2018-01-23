/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.conferencia.modelo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 *
 * @author danielguilcapi
 */
public class Conferencia {

    /**
     * Constructor para Conferencia.
     */
    public Conferencia() {
    }
    
 /**
     * Metodo público que crea y programa horarios de la conferencia
     * @param nombreArchivo
     * @throws Exception
     */
    public List<List<Evento>> programarConferencia(String nombreArchivo) throws Exception
    {
        List<String> talkList = ControladorArchivo.obtenerListaEventos(nombreArchivo);
        return programarConferencia(talkList);
    }
    
    /**
     * Método público para crear y programar la conferencia
     * @param lstEventos
     * @throws Exception
     */
    public List<List<Evento>> programarConferencia(List<String> lstEventos) throws Exception
    {
        List<Evento> listaEventos = ControladorArchivo.crearEventos(lstEventos);
        return obtenerActividadesProgramadas(listaEventos);
    }
    
       /**
     * Programa las actividades de la conferencia en el horario de la mañana y tarde.
     * @param lstEvento
     * @throws Exception
     */
    private List<List<Evento>> obtenerActividadesProgramadas(List<Evento> lstEvento) throws Exception
    {
        // Encuentra todos los posibles dias
        int diaPorMinuto = 6 * 60;
        int tiempoTotalEventos = obtenerTiempoTotalEventos(lstEvento);
        int diasPosibles = tiempoTotalEventos/diaPorMinuto;
        
        // Ordena la lista de eventos.
        List<Evento> lstEventosOperar = new ArrayList<Evento>(); 
        lstEventosOperar.addAll(lstEvento);
        Collections.sort(lstEventosOperar);
        
        // Encuentra posibles combinaciones en las actividades de la mañana
        List<List<Evento>> combActividadesMan = encontrarPosiblesActividades(lstEventosOperar, diasPosibles, true);
        
        // Remueve todas las actividades programadas en la mañana, de la lista a operar.
        for(List<Evento> talkList : combActividadesMan) {
            lstEventosOperar.removeAll(talkList);
        }
        
        // Encuentra posibles combinaciones en las actividades de la tarde
        List<List<Evento>> combActividadesTar = encontrarPosiblesActividades(lstEventosOperar, diasPosibles, false);
        
        // Remueve todas las actividades programadas en la tarde, de la lista a operar.
        for(List<Evento> talkList : combActividadesTar) {
            lstEventosOperar.removeAll(talkList);
        }
        
        // revisa si la lista a operar no esta vacia, entonces intenta llenar los eventos restantes en las actividades de la tarde
        int maxTiempoLimiteActividad = 240;
        if(!lstEventosOperar.isEmpty()) {
            List<Evento> lstEventosProgramados = new ArrayList<Evento>();
            for(List<Evento> lstEventos : combActividadesTar) {
                int tiempoTotal = obtenerTiempoTotalEventos(lstEventos);
                
                for(Evento evento : lstEventosOperar) {
                    int tiempoEvento = evento.getTiempoDuracion();
                    
                    if(tiempoEvento + tiempoTotal <= maxTiempoLimiteActividad) {
                        lstEventos.add(evento);
                        evento.setProgramado(true);
                        lstEventosProgramados.add(evento);
                    }
                }
                
                lstEventosOperar.removeAll(lstEventosProgramados);
                if(lstEventosOperar.isEmpty())
                    break;
            }
        }
        
        // Si la lista a operar todavia no esta vacia, significa que la conferencia no puede ser programada con los datos ingresados
        if(!lstEventosOperar.isEmpty())
        {
            throw new Exception("Incapaz de programar todos los eventos para la conferencia.");
        }
        
        // Programar los eventos del dia con las actividades de la mañana y tarde
        return obtenerEventosProgramados(combActividadesMan, combActividadesTar);
    }
    
    
    /**
     * Pra obtener el tiempo total de los eventos, de la lista dada
     * @param lstEventos
     * @return
     */
    public static int obtenerTiempoTotalEventos(List<Evento> lstEventos)
    {
        if(lstEventos == null || lstEventos.isEmpty())
            return 0;
        
        int tiempoTotal = 0;
        for(Evento evento : lstEventos) {
            tiempoTotal += evento.tiempoDuracion;
        }
        return tiempoTotal;
    }
    
    
    
    /**
     * Encuentra posibles combinaciones para las actividades
     * Si es en la mañana entonces cada actividad debe tener un total de 3 hr.
     * Si es en la tarde entonces cada actividad debe tener un tiempo mayor a 3 hr.
     * @param lstEventosOperar
     * @param diasPosibles
     * @param primeraActividad
     * @return
     */
    private List<List<Evento>> encontrarPosiblesActividades (List<Evento> lstEventosOperar, int diasPosibles, boolean primeraActividad)
    {
        int minTiempoActiviad= 180;
        int maxTiempoActividad = 240;
        
        if(primeraActividad)
            maxTiempoActividad = minTiempoActiviad;
        
        int tamListaEventos = lstEventosOperar.size();
        List<List<Evento>> posibleCombinacionEventos = new ArrayList<List<Evento>>();
        int contadorPosibleCombinacion = 0;
        
        // Bucle para obtener la combinacion, dentro de todos los dias posibles
        // Verifica uno por uno cada evento para obtener la posible combinacion
        for(int count = 0; count < tamListaEventos; count++) {
            int puntoInicial = count;
            int tiempoTotal = 0;
            List<Evento> listaPosiblesCombinaciones= new ArrayList<Evento>();
            
            // Bucle para obtener las posibles combinaciones
            while(puntoInicial != tamListaEventos) {
                int actual = puntoInicial;
                puntoInicial++;
                Evento eventoActual = lstEventosOperar.get(actual);
                if(eventoActual.isProgramado())
                    continue;
                int tiempoEvento = eventoActual.getTiempoDuracion();
                // Si el tiempo del evento actual es mayor que maxTiempoActividad o
                // la suma del tiempo acutal y el tiempo total de los eventos añadidos en la lista es mayor que maxTiempoActividad.
                // entonces puede continuar.
                if(tiempoEvento > maxTiempoActividad || tiempoEvento + tiempoTotal > maxTiempoActividad) {
                    continue;
                }
                
                listaPosiblesCombinaciones.add(eventoActual);
                tiempoTotal += tiempoEvento;
                
                // Si el tiempo total es completado para la actividad actual entonces sale del bucle.
                if(primeraActividad) {
                    if(tiempoTotal == maxTiempoActividad)
                        break;
                }else if(tiempoTotal >= minTiempoActiviad)
                    break;
            }
            
            // Tiempo valido para las actividades de la mañana es igual a maxTiempoActividad.
            // Tiempo valido para las actividades de la tarde es menor o igual a maxTiempoActividad  y mayo o igual que minTiempoActiviad
            boolean actividadValida = false;
            if(primeraActividad)
                actividadValida = (tiempoTotal == maxTiempoActividad);
            else
                actividadValida = (tiempoTotal >= minTiempoActiviad && tiempoTotal <= maxTiempoActividad);
            
            // SI la actividad es validad entonces se añade la misma en la lista de combinaciones posibles y establecemos todo como programado
            if(actividadValida) {
                posibleCombinacionEventos.add(listaPosiblesCombinaciones);
                for(Evento evento : listaPosiblesCombinaciones){
                    evento.setProgramado(true);
                }
                contadorPosibleCombinacion++;
                if(contadorPosibleCombinacion == diasPosibles)
                    break;
            }
        }
        
        return posibleCombinacionEventos;
    }
    
    /**
     * Imprime la programación de las actividades con el texto esperado
     * @param actividadesMan
     * @param actividadesTar
     */
    private List<List<Evento>> obtenerEventosProgramados(List<List<Evento>> actividadesMan, List<List<Evento>> actividadesTar)
    {
        List<List<Evento>> listaEventosProgramados = new ArrayList<List<Evento>>();
        int totalDiasPosibles = actividadesMan.size();
        
        // Bucle para programar los eventos de todos los dias
        for(int contDia = 0; contDia < totalDiasPosibles; contDia++) {
            List<Evento> listaEventos = new ArrayList<Evento>();
            
            // Crear e inicilizar una fecha a las 09:00 AM.
            
            Date date = new Date( );
            SimpleDateFormat dateFormat = new SimpleDateFormat ("hh:mma ");
            date.setHours(9);
            date.setMinutes(0);
            date.setSeconds(0);
            
            int contActividad = contDia + 1;
            String tiempoProgramado = dateFormat.format(date);
            
            System.out.println("Track " + contActividad + ":");
            
            // Actividades en la Mañana - establece el tiempo programado para cada evento y obtiene la siguiente hora usando la duracion o el evento actual
            List<Evento> lstEventosProgramadosMan = actividadesMan.get(contDia);
            for(Evento evento : lstEventosProgramadosMan) {
                evento.setTiempoProgramado(tiempoProgramado);
                System.out.println(tiempoProgramado + evento.getTitulo());
                tiempoProgramado = obtenerSiguientePrograma(date, evento.getTiempoDuracion());
                listaEventos.add(evento);
            }
            
            // Programar 60 mins para el Lunch
            int lunchTimeDuration = 60;
            Evento eventoLunch = new Evento("Lunch", "Lunch", 60);
            eventoLunch.setTiempoProgramado(tiempoProgramado);
            listaEventos.add(eventoLunch);
            System.out.println(tiempoProgramado + "Lunch");
            
            // Actividades en la Tarde - establece el tiempo programado para cada evento y obtiene la siguiente hora usando la duracion o el evento actual
            tiempoProgramado = obtenerSiguientePrograma(date, lunchTimeDuration);
            List<Evento> lstEventosProgramadosTar = actividadesTar.get(contDia);
            for(Evento evento : lstEventosProgramadosTar) {
                evento.setTiempoProgramado(tiempoProgramado);
                listaEventos.add(evento);
                System.out.println(tiempoProgramado + evento.getTitulo());
                tiempoProgramado = obtenerSiguientePrograma(date, evento.getTiempoDuracion());
            }
            
            // Programa el evento "Networking Event" al final de las actividades, la duración es justo para inicializar el objeto Evento
            Evento networkingTalk = new Evento("Networking Event", "Networking Event", 60);
            networkingTalk.setTiempoProgramado(tiempoProgramado);
            listaEventos.add(networkingTalk);
            System.out.println(tiempoProgramado + "Networking Event\n");
            listaEventosProgramados.add(listaEventos);
        }
        
        return listaEventosProgramados;
    }
    
    
    /**
     * Para obtener la siguiente hora programada en formato String
     * @param date
     * @param tiempoDuracion
     * @return str
     */
    private String obtenerSiguientePrograma(Date date, int tiempoDuracion)
    {
        long tiempoInLong  = date.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat ("hh:mma ");
        
        long tiempoDuracionInLong = tiempoDuracion * 60 * 1000;
        long nuevoTiempoInLong = tiempoInLong + tiempoDuracionInLong;
        
        date.setTime(nuevoTiempoInLong);
        String str = dateFormat.format(date);
        return str;
    }
    
    
}
