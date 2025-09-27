/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectosia.vista.modelosTablas;

import com.mycompany.proyectosia.modelo.Alumno;
import com.mycompany.proyectosia.modelo.Asistencia;
import java.time.LocalDate;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class AsistenciaTableModel extends AbstractTableModel{

    private final List<Alumno> listaAlumnos;
    private final LocalDate fecha;
    private final String[] nombresColumnas = {"Nombre", "RUT", "Estado asistencia"};

    public AsistenciaTableModel(List<Alumno> listaAlumnos, LocalDate fecha) {
        this.listaAlumnos = listaAlumnos;
        this.fecha = fecha;
    }

    @Override
    public int getRowCount() {
        return listaAlumnos.size();
    }

    @Override
    public int getColumnCount() {
        return nombresColumnas.length;
    }

    @Override
    public String getColumnName(int column) {
        return nombresColumnas[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        // para la columna 3 un tipo Enum
        if (columnIndex == 2) {
            return Asistencia.EstadoAsistencia.class;
        }
        return String.class;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Alumno alumno = listaAlumnos.get(rowIndex);
        Asistencia asistencia = alumno.getAsistencia(fecha);

        switch (columnIndex) {
            case 0: 
                return alumno.getNombre();
            case 1: 
                return alumno.getRut();
            case 2: 
                return (asistencia != null) ? asistencia.getEstado() : Asistencia.EstadoAsistencia.AUSENTE;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // para poner editable unicamente el Estado asistencia
        return columnIndex == 2;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 2) {
            Alumno alumno = listaAlumnos.get(rowIndex);
            Asistencia.EstadoAsistencia nuevoEstado = (Asistencia.EstadoAsistencia) aValue;

            Asistencia asistenciaExistente = alumno.getAsistencia(fecha);

            if (asistenciaExistente != null) {
                asistenciaExistente.setEstado(nuevoEstado);
            } else {
                Asistencia nuevaAsistencia = new Asistencia(fecha, nuevoEstado);
                alumno.registrarAsistencia(nuevaAsistencia);
            }
            
            System.out.println("Asistencia actualizada para " + alumno.getNombre() + ": " + nuevoEstado);
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }
}
    

