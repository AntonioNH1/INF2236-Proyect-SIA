/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectosia;

import java.io.*;

/**
 *
 * @author anton
 */
public class Main {

    // Colección genérica sin tipo específico
    static ArrayList listaAlumnos = new ArrayList();

    static BufferedReader lectorConsola = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        int opcionMenu;

        do {
            System.out.println("\n=== Sistema de Asistencia ===");
            System.out.println("1. Insertar alumno");
            System.out.println("2. Mostrar alumnos");
            System.out.println("3. Salir");
            System.out.print("Elige una opcion: ");

            opcionMenu = leerEnteroSeguro();

            switch (opcionMenu) {
                case 1:
                    insertarAlumno();
                    break;
                case 2:
                    mostrarAlumnos();
                    break;
                case 3:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opcionMenu != 3);
    }
