/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectosia.controlador;

import com.mycompany.proyectosia.modelo.Profesor;
import com.mycompany.proyectosia.modelo.Asistencia;
import com.mycompany.proyectosia.modelo.Alumno;

import com.mycompany.proyectosia.vista.AgregarProfesorV;
import com.mycompany.proyectosia.vista.GestionAlumnos.BuscarAlumnoV;
import com.mycompany.proyectosia.vista.GestionAlumnos.EliminarAlumnoV;
import com.mycompany.proyectosia.vista.GestionAlumnosV;
import com.mycompany.proyectosia.vista.GestionAsistencia.EliminarAsistenciaP;
import com.mycompany.proyectosia.vista.GestionAsistencia.ModificarAsistenciaP;
import com.mycompany.proyectosia.vista.GestionAsistencia.RegistrarAsistenciaP;
import com.mycompany.proyectosia.vista.GestionAsistenciaV;
import com.mycompany.proyectosia.vista.MenuProfesoresV;
import com.mycompany.proyectosia.vista.gestionAlumnos.AgregarAlumnoV;
import com.mycompany.proyectosia.vista.MenuV;
import com.mycompany.proyectosia.vista.gestionAlumnos.ModificarAlumnoEditarP;
import com.mycompany.proyectosia.vista.gestionAlumnos.ModificarAlumnoRellenadoP;
import com.mycompany.proyectosia.vista.gestionAlumnos.ModificarAlumnoV;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author anton
 */
public class SistemaAsistenciaEscolar implements ActionListener{

    private BufferedReader lectorConsola;
    // colecciones
    private Map<String, Alumno> mapaAlumnos;
    private ArrayList<Profesor> listaProfesores;
    
    // ventanas
    private MenuV ventanaPrincipal;
    private MenuProfesoresV ventanaProfesores;
    private GestionAlumnosV ventanaAlumnos;
    private GestionAsistenciaV ventanaAsistencia;
    private AgregarProfesorV ventanaProfesorAgregar;
    
    // ventanas GestionAlumnosV
    private AgregarAlumnoV ventanaAlumnoAgregar;
    private ModificarAlumnoV ventanaAlumnoModificar;
    private EliminarAlumnoV ventanaAlumnoEliminar;
    
    // jPanels para refrescar ventanas
    private ModificarAlumnoRellenadoP panelRellenadoAlumno;
    private ModificarAlumnoEditarP panelEditarAlumno;
    
            
    
    public SistemaAsistenciaEscolar(){
        this.lectorConsola = new BufferedReader(new InputStreamReader(System.in));
        this.mapaAlumnos = new HashMap<>();
        this.listaProfesores = new ArrayList<>();
        
        // cargar datos para mostrar ejemplos
        cargarDatosIniciales();
    }
    
   
    public void iniciar() throws IOException {
        int opcionMenu;
        
        ventanaPrincipal = new MenuV();
        ventanaPrincipal.getjButtonMenuVAgregarProfesores().addActionListener(this);
        ventanaPrincipal.getjButtonMenuVModuloProfesores().addActionListener(this);
        ventanaPrincipal.getjButtonMenuVSalir().addActionListener(this);
        ventanaPrincipal.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventanaPrincipal.setVisible(true);
        ventanaPrincipal.setLocationRelativeTo(null);
        
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
                /* comentado (implementado en ventana)
                case 1:
                    insertarAlumno();
                    break;
                    */
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
                    //insertarProfesor();
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
    
    @Override
    public void actionPerformed(ActionEvent ae){

        
        // GESTION VENTANA PRINCIPAL
        if (ae.getSource() == ventanaPrincipal.getjButtonMenuVSalir()){
          ventanaPrincipal.dispose();
          return;
       }
        
        if (ae.getSource() == ventanaPrincipal.getjButtonMenuVModuloProfesores()){
            ventanaProfesores = new MenuProfesoresV();
            // sensibilizar botones
            ventanaProfesores.getjButtonMenuProfesoresVAlumnos().addActionListener(this);
            ventanaProfesores.getjButtonMenuProfesoresVAsistencia().addActionListener(this);
            ventanaProfesores.getjButtonMenuProfesoresVCancelar().addActionListener(this);
            // mostrar ventana
            ventanaProfesores.setVisible(true);
            ventanaProfesores.setLocationRelativeTo(null);
        }
        
        

        // GESTION VENTANA PROFESOR
        if (ventanaProfesores != null && ae.getSource() == ventanaProfesores.getjButtonMenuProfesoresVAlumnos()){
           ventanaAlumnos = new GestionAlumnosV();
           // sensibilizar botones
           ventanaAlumnos.getjComboBoxGestionAlumnosVOpciones().addActionListener(this);
           ventanaAlumnos.getjButtonGestionAlumnosVIr().addActionListener(this);
           ventanaAlumnos.getjButtonGestionAlumnosVCancelar().addActionListener(this);
           // mostrar ventana
           ventanaAlumnos.setVisible(true);
           ventanaAlumnos.setLocationRelativeTo(null);
           return;
       }
        
        // cerrar ventanaProfesores
        if (ventanaProfesores !=null && ae.getSource() == ventanaProfesores.getjButtonMenuProfesoresVCancelar()){
          ventanaProfesores.dispose();
          return;
       }
        
        // Gestion Agregar Profesor
        if (ae.getSource() == ventanaPrincipal.getjButtonMenuVAgregarProfesores()){
            ventanaProfesorAgregar = new AgregarProfesorV();
            ventanaProfesorAgregar.ocultarjLabelAgregarProfesorVExito();
            // sensibilizar botones
            ventanaProfesorAgregar.getjButtonAgregarProfesorVCrear().addActionListener(this);
            ventanaProfesorAgregar.getjButtonAgregarProfesorVCancelar().addActionListener(this);
            // mostrar ventana
            ventanaProfesorAgregar.setVisible(true);
            ventanaProfesorAgregar.setLocationRelativeTo(null);
        }
        
        // cerrar ventana Agregar Profesor
        if (ventanaProfesorAgregar !=null && ae.getSource() == ventanaProfesorAgregar.getjButtonAgregarProfesorVCancelar()){
          ventanaProfesorAgregar.dispose();
          return;
       }
        if (ventanaProfesorAgregar !=null && ae.getSource() == ventanaProfesorAgregar.getjButtonAgregarProfesorVCrear()){
          insertarProfesor(ventanaProfesorAgregar.getjTextFieldAgregarProfesorVNombre(), ventanaProfesorAgregar.getjTextFieldAgregarProfesorVCurso(), ventanaProfesorAgregar.getjTextFieldAgregarProfesorVRut());
          ventanaProfesorAgregar.mostrarjLabelAgregarProfesorVExito();
          
          return;
       }
        
        
        
        
        
        // GESTION VENTANA ALUMNOS
        if (ventanaAlumnos !=null && ae.getSource() == ventanaAlumnos.getjButtonGestionAlumnosVCancelar()){
          ventanaAlumnos.dispose();
          return;
       }
        
        // ventana gestion alumnos -> abrir agregar alumno
        if (ventanaAlumnos !=null && ae.getSource() == ventanaAlumnos.getjButtonGestionAlumnosVIr()){
          String opcionSeleccionada = (String) ventanaAlumnos.getjComboBoxGestionAlumnosVOpciones().getSelectedItem();
          if ("Agregar Alumno".equals(opcionSeleccionada)){
              ventanaAlumnoAgregar = new AgregarAlumnoV();
              ventanaAlumnoAgregar.ocultarjLabelAgregarAlumnoVExito();
              // sensibilizar botones
              ventanaAlumnoAgregar.getjButtonAgregarAlumnoVCancelar().addActionListener(this);
              ventanaAlumnoAgregar.getjButtonAgregarAlumnoVCrear().addActionListener(this);
              ventanaAlumnoAgregar.setVisible(true);
              ventanaAlumnoAgregar.setLocationRelativeTo(null);
              
          }
          else if("Modificar Alumno".equals(opcionSeleccionada)){
              ventanaAlumnoModificar = new ModificarAlumnoV();
              JPanel panelPrincipalAlumno = ventanaAlumnoModificar.getjPanelModificarAlumnoVPrincipal();
              
              panelRellenadoAlumno = new ModificarAlumnoRellenadoP();
              panelRellenadoAlumno.setSize(440, 275);
              panelRellenadoAlumno.setLocation(0, 0);
              
              panelPrincipalAlumno.removeAll();
              panelPrincipalAlumno.add(panelRellenadoAlumno, BorderLayout.CENTER);
              panelPrincipalAlumno.revalidate();
              panelPrincipalAlumno.repaint();
              
              
              panelRellenadoAlumno.ocultarjLabelModificarAlumnoRellenadoPError();
              // sensibilizar botones
              panelRellenadoAlumno.getjButtonModificarAlumnoRellenadoPBuscar().addActionListener(this);
              ventanaAlumnoModificar.getjButtonModificarAlumnoVCancelar().addActionListener(this);
              ventanaAlumnoModificar.setVisible(true);
              ventanaAlumnoModificar.setLocationRelativeTo(null);
          }
          else if("Eliminar Alumno".equals(opcionSeleccionada)){
              ventanaAlumnoEliminar = new EliminarAlumnoV();
              ventanaAlumnoEliminar.ocultarjLabelEliminarAlumnoVExito();
              ventanaAlumnoEliminar.ocultarjLabelEliminarAlumnoVNoExiste();
              
              ventanaAlumnoEliminar.getjButtonEliminarAlumnoVCancelar().addActionListener(this);
              ventanaAlumnoEliminar.getjButtonEliminarAlumnoVEliminar().addActionListener(this);
              ventanaAlumnoEliminar.setVisible(true);
              ventanaAlumnoEliminar.setLocationRelativeTo(null);
          }
          /*
          else if("Buscar Alumno".equals(opcionSeleccionada)){
              ventanaAlumnoBuscar = new BuscarAlumnoV();
          return;
*/
       }
        
        if (ventanaAlumnoAgregar !=null && ae.getSource() == ventanaAlumnoAgregar.getjButtonAgregarAlumnoVCrear()){
          if(insertarAlumno(ventanaAlumnoAgregar.getjTextFieldAgregarAlumnoVNombre(), ventanaAlumnoAgregar.getjTextFieldAgregarAlumnoVCurso(), ventanaAlumnoAgregar.getjTextFieldAgregarAlumnoVRut())){
              ventanaAlumnoAgregar.mostrarjLabelAgregarAlumnoVExito();
          }
          else{
              System.out.println("Error: Ya existe un alumno con ese RUT ");
          }
          
          return;
       }
        
        // cerrar ventanaAlumnoAgregar
        if (ventanaAlumnoAgregar !=null && ae.getSource() == ventanaAlumnoAgregar.getjButtonAgregarAlumnoVCancelar()){
          ventanaAlumnoAgregar.dispose();
          return;
       }
        
        if (panelRellenadoAlumno !=null && ae.getSource() == panelRellenadoAlumno.getjButtonModificarAlumnoRellenadoPBuscar()){
            Alumno alumnoBuscado = obtenerAlumnoPorRut(panelRellenadoAlumno.getjTextFieldModificarAlumnoRellenadoPRut());
                    
            if(alumnoBuscado != null){
                        
                panelEditarAlumno = new ModificarAlumnoEditarP();
                panelEditarAlumno.setSize(440, 275);
                panelEditarAlumno.setLocation(0, 0);

                ventanaAlumnoModificar.getjPanelModificarAlumnoVPrincipal().removeAll();
                ventanaAlumnoModificar.getjPanelModificarAlumnoVPrincipal().add(panelEditarAlumno, BorderLayout.CENTER);
                ventanaAlumnoModificar.getjPanelModificarAlumnoVPrincipal().revalidate();
                ventanaAlumnoModificar.getjPanelModificarAlumnoVPrincipal().repaint();
                
                panelEditarAlumno.getjButtonModificarAlumnoEditarPActualizar().addActionListener(this);
                
                panelEditarAlumno.ocultarjLabelModificarAlumnoEditarPExito();
                panelEditarAlumno.setjTextFieldModificarAlumnoEditarPNombre(alumnoBuscado.getNombre());
                panelEditarAlumno.setjTextFieldModificarAlumnoEditarPCurso(alumnoBuscado.getCurso());
                panelEditarAlumno.setjTextFieldModificarAlumnoEditarPRut(alumnoBuscado.getRut());
                
                
            }
            panelRellenadoAlumno.mostrarjLabelModificarAlumnoRellenadoPError();
            return;
        }
        if (panelEditarAlumno != null && ae.getSource() == panelEditarAlumno.getjButtonModificarAlumnoEditarPActualizar()){
            Alumno alumnoBuscado = obtenerAlumnoPorRut(panelRellenadoAlumno.getjTextFieldModificarAlumnoRellenadoPRut());
            if(modificarAlumno(panelEditarAlumno.getjTextFieldModificarAlumnoEditarPNombre(), panelEditarAlumno.getjTextFieldModificarAlumnoEditarPCurso(), panelEditarAlumno.getjTextFieldModificarAlumnoEditarPRut(), alumnoBuscado.getRut())){
                panelEditarAlumno.mostrarjLabelModificarAlumnoEditarPExito();
            }
            else{
                panelRellenadoAlumno.mostrarjLabelModificarAlumnoRellenadoPError();
            }
            return;
        }
        if (ventanaAlumnoModificar !=null && ae.getSource() == ventanaAlumnoModificar.getjButtonModificarAlumnoVCancelar()){
            ventanaAlumnoModificar.dispose();
            return;
        }
        
        //Elimina Alumno
        if (ventanaAlumnoEliminar !=null && ae.getSource() == ventanaAlumnoEliminar.getjButtonEliminarAlumnoVEliminar()){
          if(eliminarAlumno(ventanaAlumnoEliminar.getjTextFieldEliminarAlumnoVRut())){
              ventanaAlumnoEliminar.mostrarjLabelAgregarAlumnoVExito();
          }
          else{
              ventanaAlumnoEliminar.mostarjLabelEliminarAlumnoVNoExiste();
          }
          
          return;
        }
        
        //Cerrar Ventana Eliminar
        if (ventanaAlumnoEliminar !=null && ae.getSource() == ventanaAlumnoEliminar.getjButtonEliminarAlumnoVCancelar()){
          ventanaAlumnoEliminar.dispose();
          return;
        }
        
        if (ae.getSource() == ventanaProfesores.getjButtonMenuProfesoresVAsistencia()){
           ventanaAsistencia = new GestionAsistenciaV();
           // sensibilizar botones
           ventanaAsistencia.getjButtonGestionAsistenciaVCancelar().addActionListener(this);
           ventanaAsistencia.getjButtonGestionAsistenciaVIr().addActionListener(this);
           ventanaAsistencia.getjComboBoxGestionAsistenciaVSeleccion().addActionListener(this);
           // mostrar ventana
           ventanaAsistencia.setLocationRelativeTo(null);
           ventanaAsistencia.setVisible(true);
           return;
       }
        
        // cerrar ventanaAsistencia
        if (ventanaAsistencia !=null && ae.getSource() == ventanaAsistencia.getjButtonGestionAsistenciaVCancelar()){
          ventanaAsistencia.dispose();
          return;
       }
        // Acceder boton ir 
        if (ventanaAsistencia != null && ae.getSource() == ventanaAsistencia.getjButtonGestionAsistenciaVIr()) {

            String opcion = (String) ventanaAsistencia
                    .getjComboBoxGestionAsistenciaVSeleccion()
                    .getSelectedItem();

            switch (opcion) {
                case "Registrar Asistencia":
                    // abrir nueva ventana o lógica
                    RegistrarAsistenciaP reg = new RegistrarAsistenciaP();
                    reg.setVisible(true);
                    //reg.setLocationRelativeTo(null);
                    break;

                case "Modificar Asistencia":
                    ModificarAsistenciaP mod = new ModificarAsistenciaP();
                    mod.setVisible(true);
                    //mod.setLocationRelativeTo(null);
                    break;

                case "Eliminar Asistencia":
                    EliminarAsistenciaP eli = new EliminarAsistenciaP();
                    eli.setVisible(true);
                    //eli.setLocationRelativeTo(null);
                    break;
            }
            return;
        }
        

    }
    
    
    private boolean modificarAlumno(String nombre, String curso, String rut){
        if(!this.mapaAlumnos.containsKey(rut)){
            // hacer excepcion: la accion de la excepcion puede ser abrir una ventanaError que le pase por setText() el tipo de error, por ejemplo el prinln de esta linea
            System.out.println("Error: No existe ningun alumno con el RUT"+ rut);
            return false;
        }
        
        Alumno alumnoModificar = this.mapaAlumnos.get(rut);
        alumnoModificar.setNombre(nombre);
        alumnoModificar.setCurso(curso);
        alumnoModificar.setRut(rut);
        
        return true;
    }
    
    private boolean modificarAlumno(String nombre, String curso, String rut, String rutOriginal){
        if(!this.mapaAlumnos.containsKey(rutOriginal)){
            // hacer excepcion: la accion de la excepcion puede ser abrir una ventanaError que le pase por setText() el tipo de error, por ejemplo el prinln de esta linea
            System.out.println("Error: No existe ningun alumno con el RUT"+ rut);
            return false;
        }
        
        Alumno alumnoModificar = this.mapaAlumnos.get(rutOriginal);
        alumnoModificar.setNombre(nombre);
        alumnoModificar.setCurso(curso);
        alumnoModificar.setRut(rut);
        
        return true;
    }
    
    
    private boolean insertarAlumno(String nombre, String curso, String rut){

        if(this.mapaAlumnos.containsKey(rut)){
            // hacer excepcion: la accion de la excepcion puede ser abrir una ventanaError que le pase por setText() el tipo de error, por ejemplo el prinln de esta linea
            System.out.println("Error: Ya existe un alumno con el RUT " + rut);
            return false;
        }
        
        Alumno nuevoAlumno = new Alumno(nombre, curso, rut);
        
        this.mapaAlumnos.put(rut, nuevoAlumno);
        System.out.println("El alumno " + nuevoAlumno.getNombre() + " se ha creado con exito.");
        return true;
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
    
    private boolean eliminarAlumno(String rut) {
        if (rut == null || rut.trim().isEmpty()) {
            return false;
        }

        // Si no existe la clave, no hay a quién borrar
        if (!this.mapaAlumnos.containsKey(rut)) {
            return false;
        }

        Alumno eliminado = this.mapaAlumnos.remove(rut);
        return true;
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
    
    
    
    private void insertarProfesor(String nombre, String rut, String curso) {
        System.out.println("\n--- Nuevo Profesor ---");

        // Validación: No permitir RUTs de profesor duplicados
        for (Profesor profesor : listaProfesores) {
            if (profesor.getRut().equalsIgnoreCase(rut)) {
                System.out.println("Error: Ya existe un profesor con el RUT " + rut);
                return;
            }
        }

        // Validamos que un curso no tenga más de un profesor jefe
        for (Profesor profesor : listaProfesores) {
            if (profesor.getCursoJefatura().equalsIgnoreCase(curso)) {
                System.out.println("Error: El curso '" + curso + "' ya está asignado al profesor " + profesor.getNombre());
                return;
            }
        }

        Profesor nuevoProfesor = new Profesor(nombre, rut, curso);

        if (nuevoProfesor.getCursoJefatura() != null) {
            this.listaProfesores.add(nuevoProfesor);
            System.out.println("El profesor " + nuevoProfesor.getNombre() +
                    " se ha registrado con éxito como jefe del curso " +
                    nuevoProfesor.getCursoJefatura() + ".");
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
    
