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
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Reporte {
    protected final SistemaAsistenciaEscolar sistema;
    protected final String titulo;
    protected final LocalDate fechaGeneracion = LocalDate.now();
    protected final DateTimeFormatter F_VIS  = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    protected final DateTimeFormatter F_FILE = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final String LS = System.lineSeparator();

    public Reporte(SistemaAsistenciaEscolar sistema, String titulo) {
        this.sistema = sistema;
        this.titulo = titulo;
    }

    /** Versión por defecto (puedes dejarla así; las subclases la sobrescriben) */
    public Path generarReporte(Path dir) throws IOException {
        String contenido = "Sin contenido especifico." + LS;
        return escribir(dir, nombreArchivo(), contenido);
    }

    /** Helper común para escribir archivo */
    protected Path escribir(Path dir, String nombreArchivo, String contenido) throws IOException {
        Files.createDirectories(dir);
        Path out = dir.resolve(nombreArchivo);
        Files.write(out, contenido.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        return out;
    }

    /** Nombre de archivo por defecto (puedes sobreescribir en subclases) */
    protected String nombreArchivo() {
        String base = titulo.toLowerCase().replace(" ", "_");
        return base + "_" + fechaGeneracion.format(F_FILE) + ".txt";
    }

    /** Encabezado/pie opcionales si los quieres reutilizar */
    protected String encabezado() {
        return  "=========================" + LS +
                titulo + LS +
                "Generado: " + fechaGeneracion.format(F_VIS) + LS +
                "=========================" + LS;
    }
    protected String pie() { return "=========================" + LS; }
}