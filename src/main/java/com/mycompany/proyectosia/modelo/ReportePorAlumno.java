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

public class ReportePorAlumno extends Reporte {
    private final String rutAlumno;
    private final LocalDate desde;
    private final LocalDate hasta;
    private static final String LS = System.lineSeparator();

    public ReportePorAlumno(SistemaAsistenciaEscolar sistema, String rutAlumno,
                            LocalDate desde, LocalDate hasta) {
        super(sistema, "Reporte de asistencia por alumno");
        this.rutAlumno = rutAlumno;
        this.desde = desde;
        this.hasta = hasta;
    }
    

    public ReportePorAlumno(SistemaAsistenciaEscolar sistema, String rutAlumno) {
        // rango amplio para cubrir todo el historial sin pedir fechas en la UI
        this(sistema, rutAlumno,
             java.time.LocalDate.of(1900, 1, 1),
             java.time.LocalDate.of(3000, 1, 1));
    }


    @Override
    public Path generarReporte(Path dir) throws IOException {
        Alumno a = sistema.buscarAlumno(rutAlumno);
        if (a == null) {
            String msg = encabezado() + "Alumno con RUT " + rutAlumno + " no existe." + LS + pie();
            return escribir(dir, nombreArchivo(), msg);
        }

        LocalDate ini = (desde.isAfter(hasta)) ? hasta : desde;
        LocalDate fin = (desde.isAfter(hasta)) ? desde : hasta;

        java.util.List<Asistencia> lista = a.getAsistencia(ini, fin);
        lista.sort(Comparator.comparing(Asistencia::getFecha));

        long pres=0, aus=0, jus=0;
        StringBuilder sb = new StringBuilder();
        sb.append(encabezado());
        sb.append("Alumno: ").append(a.getNombre())
          .append(" (").append(a.getRut()).append(") - Curso: ").append(a.getCurso()).append(LS)
          .append("Periodo: ").append(ini.format(F_VIS)).append(" a ").append(fin.format(F_VIS)).append(LS).append(LS)
          .append("Fecha         Estado").append(LS);

        for (Asistencia x : lista) {
            sb.append(String.format("%-13s %s%n", x.getFecha().format(F_VIS), x.getEstado()));
            switch (x.getEstado()) {
                case PRESENTE: pres++; break;
                case AUSENTE:  aus++;  break;
                case JUSTIFICADO: jus++; break;
            }
        }

        sb.append(LS)
          .append("Totales -> Presentes: ").append(pres)
          .append(" | Ausentes: ").append(aus)
          .append(" | Justificados: ").append(jus).append(LS)
          .append(pie());

        String nombre = "reporte_alumno_" +
                rutAlumno.replace(".", "").replace("-", "") + "_" +
                fechaGeneracion.format(F_FILE) + ".txt";

        return escribir(dir, nombre, sb.toString());
    }

    /** Comodín opcional: genera en carpeta 'reports/' */
    public Path generarReporte() throws IOException {
        return generarReporte(Paths.get("reports"));
    }
}
