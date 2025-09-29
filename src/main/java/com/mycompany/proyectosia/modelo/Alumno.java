package com.mycompany.proyectosia.modelo;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author geoff
 */
public class Alumno {
    private String nombre;
    private String curso;
    private String rut; // rut esencial para creacion del mapa hash
    private ArrayList<Asistencia> listaAsistencia;
   
    
    
    
    // Constructor (validacion a traves de setter, ya que aun no nos enseñan a crear excepciones)
    public Alumno(String nombre, String curso, String rut){
        this.listaAsistencia = new ArrayList<>();
        setNombre(nombre);
        setCurso(curso);
        setRut(rut);
    }
    
    
    // Getters y Setters
    public void setNombre(String nombre){
        if(nombre == null || !nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")){
            System.out.println("nombre ingresado no valido.");
            return;
        }
        
        this.nombre = nombre;
    }
    
    public void setCurso(String curso){
        if(curso == null){
            System.out.println("curso ingresado no valido.");
            return;
        }
        
        this.curso = curso;
    }

    public void setRut(String rut){
        if(rut == null){
            System.out.println("rut ingresado no valido.");
            return;
        }
        
        this.rut = rut;
    }
    
    public String getCurso(){
        return curso;
    }

    public String getNombre(){
        return nombre;
    }

    public String getRut(){
        return rut;
    }
    
    
    public void registrarAsistencia(Asistencia nuevaAsistencia){
        this.listaAsistencia.add(nuevaAsistencia);
    }
    
    // devuelve una lista de todas las asistencias que coincidan por su estado
    public ArrayList<Asistencia> getAsistencia(Asistencia.EstadoAsistencia estado) {
        ArrayList<Asistencia> resultados = new ArrayList<>();
        for (Asistencia asistencia : listaAsistencia) {
            if (asistencia.getEstado() == estado) {
                resultados.add(asistencia);
            }
        }
        return resultados;
    }
    
    // devuelve una lista de todas las asistencias que coincidan en un intervalo de tiempo
    public ArrayList<Asistencia> getAsistencia(LocalDate fechaInicio, LocalDate fechaFin) {
        ArrayList<Asistencia> resultados = new ArrayList<>();
        for (Asistencia asistencia : listaAsistencia) {
            LocalDate fechaAsistencia = asistencia.getFecha();
            // comprueba si la fecha está entre fechaInicio y fechaFin
            if (!fechaAsistencia.isBefore(fechaInicio) && !fechaAsistencia.isAfter(fechaFin)) {
                resultados.add(asistencia);
            }
        }
        return resultados;
    }
        
    public Asistencia getAsistencia(LocalDate fecha){
        for(Asistencia asistencia : listaAsistencia){
            if(asistencia.getFecha().equals(fecha)){
                return asistencia;
            }
        }
        return null;
    }
    
    
    public boolean borrarAsistencia(LocalDate fecha) {
        boolean borrado = false;
        // recorremos hacia atras para no provocar una excepcion al borrar
        for (int i = this.listaAsistencia.size() - 1; i >= 0; i--) {
            Asistencia asistencia = this.listaAsistencia.get(i);

            if (asistencia.getFecha().equals(fecha)) {
                this.listaAsistencia.remove(i);
                borrado = true;
            }
        }
        return borrado;
    }
    
    
    public boolean modificarAsistencia(LocalDate fechaOriginal, LocalDate nuevaFecha, Asistencia.EstadoAsistencia nuevoEstado) {
        Asistencia asistenciaAModificar = getAsistencia(fechaOriginal);

        if (asistenciaAModificar != null) {
            asistenciaAModificar.setFecha(nuevaFecha);
            asistenciaAModificar.setEstado(nuevoEstado);
            return true; 
        }

        return false;
    }
    
}
