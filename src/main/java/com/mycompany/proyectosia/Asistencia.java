package com.mycompany.proyectosia;
import java.time.LocalDate;

public class Asistencia
{
    private LocalDate fecha;
    private EstadoAsistencia estado;
    
	public Asistencia(LocalDate fecha, EstadoAsistencia estado) {
		this.fecha = fecha;
		this.estado = estado;
	}
	
	// Getters
	public LocalDate getFecha() {
	    return fecha; 
	    
	}
    public EstadoAsistencia getEstado() { 
        return estado; 
        
    }
    
    // fijar estado, ¿se podria cambiar enum por arreglo?
    public enum EstadoAsistencia{
        PRESENTE,
        AUSENTE,
        JUSTIFICADO
    }
    
}