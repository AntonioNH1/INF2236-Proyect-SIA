package com.mycompany.proyectosia.modelo;
import java.time.LocalDate;

public class Asistencia
{
    private LocalDate fecha;
    private EstadoAsistencia estado;
    
	public Asistencia(LocalDate fecha, EstadoAsistencia estado) {
		setFecha(fecha);
		setEstado(estado);
	}
        
    // Getters y setters
    public LocalDate getFecha() {
        return fecha; 

    }
    public EstadoAsistencia getEstado() { 
        return estado; 
        
    }
    
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    
    public void setEstado(EstadoAsistencia nuevoEstado) {
        this.estado = nuevoEstado;
    }
    
    
    public enum EstadoAsistencia{
        PRESENTE,
        AUSENTE,
        JUSTIFICADO
    }
    
    
}