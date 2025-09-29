
package com.mycompany.proyectosia.modelo;

/**
 *
 * @author geoff
 */
import com.mycompany.proyectosia.controlador.SistemaAsistenciaEscolar;
import com.mycompany.proyectosia.modelo.Asistencia;
import com.mycompany.proyectosia.modelo.Alumno;
import java.time.LocalDate;
import java.util.ArrayList;

public class Profesor {
    private String nombre;
    private String rut;
    private String cursoJefatura;

    public Profesor(String nombre, String rut, String cursoJefatura) {
        setNombre(nombre);
        setRut(rut);
        setCursoJefatura(cursoJefatura);
    }

    // Getters y setters
    public String getNombre() { 
        return nombre; 
    }
    public String getRut() {
        return rut; 
    }
    
    public String getCursoJefatura(){
        return cursoJefatura;
    }
    
    public void setNombre(String nombre){
        if(nombre == null || !nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")){
            System.out.println("nombre ingresado no valido.");
            return;
        }
        
        this.nombre = nombre;
    }
    public void setRut(String rut){
        if(rut == null){
            System.out.println("rut ingresado no valido.");
            return;
        }
        
        this.rut = rut;
    }
    
    public void setCursoJefatura(String cursoJefatura){
        if(cursoJefatura == null){
            System.out.println("curso ingresado no valido.");
            return;
        }
        
        this.cursoJefatura = cursoJefatura;
    }
    

    // cambia el estado de AUSENTE a JUSTIFICADO de la asistencia de un alumno en un dia especifico
    public void justificarAusencia(SistemaAsistenciaEscolar sistema, Alumno alumno, LocalDate fecha) {
        if (alumno == null) {
            System.out.println("Error: El alumno no puede ser nulo.");
            return;
        }

        Asistencia asistencia = alumno.getAsistencia(fecha);

        if (asistencia != null && asistencia.getEstado() == Asistencia.EstadoAsistencia.AUSENTE) {
            asistencia.setEstado(Asistencia.EstadoAsistencia.JUSTIFICADO);
            System.out.println("Ausencia del alumno " + alumno.getNombre() + " en la fecha " + fecha + " ha sido justificada.");
        } else {
            System.out.println("No se encontró un registro de ausencia para el alumno " + alumno.getNombre() + " en esa fecha.");
        }
    }

    // cambia el estado de AUSENTE a JUSTIFICADO de la asistencia de todos los alumnos de un curso en un dia especifico (ej: salidas escolares)
    public void justificarAusencia(SistemaAsistenciaEscolar sistema, String curso, LocalDate fecha) {

        ArrayList<Alumno> alumnosDelCurso = sistema.obtenerAlumnosPorCurso(curso);
        
        if (alumnosDelCurso.isEmpty()) {
            System.out.println("No se encontraron alumnos en el curso " + curso);
            return;
        }

        System.out.println("Justificando ausencias para el curso '" + curso + "' en la fecha " + fecha + "...");
        int justificacionesRealizadas = 0;
        for (Alumno alumno : alumnosDelCurso) {
            Asistencia asistencia = alumno.getAsistencia(fecha);
            if (asistencia != null && asistencia.getEstado() == Asistencia.EstadoAsistencia.AUSENTE) {
                asistencia.setEstado(Asistencia.EstadoAsistencia.JUSTIFICADO);
                justificacionesRealizadas++;
            }
        }
        System.out.println("Proceso finalizado. Se realizaron " + justificacionesRealizadas + " justificaciones.");
    }
}
