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
            // Setters
        public void setFecha(LocalDate fecha) {
            if (fecha == null) {
                System.out.println("Fecha no válida.");
                return;
            }
            this.fecha = fecha;
        }

        public void setEstado(EstadoAsistencia estado) {
            if (estado == null) {
                System.out.println("Estado no válido.");
                return;
            }
            this.estado = estado;
        }

        // fijar estado, ¿se podria cambiar enum por arreglo?
        public enum EstadoAsistencia{
            PRESENTE,
            AUSENTE,
            JUSTIFICADO
        }

    
}