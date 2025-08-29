/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectosia;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author anton
 */
public class SistemaAsistenciaEscolar {

    private BufferedReader lectorConsola;
    
    private Map<String, Alumno> mapaAlumnos;
    private ArrayList<Profesor> listaProfesores;

    public SistemaAsistenciaEscolar(){
        this.lectorConsola = new BufferedReader(new InputStreamReader(System.in));
        this.mapaAlumnos = new HashMap<>();
        this.listaProfesores = new ArrayList<>();
        
        // cargar datos para mostrar ejemplos
        cargarDatosIniciales();
    }
    
    
    public void iniciar() throws IOException {
        int opcionMenu;

        do {
            System.out.println("\n=== Sistema de Asistencia general ===");
            System.out.println("1. Insertar alumno");
            System.out.println("2. Mostrar asistencia alumnos de un curso");
            System.out.println("3. Buscar alumno por RUT");
            System.out.println("4. Registrar asistencia por dia");
            System.out.println("5. Generar Reporte de Alumno");
            System.out.println("\n\n=== Modulo profesores ===");
            System.out.println("6. Insertar profesor");
            System.out.println("7. Justificar ausencias"); 
            System.out.println("8. Salir");
            System.out.print("Elige una opción: ");

            opcionMenu = leerEntero();

            switch (opcionMenu) {
                case 1:
                    insertarAlumno();
                    break;
                case 2:
                    mostrarAsistenciaAlumnos();
                    break;
                case 3:
                    buscarAlumnoPorRut();
                    break;
                case 4:
                    registrarAsistenciaDiaria();
                    break;
                case 5:
                    gestionarReportes();
                    break;
                case 6:
                    insertarProfesor();
                    break;
                case 7:
                    gestionarJustificaciones();
                    break;
                case 8:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
                }
        } while (opcionMenu != 8);
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
    

   
    private void registrarAsistenciaDiaria() throws IOException {
        
        System.out.println("\n--- Registro de Asistencia Diario ---");
        
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaDeAsistencia = leerYConvertirFecha();

        // Pedida de curso
        System.out.print("Ingrese el curso para pasar lista (ej: Primero basico): ");
        String cursoSeleccionado = this.lectorConsola.readLine();

        System.out.println("\nPasando lista para el curso '" + cursoSeleccionado + "' en la fecha " + fechaDeAsistencia.format(formatoFecha));

        for (Alumno alumno : this.mapaAlumnos.values()) {

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
        
        boolean encontrado = false;
        for (Alumno alumno : this.mapaAlumnos.values()) {
            // agrupar por curso
            if (alumno.getCurso().equalsIgnoreCase(cursoSeleccionado)) {
                encontrado = true;
                Asistencia asistencia = alumno.getAsistencia(fechaDeAsistencia);
                if(asistencia != null){
                    System.out.print("\nEstado del alumno " + alumno.getNombre() + ": ("+ asistencia.getEstado() +") ");
                }
                else{
                    System.out.print("\nEl alumno "+ alumno.getNombre() + " no contempla registro en esa fecha");
                }
       
            }
        }
        if (!encontrado) {
            System.out.println("No hay alumnos registrados en el curso '" + cursoSeleccionado + "'.");
        }
    }
    
    public ArrayList<Alumno> obtenerAlumnosPorCurso(String curso){
        ArrayList<Alumno> alumnosEncontrados = new ArrayList<>();
        for(Alumno alumno : this.mapaAlumnos.values()){
            if(alumno.getCurso().equalsIgnoreCase(curso)){
                alumnosEncontrados.add(alumno);
            }
        }
        
        return alumnosEncontrados;
    }
    
private void gestionarReportes() throws IOException {
    System.out.println("\n--- Generador de Reportes de Alumno ---");
    System.out.print("Ingrese el RUT del alumno para generar el reporte: ");
    String rut = lectorConsola.readLine();
    Alumno alumno = obtenerAlumnoPorRut(rut);

    if (alumno == null) {
        System.out.println("No se encontró al alumno con el RUT " + rut);
        return;
    }

    System.out.println("Reportes para: " + alumno.getNombre());
    System.out.println("1. Ver historial completo de ausencias");
    System.out.println("2. Ver asistencia en un rango de fechas");
    System.out.print("Elige una opción: ");
    int opcion = leerEntero();

    if (opcion == 1) {
        ArrayList<Asistencia> ausencias = alumno.getAsistencia(Asistencia.EstadoAsistencia.AUSENTE);
        System.out.println("\n--- Historial de Ausencias ---");
        if (ausencias.isEmpty()) {
            System.out.println("El alumno no tiene ausencias registradas.");
        } else {
            for (Asistencia a : ausencias) {
                System.out.println("- Fecha: " + a.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        }

    } else if (opcion == 2) {
        System.out.println("\n--- Asistencia por Rango de Fechas ---");
        System.out.println("Ingrese la fecha de INICIO:");
        LocalDate fechaInicio = leerYConvertirFecha();
        System.out.println("Ingrese la fecha de FIN:");
        LocalDate fechaFin = leerYConvertirFecha();
        
        ArrayList<Asistencia> registros = alumno.getAsistencia(fechaInicio, fechaFin);
        if(registros.isEmpty()){
            System.out.println("No hay registros de asistencia en ese rango de fechas.");
        } else {
            for(Asistencia a : registros){
                 System.out.println("- Fecha: " + a.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " | Estado: " + a.getEstado());
            }
        }
        
    } else {
        System.out.println("Opción no válida.");
    }
}
    
    private void gestionarJustificaciones() throws IOException {
        System.out.println("\n--- Módulo de Justificación de Ausencias ---");
        
        if (listaProfesores.isEmpty()) {
            System.out.println("No hay profesores registrados en el sistema. Por favor, inserte un profesor primero.");
            return;
        }

        System.out.println("Por favor, seleccione el profesor que realizará la justificación:");
        for (int i = 0; i < listaProfesores.size(); i++) {
            Profesor profesor = listaProfesores.get(i);
            System.out.println((i + 1) + ". " + profesor.getNombre() + " | profesor/a jefe/a de " + profesor.getCursoJefatura());
        }
        System.out.print("Elige una opción: ");
        
        int opcionProfesor = leerEntero();
        if (opcionProfesor < 1 || opcionProfesor > listaProfesores.size()) {
            System.out.println("Opción no válida.");
            return;
        }
        
        Profesor profesorSeleccionado = listaProfesores.get(opcionProfesor - 1);
        System.out.println("Sesión iniciada como: " + profesorSeleccionado.getNombre());

        
        System.out.println("\n1. Justificar ausencia de un alumno individual");
        System.out.println("2. Justificar ausencia del curso completo (" + profesorSeleccionado.getCursoJefatura() + ")");
        System.out.print("Elige una opción: ");
        int opcionJustificacion = leerEntero();
        
        if (opcionJustificacion != 1 && opcionJustificacion != 2) {
            System.out.println("Opción no válida.");
            return;
        }

        LocalDate fecha = leerYConvertirFecha();

        if (opcionJustificacion == 1) {
            System.out.print("Ingrese el RUT del alumno a justificar: ");
            String rutAlumno = lectorConsola.readLine();
            Alumno alumno = obtenerAlumnoPorRut(rutAlumno);
            profesorSeleccionado.justificarAusencia(this, alumno, fecha);
        } else { 
            profesorSeleccionado.justificarAusencia(this, profesorSeleccionado.getCursoJefatura(), fecha);
        }
    }
    
    
    
     private void insertarProfesor() throws IOException {
        System.out.println("\n--- Nuevo Profesor ---");
        System.out.print("Ingrese RUT del nuevo profesor: ");
        String rut = this.lectorConsola.readLine();
        
        // Validación: No permitir RUTs de profesor duplicados
        for(Profesor profesor : listaProfesores) {
            if(profesor.getRut().equalsIgnoreCase(rut)) {
                System.out.println("Error: Ya existe un profesor con el RUT " + rut);
                return;
            }
        }

        System.out.print("Ingrese nombre completo del nuevo profesor: ");
        String nombre = this.lectorConsola.readLine();
        
        System.out.print("Ingrese el curso del cual será profesor jefe: ");
        String curso = this.lectorConsola.readLine();

        // validamos que un curso no tenga más de un profesor jefe
        for(Profesor profesor : listaProfesores) {
            if(profesor.getCursoJefatura().equalsIgnoreCase(curso)) {
                System.out.println("Error: El curso '" + curso + "' ya está asignado al profesor " + profesor.getNombre());
                return;
            }
        }
        
        Profesor nuevoProfesor = new Profesor(nombre, rut, curso);
        
        if (nuevoProfesor.getCursoJefatura() != null) {
            this.listaProfesores.add(nuevoProfesor);
            System.out.println("El profesor " + nuevoProfesor.getNombre() + " se ha registrado con éxito como jefe del curso " + nuevoProfesor.getCursoJefatura() + ".");
        }
    }

     
private void cargarDatosIniciales() {

    LocalDate fecha_05_05_2025 = LocalDate.of(2025, 5, 5); 
    LocalDate fecha_06_05_2025 = LocalDate.of(2025, 5, 6); 


    Alumno a1 = new Alumno("Juan Perez", "Cuarto basico", "20.111.222-3");
    Alumno a2 = new Alumno("Ana Lopez", "Cuarto basico", "20.222.333-4");
    Alumno a3 = new Alumno("Pedro Soto", "Segundo medio", "19.888.777-6");
    Alumno a4 = new Alumno("Sofía Castro", "Segundo medio", "19.777.666-5");

    this.mapaAlumnos.put(a1.getRut(), a1);
    this.mapaAlumnos.put(a2.getRut(), a2);
    this.mapaAlumnos.put(a3.getRut(), a3);
    this.mapaAlumnos.put(a4.getRut(), a4);


    Profesor p1 = new Profesor("Carlos Araya", "11.111.111-1", "Cuarto basico");
    Profesor p2 = new Profesor("Constanza Reyes", "12.222.333-2", "Segundo medio");

    this.listaProfesores.add(p1);
    this.listaProfesores.add(p2);

    a1.registrarAsistencia(new Asistencia(fecha_05_05_2025, Asistencia.EstadoAsistencia.PRESENTE));
    a1.registrarAsistencia(new Asistencia(fecha_06_05_2025, Asistencia.EstadoAsistencia.AUSENTE));
    
    a2.registrarAsistencia(new Asistencia(fecha_05_05_2025, Asistencia.EstadoAsistencia.AUSENTE));
    a2.registrarAsistencia(new Asistencia(fecha_06_05_2025, Asistencia.EstadoAsistencia.PRESENTE));
    
    // Asistencias para el curso "Segundo medio"
    a3.registrarAsistencia(new Asistencia(fecha_05_05_2025, Asistencia.EstadoAsistencia.PRESENTE));
    a3.registrarAsistencia(new Asistencia(fecha_06_05_2025, Asistencia.EstadoAsistencia.PRESENTE));
    
    a4.registrarAsistencia(new Asistencia(fecha_05_05_2025, Asistencia.EstadoAsistencia.AUSENTE));
    a4.registrarAsistencia(new Asistencia(fecha_06_05_2025, Asistencia.EstadoAsistencia.AUSENTE));
    
    p1.justificarAusencia(this, a2, fecha_05_05_2025);
    
    System.out.println("Para probar los datos cargados, intente consultar la asistencia del curso 'Cuarto basico' en la fecha 05/05/2025.");
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
        return this.mapaAlumnos.get(rut);
    }
    // lector enteros(¿estamos autorizados para usar try/catch? esto es materia de exceptions)
    private int leerEntero()throws IOException{
        int opcion = Integer.parseInt(this.lectorConsola.readLine());
        return opcion;
    }
}
    
