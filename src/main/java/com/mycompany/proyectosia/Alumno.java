/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectosia;

/**
 *
 * @author anton
 */
package com.mycompany.proyectosia;

public class Alumno {
    private String nombre;
    private String curso;

    public Alumno(String nombre, String curso) {
        this.nombre = nombre;
        this.curso = curso;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }
    
    @Override
    public String toString() {
        return "Alumno{nombre='" + nombre + "', curso='" + curso + "'}";
    }
}
