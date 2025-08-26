package com.mycompany.proyectosia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SistemaAsistenciaEscolar {

    // Atributos privados 
    private ArrayList listaAlumnos;          
    private BufferedReader lectorConsola;

    // Constructor
    public SistemaAsistenciaEscolar() {
        this.listaAlumnos = new ArrayList();
        this.lectorConsola = new BufferedReader(new InputStreamReader(System.in));
    }

    // Getters/Setters 
    public ArrayList getListaAlumnos() { return listaAlumnos; }
    public void setListaAlumnos(ArrayList listaAlumnos) { this.listaAlumnos = listaAlumnos; }
    public BufferedReader getLectorConsola() { return lectorConsola; }
    public void setLectorConsola(BufferedReader lectorConsola) { this.lectorConsola = lectorConsola; }

    // Menú principal 
    public void iniciar() throws IOException {
        int opcionMenu;
        do {
            System.out.println("\n=== Sistema de Asistencia ===");
            System.out.println("1. Insertar alumno");
            System.out.println("2. Mostrar alumnos");
            System.out.println("3. Salir");
            System.out.print("Elige una opcion: ");

            opcionMenu = leerEntero(); // <- ahora validado

            switch (opcionMenu) {
                case 1:
                    this.insertarAlumno();
                    break;
                case 2:
                    this.mostrarAlumnos();
                    break;
                case 3:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opcionMenu != 3);
    }

    // Inserción: aquí se leen STRINGS (nombre/curso)
    private void insertarAlumno() throws IOException {
        System.out.println("\n--- Nuevo Alumno ---");
        System.out.print("Ingrese nombre completo del nuevo alumno: ");
        String nombre = this.lectorConsola.readLine();

        System.out.print("Ingrese curso del nuevo alumno: ");
        String curso = this.lectorConsola.readLine();

        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("Nombre vacío. Operacion cancelada.");
            return;
        }
        if (curso == null || curso.trim().isEmpty()) {
            System.out.println("Curso vacío. Operacion cancelada.");
            return;
        }

        if (this.existeAlumno(nombre.trim())) {
            System.out.println("El alumno ingresado ya existe en el sistema.");
            return;
        }

        Alumno nuevoAlumno = new Alumno(nombre.trim(), curso.trim());
        this.listaAlumnos.add(nuevoAlumno);
        System.out.println("Alumno agregado: " + nuevoAlumno.getNombre());
    }

    private void mostrarAlumnos() {
        System.out.println("\n--- Lista de Alumnos ---");
        if (this.listaAlumnos.isEmpty()) {
            System.out.println("No hay alumnos registrados.");
        } else {
            for (Object obj : this.listaAlumnos) {
                Alumno alumno = (Alumno) obj; // casting manual desde ArrayList raw
                System.out.println("Nombre: " + alumno.getNombre() + ", Curso: " + alumno.getCurso());
            }
        }
    }

    private boolean existeAlumno(String nombre) {
        for (Object obj : this.listaAlumnos) {
            Alumno a = (Alumno) obj;
            if (nombre.equalsIgnoreCase(a.getNombre())) {
                return true;
            }
        }
        return false;
    }

    // Lector de enteros robusto para el MENÚ
    private int leerEntero() throws IOException {
        while (true) {
            String linea = this.lectorConsola.readLine();
            if (linea == null) {
                // EOF (Ctrl+D/Ctrl+Z): salir del sistema
                return 3; // opción Salir
            }
            linea = linea.trim();
            if (linea.isEmpty()) {
                System.out.print("No ingresaste nada. Intenta de nuevo: ");
                continue;
            }
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.print("Debes ingresar un numero. Intenta de nuevo: ");
            }
        }
    }
}
