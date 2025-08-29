/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectosia;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author anton
 */
public class SistemaAsistenciaEscolar {

    private BufferedReader lectorConsola;
    
    //Clave: RUT, Valor: Alumno
    private Map mapaAlumnos;

    public SistemaAsistenciaEscolar(){
        this.lectorConsola = new BufferedReader(new InputStreamReader(System.in));
        this.mapaAlumnos = new HashMap();
    }
    
    
    public void iniciar() throws IOException {
        int opcionMenu;

        do {
            System.out.println("\n=== Sistema de Asistencia ===");
            System.out.println("1. Insertar alumno");
            System.out.println("2. Mostrar asistencia alumnos");
            System.out.println("3. Buscar alumno por rut");
            System.out.println("4. Registrar asistencia por dia");
            System.out.println("5. Salir");
            System.out.print("Elige una opcion: ");

            opcionMenu = leerEntero();

            switch (opcionMenu) {
                case 1:
                    this.insertarAlumno();
                    break;
                case 2:
                    mostrarAsistenciaAlumnos();
                    break;
                case 3:
                    this.buscarAlumnoPorRut();
                    break;
                case 4:
                    this.registrarAsistenciaDiaria();
                    break;
                case 5:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opcionMenu != 5);
    }
    
    private void insertarAlumno() throws IOException{
        System.out.println("\n --- Nuevo Alumno ---");
        
        System.out.print("Ingrese rut del nuevo alumno: ");
        String rut = this.lectorConsola.readLine();
        
        if(this.mapaAlumnos.containsKey(rut)){
            System.out.println("Error: Ya existe un alumno con el RUT " + rut);
            return;
        }
        
        System.out.print("Ingrese nombre completo del nuevo alumno: ");
        String nombre = this.lectorConsola.readLine();
        
        System.out.print("Ingrese curso del nuevo alumno: ");
        String curso = this.lectorConsola.readLine();

        Alumno nuevoAlumno = new Alumno(nombre, curso, rut);
        
        this.mapaAlumnos.put(rut, nuevoAlumno);
        
        System.out.println("El alumno " + nuevoAlumno.getNombre() + " se ha creado con exito.");
    
    }
    
    private void buscarAlumnoPorRut() throws IOException {
    System.out.println("Ingrese RUT a buscar");
    String rut = this.lectorConsola.readLine();

    Alumno alumno = obtenerAlumnoPorRut(rut);
    if (alumno == null) {
        System.out.println("No existe el alumno con ese RUT");
        return;
    }
    System.out.println("Alumno: " + alumno.getNombre() + "| Curso: " + alumno.getCurso() + "| RUT: " + alumno.getRut());
    }
    
/*    Este metodo ya no se ultiliza, puesto a que usamos  this.mapaAlumnos.containsKey(rut)
    private boolean existeAlumno(String nombre, String rut){
        for(Object objetoAlumno : this.listaAlumnos){
            Alumno alumnoLista = (Alumno)objetoAlumno;
            if(nombre.equals(alumnoLista.getNombre()) && rut.equals(alumnoLista.getRut())){
                return true;
            }
        }
        
        return false;
    }
    */
   
    private void registrarAsistenciaDiaria() throws IOException {
        
        System.out.println("\n--- Registro de Asistencia Diario ---");
        
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaDeAsistencia = leerYConvertirFecha();

        // Pedida de curso
        System.out.print("Ingrese el curso para pasar lista (ej: Primero basico): ");
        String cursoSeleccionado = this.lectorConsola.readLine();

        System.out.println("\nPasando lista para el curso '" + cursoSeleccionado + "' en la fecha " + fechaDeAsistencia.format(formatoFecha));

        for (Object objAlumno : this.mapaAlumnos.values()) {
            Alumno alumno = (Alumno) objAlumno;

            // agrupar por curso
            if (alumno.getCurso().equalsIgnoreCase(cursoSeleccionado)) {
                String estadoInput;
                Asistencia.EstadoAsistencia estadoFinal = null;

                // se pregunta la asistencia por cada alumno del curso
                while (estadoFinal == null) {
                    System.out.print("Estado de " + alumno.getNombre() + " (P=Presente, A=Ausente): ");
                    estadoInput = this.lectorConsola.readLine();

                    if (estadoInput.equalsIgnoreCase("P")) {
                        estadoFinal = Asistencia.EstadoAsistencia.PRESENTE;
                    } else if (estadoInput.equalsIgnoreCase("A")) {
                        estadoFinal = Asistencia.EstadoAsistencia.AUSENTE;
                    } else {
                        System.err.println("Opción inválida.");
                    }
                }

                // Creamos el nuevo objeto de asistencia y se lo añadimos al historial del alumno.
                Asistencia nuevoRegistro = new Asistencia(fechaDeAsistencia, estadoFinal);
                alumno.registrarAsistencia(nuevoRegistro);
            }
        }
    }
    
    private void mostrarAsistenciaAlumnos() throws IOException{
        LocalDate fechaDeAsistencia = leerYConvertirFecha();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // Pedida de curso
        System.out.print("Ingrese el curso para ver la asistencia (ej: Primero basico): ");
        String cursoSeleccionado = this.lectorConsola.readLine();

        System.out.println("\nMostrando asistencia del curso '" + cursoSeleccionado + "' en la fecha " + fechaDeAsistencia.format(formatoFecha));
        
        for (Object objAlumno : this.mapaAlumnos.values()) {
            Alumno alumno = (Alumno) objAlumno;

            // agrupar por curso
            if (alumno.getCurso().equalsIgnoreCase(cursoSeleccionado)) {
                    System.out.print("Estado de " + alumno.getNombre() + " ("+ alumno.listaAsistencia.get(estado) +") ");
                }
       
            }

    }
    // Utilidades
    
    private LocalDate leerYConvertirFecha()throws IOException{
        LocalDate fecha = null;
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // pedida de fecha
        while (fecha == null) {
            System.out.print("Ingrese la fecha para el registro (formato dd/MM/yyyy): ");
            String fechaTexto = this.lectorConsola.readLine();

            fecha = LocalDate.parse(fechaTexto, formatoFecha);
        }
        return fecha;
    }
    
    private Alumno obtenerAlumnoPorRut(String rut) {
        return (Alumno) this.mapaAlumnos.get(rut);
    }
    // lector enteros(¿estamos autorizados para usar try/catch? esto es materia de exceptions)
    private int leerEntero()throws IOException{
        int opcion = Integer.parseInt(this.lectorConsola.readLine());
        return opcion;
    }
}
    
    