/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectosia.modelo;

/**
 *
 * @author Bruno Medina
 */

import com.mycompany.proyectosia.controlador.SistemaAsistenciaEscolar;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class ReportePorCurso extends Reporte {
    private final String curso;
    private final LocalDate desde;
    private final LocalDate hasta;
    private static final String LS = System.lineSeparator();

    // Rango explícito
    public ReportePorCurso(SistemaAsistenciaEscolar sistema, String curso,
                           LocalDate desde, LocalDate hasta) {
        super(sistema, "Reporte de asistencia por curso");
        this.curso = curso;
        this.desde = desde;
        this.hasta = hasta;
    }

    // Comodín: todo el historial
    public ReportePorCurso(SistemaAsistenciaEscolar sistema, String curso) {
        this(sistema, curso,
             LocalDate.of(1900, 1, 1),
             LocalDate.of(3000, 1, 1));
    }

    @Override
    public Path generarReporte(Path dir) throws IOException {
        List<Alumno> alumnos = sistema.obtenerAlumnosPorCurso(curso);
        if (alumnos == null || alumnos.isEmpty()) {
            String msg = encabezado() + "No hay alumnos para el curso: " + curso + LS + pie();
            return escribir(dir, nombreArchivo(), msg);
        }

        LocalDate ini = (desde.isAfter(hasta)) ? hasta : desde;
        LocalDate fin = (desde.isAfter(hasta)) ? desde : hasta;

        alumnos.sort(Comparator.comparing(Alumno::getNombre));

        long tPres = 0, tAus = 0, tJus = 0;

        StringBuilder sb = new StringBuilder();
        sb.append(encabezado());
        sb.append("Curso: ").append(curso).append(LS)
          .append("Periodo: ").append(ini.format(F_VIS))
          .append(" a ").append(fin.format(F_VIS)).append(LS).append(LS)
          .append(String.format("%-12s %-25s %s%n", "RUT", "Alumno", "P/A/J"));

        for (Alumno a : alumnos) {
            long p = 0, au = 0, ju = 0;

            // Cuenta asistencias del alumno dentro del rango
            List<Asistencia> lista = a.getAsistencia(ini, fin);
            for (Asistencia x : lista) {
                switch (x.getEstado()) {
                    case PRESENTE:    p++; break;
                    case AUSENTE:     au++; break;
                    case JUSTIFICADO: ju++; break;
                }
            }

            tPres += p; tAus += au; tJus += ju;
            sb.append(String.format("%-12s %-25s %d/%d/%d%n",
                    a.getRut(), a.getNombre(), p, au, ju));
        }

        sb.append(LS)
          .append("Totales del curso -> Presentes: ").append(tPres)
          .append(" | Ausentes: ").append(tAus)
          .append(" | Justificados: ").append(tJus).append(LS)
          .append(pie());

        return escribir(dir, nombreArchivo(), sb.toString());
    }

    // Genera por defecto en carpeta 'reports/'
    public Path generarReporte() throws IOException {
        return generarReporte(Paths.get("reports"));
    }

    @Override
    protected String nombreArchivo() {
        String base = curso == null ? "curso" : curso.trim()
                .toLowerCase()
                .replace(" ", "_")
                .replaceAll("[^a-z0-9_]", "_");
        return "reporte_curso_" + base + "_" + fechaGeneracion.format(F_FILE) + ".txt";
    }
}

