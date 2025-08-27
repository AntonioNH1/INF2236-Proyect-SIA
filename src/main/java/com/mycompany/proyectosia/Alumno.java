package com.mycompany.proyectosia;

import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author geoff
 */
public class Alumno {
    private String nombre;
    private String curso;
    private String rut; // rut esencial para creacion de mapa
    private ArrayList listaAsistencia;
    
    private static Set CURSOSVALIDOS = Set.of(
        "Primero basico", "Segundo basico", "Tercero basico", "Cuarto basico", "Quinto basico","Sexto basico"
        , "Septimo basico", "Octavo basico", "Primero medio","Segundo medio", "Tercero medio", "Cuarto medio"
    );
    
    
    
    // Constructor (validacion a traves de setter)
    public Alumno(String nombre, String curso, String rut){
        this.listaAsistencia = new ArrayList();
        setNombre(nombre);
        setCurso(curso);
        setRut(rut);
    }
    
    
    // Getters y Setters
    public void setNombre(String nombre){
        if(nombre == null || !nombre.matches("^[a-zA-Z ]+$")){
            System.out.println("nombre ingresado no valido.");
            return;
        }
        
        this.nombre = nombre;
    }
    
    public void setCurso(String curso){
        if(curso != null || !CURSOSVALIDOS.contains(curso)){
            System.out.println("curso ingresado no valido.");
            return;
        }
        
        this.curso = curso;
    }

    public void setRut(String rut){
        if(curso != null){
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
}
