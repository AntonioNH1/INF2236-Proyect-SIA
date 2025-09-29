/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectosia.controlador;

import com.mycompany.proyectosia.excepciones.EntidadDuplicadaException;
import com.mycompany.proyectosia.excepciones.EntidadNoEncontradaException;
import com.mycompany.proyectosia.vista.GenerarReportes.GenerarAlumnoV;
import com.mycompany.proyectosia.vista.GenerarReportes.GenerarCursoV;
import com.mycompany.proyectosia.modelo.Profesor;
import com.mycompany.proyectosia.modelo.Asistencia;
import com.mycompany.proyectosia.modelo.Alumno;
import com.mycompany.proyectosia.modelo.ReportePorAlumno;
import com.mycompany.proyectosia.modelo.ReportePorCurso;
import com.mycompany.proyectosia.vista.FuncionalidadAsistenciaCronica.AlertaInasistenciaV;
import com.mycompany.proyectosia.vista.GestionProfesor.AgregarProfesorV;
import com.mycompany.proyectosia.vista.GestionProfesor.EliminarProfesorV;
import com.mycompany.proyectosia.vista.GestionAlumnos.EliminarAlumnoV;
import com.mycompany.proyectosia.vista.GestionAlumnosV;
import com.mycompany.proyectosia.vista.GestionAsistencia.GenerarReportesV;
import com.mycompany.proyectosia.vista.GestionAsistencia.GestionTotalAsistenciaV;
import com.mycompany.proyectosia.vista.GestionAsistencia.ModificarFechaAsistenciaEditarP;
import com.mycompany.proyectosia.vista.GestionAsistencia.ModificarFechaAsistenciaRellenadoP;
import com.mycompany.proyectosia.vista.GestionAsistencia.ModificarFechaAsistenciaV;
import com.mycompany.proyectosia.vista.GestionAsistenciaV;
import com.mycompany.proyectosia.vista.MenuAsistenciaV;
import com.mycompany.proyectosia.vista.MenuProfesorV;
import com.mycompany.proyectosia.vista.gestionAlumnos.AgregarAlumnoV;
import com.mycompany.proyectosia.vista.MenuV;
import com.mycompany.proyectosia.vista.GestionProfesor.ModificarProfesorV;
import com.mycompany.proyectosia.vista.InicioSesion.InicioSesionAdministradorP;
import com.mycompany.proyectosia.vista.InicioSesion.InicioSesionProfesorP;
import com.mycompany.proyectosia.vista.InicioSesion.InicioSesionV;
import com.mycompany.proyectosia.vista.gestionAlumnos.ModificarAlumnoEditarP;
import com.mycompany.proyectosia.vista.gestionAlumnos.ModificarAlumnoRellenadoP;
import com.mycompany.proyectosia.vista.gestionAlumnos.ModificarAlumnoV;

import com.mycompany.proyectosia.vista.modelosTablas.AsistenciaTableModel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

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
    private MenuAsistenciaV ventanaAsistenciaMenu;
    private GestionAlumnosV ventanaAlumnos;
    private GestionAsistenciaV ventanaAsistencia;
    private AgregarProfesorV ventanaProfesorAgregar;
    private MenuProfesorV ventanaMenuProfesor;
    private EliminarProfesorV ventanaEliminarProfesor;
    private ModificarProfesorV ventanaModificarProfesor;
    private AlertaInasistenciaV ventanaAlertaInasistencia;
    
    // ventanas InicioSesionV
    private InicioSesionV ventanaInicioSesion;
    private InicioSesionAdministradorP panelLoginAdmin;
    private InicioSesionProfesorP panelLoginProfesor;
    
    // Estado de la sesión actual
    private Profesor profesorLogueado = null;
    private boolean esAdminLogueado = false;
    private static final String ADMIN_PASSWORD = "admin";
    
    // Almacena la acción a ejecutar después de un login exitoso
    private Runnable accionPostLogin;
    
    // ventanas GestionAlumnosV
    private AgregarAlumnoV ventanaAlumnoAgregar;
    private ModificarAlumnoV ventanaAlumnoModificar;
    private EliminarAlumnoV ventanaAlumnoEliminar;
    
    // ventanas GestionAsistenciaV
    private GestionTotalAsistenciaV ventanaTablaAsistencia;
    private ModificarFechaAsistenciaV ventanaAsistenciaModificar;
    
    // jPanels para refrescar ventanas
    private ModificarAlumnoRellenadoP panelRellenadoAlumno;
    private ModificarAlumnoEditarP panelEditarAlumno;
    private ModificarFechaAsistenciaRellenadoP panelRellenadoFechaAsistencia;
    private ModificarFechaAsistenciaEditarP panelEditarFechaAsistencia;
    
    // ventanas Reportes
    private GenerarReportesV ventanaGenerarReportes;
    private GenerarAlumnoV ventanaGenerarAlumno;
    private GenerarCursoV ventanaGenerarCurso;
    
    private static final Path DATA_DIR = Paths.get("data");
    private static final Path F_ALUMNOS = DATA_DIR.resolve("alumnos.txt");
    private static final Path F_PROFESORES = DATA_DIR.resolve("profesores.txt");
    private static final Path F_ASISTENCIAS = DATA_DIR.resolve("asistencias.txt");
    private static final DateTimeFormatter FMT = DateTimeFormatter.ISO_LOCAL_DATE;
            
    
    public SistemaAsistenciaEscolar(){
        this.lectorConsola = new BufferedReader(new InputStreamReader(System.in));
        this.mapaAlumnos = new HashMap<>();
        this.listaProfesores = new ArrayList<>();
        cargarDatosIniciales();
    }
    
    public void cargarDatosDesdeTxt() {
        try {
            Files.createDirectories(DATA_DIR);
            cargarAlumnos();
            cargarProfesores();
            cargarAsistencias();
            System.out.println("Datos cargados desde TXT.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se pudieron leer los archivos de datos (TXT): \n" + e.getMessage(), "Error de Carga de Datos", JOptionPane.ERROR_MESSAGE);
            cargarDatosIniciales(); 
        }
    }
    private void cargarAlumnos() throws IOException {
        if (!Files.exists(F_ALUMNOS)) return;
        try (BufferedReader br = Files.newBufferedReader(F_ALUMNOS)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] t = line.split(";", -1);
                if (t.length < 3) { System.err.println("Alumno mal formado: " + line); continue; }
                String rut = t[0].trim(), nombre = t[1].trim(), curso = t[2].trim();
                Alumno a = new Alumno(nombre, curso, rut);
                this.mapaAlumnos.put(a.getRut(), a);
            }
        }
    }

    private void cargarProfesores() throws IOException {
        if (!Files.exists(F_PROFESORES)) return;
        try (BufferedReader br = Files.newBufferedReader(F_PROFESORES)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] t = line.split(";", -1);
                if (t.length < 3) { System.err.println("Profesor mal formado: " + line); continue; }
                String rut = t[0].trim(), nombre = t[1].trim(), curso = t[2].trim();
                Profesor p = new Profesor(nombre, rut, curso);
                this.listaProfesores.add(p);
            }
        }
    }

    private void cargarAsistencias() throws IOException {
        if (!Files.exists(F_ASISTENCIAS)) return;

        try (BufferedReader br = Files.newBufferedReader(F_ASISTENCIAS)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                if (line.startsWith("#") || line.toLowerCase().startsWith("rut;")) continue;

                String[] t = line.split(";", -1);
                if (t.length < 3) {
                    System.err.println("Asistencia mal formada: " + line);
                    continue;
                }

                String rutAlu = t[0].trim();
                String sFecha = t[1].trim();
                String sEstado = t[2].trim();

                Alumno a = this.mapaAlumnos.get(rutAlu);
                if (a == null) {
                    System.err.println("Alumno no existe para asistencia: " + rutAlu);
                    continue;
                }

                LocalDate fecha;
                try {
                    fecha = LocalDate.parse(sFecha, FMT);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error de conversion de fecha ", "Error en al conversion de fecha", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                Asistencia.EstadoAsistencia estado;
                try {
                    estado = Asistencia.EstadoAsistencia.valueOf(sEstado);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Estado invalido (" + sEstado + ") en: " + line, "Error de estado invalido", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                Asistencia existente = a.getAsistencia(fecha);
                if (existente == null) {
                    a.registrarAsistencia(new Asistencia(fecha, estado));
                } else {
                    existente.setEstado(estado);
                }

                if (t.length >= 4 && !t[3].trim().isEmpty()
                        && estado == Asistencia.EstadoAsistencia.JUSTIFICADO) {
                    String rutProf = t[3].trim();
                    Profesor prof = this.listaProfesores.stream()
                            .filter(p -> rutProf.equalsIgnoreCase(p.getRut()))
                            .findFirst().orElse(null);
                    if (prof != null) {
                    }
                }
            }
        }
    }
    public void cargarDatosIniciales() {
        try {
            Files.createDirectories(DATA_DIR);
            if (Files.exists(F_ALUMNOS) && Files.exists(F_PROFESORES) && Files.exists(F_ASISTENCIAS)) {
                cargarDatosDesdeTxt();
            } else {
                cargarDatosEjemplo();
                guardarDatosEnTxt();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error en carga inicial: " + e.getMessage(), "Error de carga inicial", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarDatosEjemplo() {

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

        a3.registrarAsistencia(new Asistencia(fecha_05_05_2025, Asistencia.EstadoAsistencia.PRESENTE));
        a3.registrarAsistencia(new Asistencia(fecha_06_05_2025, Asistencia.EstadoAsistencia.PRESENTE));

        a4.registrarAsistencia(new Asistencia(fecha_05_05_2025, Asistencia.EstadoAsistencia.AUSENTE));
        a4.registrarAsistencia(new Asistencia(fecha_06_05_2025, Asistencia.EstadoAsistencia.AUSENTE));

        p1.justificarAusencia(this, a2, fecha_05_05_2025);

        System.out.println("Para probar los datos cargados, intente consultar la asistencia del curso 'Cuarto basico' en la fecha 05/05/2025.");
    }
    
    public void guardarDatosEnTxt() throws IOException {
        Files.createDirectories(DATA_DIR);
        guardarAlumnos();
        guardarAsistencias();
        guardarProfesores();
        System.out.println("Datos guardados en TXT.");
    }

    private void guardarAlumnos() throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(F_ALUMNOS)) {
            for (Alumno a : this.mapaAlumnos.values()) {
                bw.write(a.getRut() + ";" + a.getNombre() + ";" + a.getCurso());
                bw.newLine();
            }
        }
    }

    private void guardarAsistencias() throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(F_ASISTENCIAS)) {
            DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;

            java.util.List<Alumno> alumnos = new java.util.ArrayList<>(this.mapaAlumnos.values());
            alumnos.sort(java.util.Comparator.comparing(Alumno::getRut));

            for (Alumno a : alumnos) {
                java.util.List<Asistencia> historial =
                        a.getAsistencia(java.time.LocalDate.MIN, java.time.LocalDate.MAX);

                historial.sort(java.util.Comparator.comparing(Asistencia::getFecha));

                for (Asistencia as : historial) {
                    if (as == null || as.getFecha() == null || as.getEstado() == null) continue;
                    bw.write(a.getRut() + ";" +
                             as.getFecha().format(fmt) + ";" +
                             as.getEstado().name());
                    bw.newLine();
                }
            }
        }
    }

    private void guardarProfesores() throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(F_PROFESORES)) {
            for (Profesor p : this.listaProfesores) {
                bw.write(p.getRut() + ";" + p.getNombre() + ";" + p.getCursoJefatura());
                bw.newLine();
            }
        }
    }
   
    public void iniciar() throws IOException {
        int opcionMenu;
        
        ventanaPrincipal = new MenuV();
        ventanaPrincipal.getjButtonMenuVModuloProfesor().addActionListener(this);
        ventanaPrincipal.getjButtonMenuVModuloAsistencia().addActionListener(this);
        ventanaPrincipal.getjButtonMenuVGestionarAlumnos().addActionListener(this);
        ventanaPrincipal.getjButtonMenuVSalir().addActionListener(this);
        ventanaPrincipal.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        ventanaPrincipal.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        ventanaPrincipal.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                cerrarAplicacion();
            }
        });
        
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
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == ventanaPrincipal.getjButtonMenuVSalir()) {
            cerrarAplicacion();
            return;
        }
        
        if (ae.getSource() == ventanaPrincipal.getjButtonMenuVSalir()) {
            ventanaPrincipal.dispose();
            return;
        }

        if (ae.getSource() == ventanaPrincipal.getjButtonMenuVGestionarAlumnos()) {
            mostrarLoginAdmin(() -> {
                ventanaAlumnos = new GestionAlumnosV();
                ventanaAlumnos.getjButtonGestionAlumnosVIr().addActionListener(this);
                ventanaAlumnos.getjButtonGestionAlumnosVCancelar().addActionListener(this);
                ventanaAlumnos.setVisible(true);
                ventanaAlumnos.setLocationRelativeTo(null);
            });
            return;
        }

        if (ae.getSource() == ventanaPrincipal.getjButtonMenuVModuloAsistencia()) {
            mostrarLoginProfesor(() -> {
                ventanaAsistenciaMenu = new MenuAsistenciaV();
                ventanaAsistenciaMenu.getjButtonMenuAsistenciaVAsistencia().addActionListener(this);
                ventanaAsistenciaMenu.getjButtonGenerarReportesVir().addActionListener(this);
                ventanaAsistenciaMenu.getjButtonMenuAsistenciaVAlertaAsistencia().addActionListener(this);
                ventanaAsistenciaMenu.getjButtonMenuAsistenciaVCancelar().addActionListener(this);
                ventanaAsistenciaMenu.setVisible(true);
                ventanaAsistenciaMenu.setLocationRelativeTo(null);
            });
            return;
        }

        if (ae.getSource() == ventanaPrincipal.getjButtonMenuVModuloProfesor()) {
            mostrarLoginAdmin(() -> {
                ventanaMenuProfesor = new MenuProfesorV();
                ventanaMenuProfesor.getjButtonMenuProfesorVAgregar().addActionListener(this);
                ventanaMenuProfesor.getjButtonMenuProfesorVEliminar().addActionListener(this);
                ventanaMenuProfesor.getjButtonMenuProfesorVModificar().addActionListener(this);
                ventanaMenuProfesor.getjButtonMenuProfesorVCancelar().addActionListener(this);
                ventanaMenuProfesor.setVisible(true);
                ventanaMenuProfesor.setLocationRelativeTo(null);
            });
            return;
        }
        
        
        if (panelLoginAdmin != null && ae.getSource() == panelLoginAdmin.getjButtonIngresarAdmin()) {
            String password = new String(panelLoginAdmin.getjPasswordFieldAdmin().getPassword());
            if (ADMIN_PASSWORD.equals(password)) {
                esAdminLogueado = true; 
                profesorLogueado = null; 
                System.out.println("Login de Administrador exitoso.");
                ventanaInicioSesion.dispose();
                if (accionPostLogin != null) {
                    accionPostLogin.run(); 
                }
            } else {
                panelLoginAdmin.mostrarLabelError();
            }
            return;
        }
        
        if (panelLoginProfesor != null && ae.getSource() == panelLoginProfesor.getjButtonIngresarProfesor()) {
            String rut = panelLoginProfesor.getjTextFieldRutProfesor().getText();

            try {
                Profesor profesor = buscarProfesorPorRut(rut);
                profesorLogueado = profesor; 
                esAdminLogueado = false;
                System.out.println("Login de Profesor exitoso: " + profesor.getNombre());
                ventanaInicioSesion.dispose();
                if (accionPostLogin != null) {
                    accionPostLogin.run(); 
                }
            } catch (EntidadNoEncontradaException e) {
                panelLoginProfesor.mostrarLabelError();
            }
            return;
        }

        if (ventanaInicioSesion != null && ae.getSource() == ventanaInicioSesion.getjButtonCancelar()) {
            ventanaInicioSesion.dispose();
            logout();
            return;
        }

        if (ventanaAlumnos != null && ae.getSource() == ventanaAlumnos.getjButtonGestionAlumnosVCancelar()) {
            ventanaAlumnos.dispose();
            return;
        }

        if (ventanaAlumnos != null && ae.getSource() == ventanaAlumnos.getjButtonGestionAlumnosVIr()) {
            String opcionSeleccionada = (String) ventanaAlumnos.getjComboBoxGestionAlumnosVOpciones().getSelectedItem();
            if ("Agregar Alumno".equals(opcionSeleccionada)) {
                ventanaAlumnoAgregar = new AgregarAlumnoV();
                ventanaAlumnoAgregar.ocultarjLabelAgregarAlumnoVExito();
                ventanaAlumnoAgregar.getjButtonAgregarAlumnoVCancelar().addActionListener(this);
                ventanaAlumnoAgregar.getjButtonAgregarAlumnoVCrear().addActionListener(this);
                ventanaAlumnoAgregar.setVisible(true);
                ventanaAlumnoAgregar.setLocationRelativeTo(null);

            } else if ("Modificar Alumno".equals(opcionSeleccionada)) {
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
                panelRellenadoAlumno.getjButtonModificarAlumnoRellenadoPBuscar().addActionListener(this);
                ventanaAlumnoModificar.getjButtonModificarAlumnoVCancelar().addActionListener(this);
                ventanaAlumnoModificar.setVisible(true);
                ventanaAlumnoModificar.setLocationRelativeTo(null);

            } else if ("Eliminar Alumno".equals(opcionSeleccionada)) {
                ventanaAlumnoEliminar = new EliminarAlumnoV();
                ventanaAlumnoEliminar.ocultarjLabelEliminarAlumnoVExito();
                ventanaAlumnoEliminar.ocultarjLabelEliminarAlumnoVNoExiste();

                ventanaAlumnoEliminar.getjButtonEliminarAlumnoVCancelar().addActionListener(this);
                ventanaAlumnoEliminar.getjButtonEliminarAlumnoVEliminar().addActionListener(this);
                ventanaAlumnoEliminar.setVisible(true);
                ventanaAlumnoEliminar.setLocationRelativeTo(null);
            }
            return;
        }

        if (ventanaAlumnoAgregar != null && ae.getSource() == ventanaAlumnoAgregar.getjButtonAgregarAlumnoVCrear()) {
            String cursoSeleccionado = (String) ventanaAlumnoAgregar.getjComboBoxAgregarAlumnoVCurso().getSelectedItem();

            try {
                String nombre = ventanaAlumnoAgregar.getjTextFieldAgregarAlumnoVNombre();
                String rut = ventanaAlumnoAgregar.getjTextFieldAgregarAlumnoVRut();
                insertarAlumno(nombre, cursoSeleccionado, rut);
                ventanaAlumnoAgregar.mostrarjLabelAgregarAlumnoVExito();
            } catch (EntidadDuplicadaException e) {
                JOptionPane.showMessageDialog(ventanaAlumnoAgregar, 
                    e.getMessage(), 
                    "Error de Duplicado", 
                    JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        if (ventanaAlumnoAgregar != null && ae.getSource() == ventanaAlumnoAgregar.getjButtonAgregarAlumnoVCancelar()) {
            ventanaAlumnoAgregar.dispose();
            return;
        }

        if (panelRellenadoAlumno != null && ae.getSource() == panelRellenadoAlumno.getjButtonModificarAlumnoRellenadoPBuscar()) {
            Alumno alumnoBuscado = obtenerAlumnoPorRut(panelRellenadoAlumno.getjTextFieldModificarAlumnoRellenadoPRut());

            if (alumnoBuscado != null) {
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
                panelEditarAlumno.getjComboBoxModificarAlumnoEditarPCurso().setSelectedItem(alumnoBuscado.getCurso());
                panelEditarAlumno.setjTextFieldModificarAlumnoEditarPRut(alumnoBuscado.getRut());
                return;
            }
            panelRellenadoAlumno.mostrarjLabelModificarAlumnoRellenadoPError();
            return;
        }

        if (panelEditarAlumno != null && ae.getSource() == panelEditarAlumno.getjButtonModificarAlumnoEditarPActualizar()) {
            Alumno alumnoBuscado = obtenerAlumnoPorRut(panelRellenadoAlumno.getjTextFieldModificarAlumnoRellenadoPRut());
            String nuevoCurso = (String) panelEditarAlumno.getjComboBoxModificarAlumnoEditarPCurso().getSelectedItem();
            try {
                modificarAlumno(panelEditarAlumno.getjTextFieldModificarAlumnoEditarPNombre(), nuevoCurso, panelEditarAlumno.getjTextFieldModificarAlumnoEditarPRut(), alumnoBuscado.getRut());
                panelEditarAlumno.mostrarjLabelModificarAlumnoEditarPExito();
            } catch (EntidadNoEncontradaException e) {
                panelRellenadoAlumno.mostrarjLabelModificarAlumnoRellenadoPError();
            }
            return;
        }

        if (ventanaAlumnoModificar != null && ae.getSource() == ventanaAlumnoModificar.getjButtonModificarAlumnoVCancelar()) {
            ventanaAlumnoModificar.dispose();
            return;
        }

        if (ventanaAlumnoEliminar != null && ae.getSource() == ventanaAlumnoEliminar.getjButtonEliminarAlumnoVEliminar()) {
            try {
                String rut = ventanaAlumnoEliminar.getjTextFieldEliminarAlumnoVRut();
                eliminarAlumno(rut);
                ventanaAlumnoEliminar.mostrarjLabelAgregarAlumnoVExito();
            } catch (EntidadNoEncontradaException e) {
                ventanaAlumnoEliminar.mostarjLabelEliminarAlumnoVNoExiste();
            }
            return;
        }

        if (ventanaAlumnoEliminar != null && ae.getSource() == ventanaAlumnoEliminar.getjButtonEliminarAlumnoVCancelar()) {
            ventanaAlumnoEliminar.dispose();
            return;
        }

        if (ventanaAsistenciaMenu != null && ae.getSource() == ventanaAsistenciaMenu.getjButtonMenuAsistenciaVCancelar()) {
            ventanaAsistenciaMenu.dispose();
            logout();
            return;
        }

        if (ventanaAsistenciaMenu != null && ae.getSource() == ventanaAsistenciaMenu.getjButtonMenuAsistenciaVAsistencia()) {
            ventanaAsistencia = new GestionAsistenciaV();
            ventanaAsistencia.getjButtonGestionAsistenciaVCancelar().addActionListener(this);
            ventanaAsistencia.getjButtonGestionAsistenciaVGestionPorCurso().addActionListener(this);
            ventanaAsistencia.getjButtonGestionAsistenciaVModificarFechaAsistencia().addActionListener(this);
            ventanaAsistencia.setLocationRelativeTo(null);
            ventanaAsistencia.setVisible(true);
            return;
        }
        
        if (ventanaAsistenciaMenu != null && ae.getSource() == ventanaAsistenciaMenu.getjButtonMenuAsistenciaVAlertaAsistencia()){
            ventanaAlertaInasistencia = new AlertaInasistenciaV();
            ventanaAlertaInasistencia.getjButtonAlertaInasistenciaPBuscar().addActionListener(this);
            ventanaAlertaInasistencia.getjButtonAlertaInasistenciaPCerrar().addActionListener(this);
            ventanaAlertaInasistencia.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            ventanaAlertaInasistencia.setLocationRelativeTo(null);
            ventanaAlertaInasistencia.setVisible(true);
            return;
        }
        
        if (ventanaAsistencia != null && ae.getSource() == ventanaAsistencia.getjButtonGestionAsistenciaVCancelar()) {
            ventanaAsistencia.dispose();
            return;
        }

        if (ventanaAsistencia != null && ae.getSource() == ventanaAsistencia.getjButtonGestionAsistenciaVGestionPorCurso()) {
            ventanaTablaAsistencia = new GestionTotalAsistenciaV();
            ventanaTablaAsistencia.getjButtonGestionTotalAsistenciaVBorrar().addActionListener(this);
            ventanaTablaAsistencia.getjButtonGestionTotalAsistenciaVBuscar().addActionListener(this);
            ventanaTablaAsistencia.getjButtonGestionTotalAsistenciaVSalir().addActionListener(this);
            ventanaTablaAsistencia.setLocationRelativeTo(null);
            ventanaTablaAsistencia.setVisible(true);
            return;
        }

        if (ventanaTablaAsistencia != null && ae.getSource() == ventanaTablaAsistencia.getjButtonGestionTotalAsistenciaVBuscar()) {
            String cursoSeleccionado = (String) ventanaTablaAsistencia.getjComboBoxGestionTotalAsistenciaVCurso().getSelectedItem();
            
            if (!esAdminLogueado && profesorLogueado != null && !profesorLogueado.getCursoJefatura().equalsIgnoreCase(cursoSeleccionado)) {
                JOptionPane.showMessageDialog(ventanaTablaAsistencia,
                    "Acceso denegado. Solo puede gestionar la asistencia de su curso: " + profesorLogueado.getCursoJefatura(),
                    "Permiso Denegado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (cursoSeleccionado == null || cursoSeleccionado.trim().isEmpty()) {
                JOptionPane.showMessageDialog(ventanaTablaAsistencia, "Por favor, seleccione un curso.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String fechaTexto = ventanaTablaAsistencia.getjTextFieldGestionTotalAsistenciaVFecha().getText();
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate fecha = LocalDate.parse(fechaTexto, formatter);
                ArrayList<Alumno> alumnosDelCurso = obtenerAlumnosPorCurso(cursoSeleccionado);

                if (alumnosDelCurso.isEmpty()) {
                    JOptionPane.showMessageDialog(ventanaTablaAsistencia, "No se encontraron alumnos para el curso '" + cursoSeleccionado + "'.", "Información", JOptionPane.INFORMATION_MESSAGE);
                }

                AsistenciaTableModel modelo = new AsistenciaTableModel(alumnosDelCurso, fecha);
                JTable tabla = ventanaTablaAsistencia.getjTableGestionTotalAsistenciaVTabla();
                tabla.setModel(modelo);
                configurarEditorDeAsistencia(tabla);

            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(ventanaTablaAsistencia, "El formato de la fecha es incorrecto. Use dd-MM-yyyy.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        if (ventanaTablaAsistencia != null && ae.getSource() == ventanaTablaAsistencia.getjButtonGestionTotalAsistenciaVBorrar()) {
            JTable tabla = ventanaTablaAsistencia.getjTableGestionTotalAsistenciaVTabla();
            int filaSeleccionada = tabla.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(ventanaTablaAsistencia, "Por favor, seleccione una fila para borrar.", "Ninguna fila seleccionada", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (tabla.getModel() instanceof AsistenciaTableModel) {
                AsistenciaTableModel modelo = (AsistenciaTableModel) tabla.getModel();
                int confirmacion = JOptionPane.showConfirmDialog(
                    ventanaTablaAsistencia,
                    "¿Está seguro de que desea eliminar esta fila de la vista?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    modelo.borrarAsistenciaDeFila(filaSeleccionada);
                    modelo.eliminarFila(filaSeleccionada);
                }
            }
            return;
        }

        if (ventanaTablaAsistencia != null && ae.getSource() == ventanaTablaAsistencia.getjButtonGestionTotalAsistenciaVSalir()) {
            ventanaTablaAsistencia.dispose();
            return;
        }

        if (ventanaAsistencia != null && ae.getSource() == ventanaAsistencia.getjButtonGestionAsistenciaVModificarFechaAsistencia()) {
            ventanaAsistenciaModificar = new ModificarFechaAsistenciaV();
            JPanel panelPrincipalFechaAsistencia = ventanaAsistenciaModificar.getjPanelModificarFechaAsistenciaVPrincipal();

            panelRellenadoFechaAsistencia = new ModificarFechaAsistenciaRellenadoP();
            panelRellenadoFechaAsistencia.setSize(440, 275);
            panelRellenadoFechaAsistencia.setLocation(0, 0);
            panelPrincipalFechaAsistencia.removeAll();
            panelPrincipalFechaAsistencia.add(panelRellenadoFechaAsistencia, BorderLayout.CENTER);
            panelPrincipalFechaAsistencia.revalidate();
            panelPrincipalFechaAsistencia.repaint();
            panelRellenadoFechaAsistencia.ocultarjLabelModificarFechaAsistenciaRellenadoPError();

            panelRellenadoFechaAsistencia.getjButtonModificarFechaAsistenciaPBuscar().addActionListener(this);
            ventanaAsistenciaModificar.getjButtonModificarFechaAsistenciaVCancelar().addActionListener(this);
            ventanaAsistenciaModificar.setVisible(true);
            ventanaAsistenciaModificar.setLocationRelativeTo(null);
            return;
        }

        if (panelRellenadoFechaAsistencia != null && ae.getSource() == panelRellenadoFechaAsistencia.getjButtonModificarFechaAsistenciaPBuscar()) {
            Alumno alumnoBuscado = obtenerAlumnoPorRut(panelRellenadoFechaAsistencia.getjTextFieldModificarFechaAsistenciaPRut());
            if (alumnoBuscado != null) {
                try {
                    String fechaTexto = panelRellenadoFechaAsistencia.getjTextFieldModificarFechaAsistenciaPFecha();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate fecha = LocalDate.parse(fechaTexto, formatter);
                    Asistencia asistenciaActual = alumnoBuscado.getAsistencia(fecha);

                    if (asistenciaActual != null) {
                        panelEditarFechaAsistencia = new ModificarFechaAsistenciaEditarP();
                        panelEditarFechaAsistencia.setSize(440, 275);
                        panelEditarFechaAsistencia.setLocation(0, 0);

                        ventanaAsistenciaModificar.getjPanelModificarFechaAsistenciaVPrincipal().removeAll();
                        ventanaAsistenciaModificar.getjPanelModificarFechaAsistenciaVPrincipal().add(panelEditarFechaAsistencia, BorderLayout.CENTER);
                        ventanaAsistenciaModificar.getjPanelModificarFechaAsistenciaVPrincipal().revalidate();
                        ventanaAsistenciaModificar.getjPanelModificarFechaAsistenciaVPrincipal().repaint();

                        panelEditarFechaAsistencia.getjButtonModificarFechaAsistenciaEditarPActualizar().addActionListener(this);
                        panelEditarFechaAsistencia.getjComboBoxModificarFechaAs().setSelectedItem(asistenciaActual.getEstado().toString());
                        panelEditarFechaAsistencia.getjTextFieldModificarFechaAsistenciaEditarPFecha().setText(fechaTexto);
                        panelEditarFechaAsistencia.ocultarjLabelModificarFechaAsistenciaEditarPExito();
                        return;
                    }
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(null, "Error al convertir la fecha ingresada", "Error de conversion de hecha", JOptionPane.ERROR_MESSAGE);
                }
            }
            panelRellenadoFechaAsistencia.mostrarjLabelModificarFechaAsistenciaRellenadoPError();
            return;
        }

        if (panelEditarFechaAsistencia != null && ae.getSource() == panelEditarFechaAsistencia.getjButtonModificarFechaAsistenciaEditarPActualizar()) {
            String rutAlumno = panelRellenadoFechaAsistencia.getjTextFieldModificarFechaAsistenciaPRut();
            String fechaOriginalTexto = panelRellenadoFechaAsistencia.getjTextFieldModificarFechaAsistenciaPFecha();
            String nuevaFechaTexto = panelEditarFechaAsistencia.getjTextFieldModificarFechaAsistenciaEditarPFecha().getText();
            String estadoAsistenciaSeleccionado = (String) panelEditarFechaAsistencia.getjComboBoxModificarFechaAs().getSelectedItem();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            try {
                LocalDate fechaOriginal = LocalDate.parse(fechaOriginalTexto, formatter);
                LocalDate nuevaFecha = LocalDate.parse(nuevaFechaTexto, formatter);
                Asistencia.EstadoAsistencia nuevoEstado = Asistencia.EstadoAsistencia.valueOf(estadoAsistenciaSeleccionado);
                Alumno alumno = obtenerAlumnoPorRut(rutAlumno);

                if (alumno != null) {
                    if (alumno.modificarAsistencia(fechaOriginal, nuevaFecha, nuevoEstado)) {
                        panelEditarFechaAsistencia.mostrarjLabelModificarFechaAsistenciaEditarPExito();
                    } else {
                        JOptionPane.showMessageDialog(ventanaAsistenciaModificar, "Error: No se encontró la asistencia original para modificar.", "Error de Modificación", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(ventanaAsistenciaModificar, "El formato de la nueva fecha es incorrecto. Use dd-MM-yyyy.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }
        
        if (ventanaAsistenciaModificar != null && ae.getSource() == ventanaAsistenciaModificar.getjButtonModificarFechaAsistenciaVCancelar()) {
            ventanaAsistenciaModificar.dispose();
            return;
        }
        
        if(ventanaAlertaInasistencia != null && ae.getSource() == ventanaAlertaInasistencia.getjButtonAlertaInasistenciaPBuscar()){
            int umbral = (int) ventanaAlertaInasistencia.getjSpinnerAlertaInasistenciaPUmbralFaltas().getValue();
            int dias = (int) ventanaAlertaInasistencia.getjSpinnerAlertaInasistenciaPDias().getValue();
            Map<Alumno, Integer> alumnosEncontrados = obtenerAlumnosConInasistenciaCronica(umbral, dias);
            JTable tablaResultados = ventanaAlertaInasistencia.getJTableResultados();
            DefaultTableModel modelo = (DefaultTableModel) tablaResultados.getModel();
            modelo.setRowCount(0); 
            for (Map.Entry<Alumno, Integer> entry : alumnosEncontrados.entrySet()) {
                Alumno alumno = entry.getKey();
                Integer cantidadAusencias = entry.getValue();
                Object[] fila = {
                    alumno.getRut(),
                    alumno.getNombre(),
                    alumno.getCurso(),
                    cantidadAusencias
                };
                modelo.addRow(fila);
            }
            ventanaAlertaInasistencia.getJLabelContador().setText(String.valueOf(alumnosEncontrados.size()));
            return;
        }
        
        if(ventanaAlertaInasistencia != null && ae.getSource() == ventanaAlertaInasistencia.getjButtonAlertaInasistenciaPCerrar()){
            ventanaAlertaInasistencia.dispose();
            return;
        }

        if (ventanaAsistenciaMenu != null && ae.getSource() == ventanaAsistenciaMenu.getjButtonGenerarReportesVir()) {
            ventanaGenerarReportes = new GenerarReportesV();
            ventanaGenerarReportes.getjButtonReporteAlumnoVir().addActionListener(this);
            ventanaGenerarReportes.getjButtonReporteCursosVir().addActionListener(this);
            ventanaGenerarReportes.getjButtonReporteVCancelar().addActionListener(this);
            ventanaGenerarReportes.setLocationRelativeTo(null);
            ventanaGenerarReportes.setVisible(true);
            return;
        }

        if (ventanaGenerarReportes != null && ae.getSource() == ventanaGenerarReportes.getjButtonReporteVCancelar()) {
            ventanaGenerarReportes.dispose();
            return;
        }

        if (ventanaGenerarReportes != null && ae.getSource() == ventanaGenerarReportes.getjButtonReporteAlumnoVir()) {
            ventanaGenerarAlumno = new GenerarAlumnoV();
            ventanaGenerarAlumno.getjButtonGenerarAlumnoVCrear().addActionListener(this);
            ventanaGenerarAlumno.getGenerarAlumnoVCancelar().addActionListener(this);
            ventanaGenerarAlumno.setLocationRelativeTo(null);
            ventanaGenerarAlumno.setVisible(true);
            return;
        }
        
        if (ventanaGenerarAlumno != null && ae.getSource() == ventanaGenerarAlumno.getjButtonGenerarAlumnoVCrear()) {
            String rut = ventanaGenerarAlumno.getjTextFieldGenerarAlumnoVRut().trim();
            if (rut.isEmpty()) {
                JOptionPane.showMessageDialog(ventanaGenerarAlumno, "Ingresa el RUT del alumno.");
                return;
            }
            if (this.buscarAlumno(rut) == null) {
                JOptionPane.showMessageDialog(ventanaGenerarAlumno, "No existe un alumno con ese RUT.");
                return;
            }
            try {
                ReportePorAlumno rep = new ReportePorAlumno(this, rut);
                java.nio.file.Path out = rep.generarReporte(java.nio.file.Paths.get("reportsAlumnos"));
                JOptionPane.showMessageDialog(ventanaGenerarAlumno, "Reporte generado en:\n" + out.toAbsolutePath());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(ventanaGenerarAlumno, "Error al generar el reporte: " + ex.getMessage());
            }
            return;
        }

        if (ventanaGenerarAlumno != null && ae.getSource() == ventanaGenerarAlumno.getGenerarAlumnoVCancelar()) {
            ventanaGenerarAlumno.dispose();
            return;
        }

        if (ventanaGenerarReportes != null && ae.getSource() == ventanaGenerarReportes.getjButtonReporteCursosVir()) {
            ventanaGenerarCurso = new GenerarCursoV();
            ventanaGenerarCurso.getjButtonGenerarCursoVCrear().addActionListener(this);
            ventanaGenerarCurso.getjButtonGenerarReporteVCancelar().addActionListener(this);
            ventanaGenerarCurso.setLocationRelativeTo(null);
            ventanaGenerarCurso.setVisible(true);
            return;
        }

        if (ventanaGenerarCurso != null && ae.getSource() == ventanaGenerarCurso.getjButtonGenerarCursoVCrear()) {
            String curso = ventanaGenerarCurso.getjTextFieldGenerarCursoVC().trim();
            if (curso.isEmpty()) {
                JOptionPane.showMessageDialog(ventanaGenerarCurso, "Ingresa el nombre del curso.");
                return;
            }
            java.util.List<Alumno> alumnos = this.obtenerAlumnosPorCurso(curso);
            if (alumnos.isEmpty()) {
                JOptionPane.showMessageDialog(ventanaGenerarCurso, "No hay alumnos para ese curso.");
                return;
            }
            try {
                ReportePorCurso rep = new ReportePorCurso(this, curso);
                java.nio.file.Path out = rep.generarReporte(java.nio.file.Paths.get("reportsCursos"));
                JOptionPane.showMessageDialog(ventanaGenerarCurso, "Reporte generado en:\n" + out.toAbsolutePath());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(ventanaGenerarCurso, "Error al generar el reporte: " + ex.getMessage());
            }
            return;
        }
        
        if (ventanaGenerarCurso != null && ae.getSource() == ventanaGenerarCurso.getjButtonGenerarReporteVCancelar()) {
            ventanaGenerarCurso.dispose();
            return;
        }

        if (ventanaMenuProfesor != null && ae.getSource() == ventanaMenuProfesor.getjButtonMenuProfesorVCancelar()) {
            ventanaMenuProfesor.dispose();
            logout();
            return;
        }

        if (ventanaMenuProfesor != null && ae.getSource() == ventanaMenuProfesor.getjButtonMenuProfesorVAgregar()) {
            ventanaProfesorAgregar = new AgregarProfesorV();
            ventanaProfesorAgregar.ocultarjLabelAgregarProfesorVExito();
            ventanaProfesorAgregar.getjButtonAgregarProfesorVCrear().addActionListener(this);
            ventanaProfesorAgregar.getjButtonAgregarProfesorVCancelar().addActionListener(this);
            ventanaProfesorAgregar.setVisible(true);
            ventanaProfesorAgregar.setLocationRelativeTo(null);
            return;
        }

        if (ventanaProfesorAgregar != null && ae.getSource() == ventanaProfesorAgregar.getjButtonAgregarProfesorVCrear()) {
            String nombre = ventanaProfesorAgregar.getjTextFieldAgregarProfesorVNombre().getText();
            String curso = (String) ventanaProfesorAgregar.getjComboBoxAgregarProfesorVCurso().getSelectedItem();
            String rut = ventanaProfesorAgregar.getjTextFieldAgregarProfesorVRut().getText();

            if (nombre == null || curso == null || rut == null || nombre.trim().isEmpty() || curso.trim().isEmpty() || rut.trim().isEmpty()) {
                JOptionPane.showMessageDialog(ventanaProfesorAgregar, "Nombre, Curso y RUT son obligatorios.", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                insertarProfesor(nombre.trim(), rut.trim(), curso.trim());
                JOptionPane.showMessageDialog(ventanaProfesorAgregar, "Profesor agregado correctamente.");
                ventanaProfesorAgregar.getjTextFieldAgregarProfesorVNombre().setText("");
                ventanaProfesorAgregar.getjComboBoxAgregarProfesorVCurso().setSelectedItem("");
                ventanaProfesorAgregar.getjTextFieldAgregarProfesorVRut().setText("");
                ventanaProfesorAgregar.mostrarjLabelAgregarProfesorVExito();
            } catch (EntidadDuplicadaException e) {
                JOptionPane.showMessageDialog(ventanaProfesorAgregar, e.getMessage(), "RUT/Curso duplicado", JOptionPane.WARNING_MESSAGE);
            }
            return;
        }

        if (ventanaProfesorAgregar != null && ae.getSource() == ventanaProfesorAgregar.getjButtonAgregarProfesorVCancelar()) {
            ventanaProfesorAgregar.dispose();
            return;
        }

        if (ventanaMenuProfesor != null && ae.getSource() == ventanaMenuProfesor.getjButtonMenuProfesorVEliminar()) {
            ventanaEliminarProfesor = new EliminarProfesorV();
            ventanaEliminarProfesor.getjButtonEliminarProfesorVEliminar().addActionListener(this);
            ventanaEliminarProfesor.getjButtonEliminarProfesorVCancelar().addActionListener(this);
            ventanaEliminarProfesor.setVisible(true);
            ventanaEliminarProfesor.setLocationRelativeTo(null);
            return;
        }

        if (ventanaEliminarProfesor != null && ae.getSource() == ventanaEliminarProfesor.getjButtonEliminarProfesorVEliminar()) {
            String rut = ventanaEliminarProfesor.getjTextFieldRutProfesor().getText();
            if (rut == null || rut.trim().isEmpty()) {
                JOptionPane.showMessageDialog(ventanaEliminarProfesor, "Ingrese el RUT del profesor a eliminar.", "Falta RUT", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                eliminarProfesorPorRut(rut);
                JOptionPane.showMessageDialog(ventanaEliminarProfesor, "Profesor eliminado.");
                ventanaEliminarProfesor.getjTextFieldRutProfesor().setText("");
            } catch (EntidadNoEncontradaException e) {
                JOptionPane.showMessageDialog(ventanaEliminarProfesor, "No se encontró un profesor con ese RUT.", "No existe", JOptionPane.INFORMATION_MESSAGE);
            }
            return;
        }

        if (ventanaEliminarProfesor != null && ae.getSource() == ventanaEliminarProfesor.getjButtonEliminarProfesorVCancelar()) {
            ventanaEliminarProfesor.dispose();
            return;
        }

        if (ventanaMenuProfesor != null && ae.getSource() == ventanaMenuProfesor.getjButtonMenuProfesorVModificar()) {
            ventanaModificarProfesor = new ModificarProfesorV();
            ventanaModificarProfesor.getjButtonMenuModificarProfesorVModificar().addActionListener(this);
            ventanaModificarProfesor.getjButtonMenuModificarProfesorVCancelar().addActionListener(this);
            ventanaModificarProfesor.getjTextFieldRutProfesorModificar().setText("");
            ventanaModificarProfesor.setVisible(true);
            ventanaModificarProfesor.setLocationRelativeTo(null);
            return;
        }

        if (ventanaModificarProfesor != null && ae.getSource() == ventanaModificarProfesor.getjButtonMenuModificarProfesorVModificar()) {
            String rut = ventanaModificarProfesor.getjTextFieldRutProfesorModificar().getText();
            String curso = (String) ventanaModificarProfesor.getjComboBoxModificarProfesorVCurso().getSelectedItem();

            if (rut == null || curso == null || rut.trim().isEmpty() || curso.trim().isEmpty()) {
                JOptionPane.showMessageDialog(ventanaModificarProfesor, "Ingrese RUT y el nuevo curso.", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                modificarProfesorCurso(rut, curso);
                JOptionPane.showMessageDialog(ventanaModificarProfesor, "Profesor modificado correctamente.");
                ventanaModificarProfesor.getjTextFieldRutProfesorModificar().setText("");
            } catch (EntidadNoEncontradaException e) {
                JOptionPane.showMessageDialog(ventanaModificarProfesor, "No se encontró un profesor con ese RUT.", "No existe", JOptionPane.INFORMATION_MESSAGE);
            }
            return;
        }

        if (ventanaModificarProfesor != null && ae.getSource() == ventanaModificarProfesor.getjButtonMenuModificarProfesorVCancelar()) {
            ventanaModificarProfesor.dispose();
            return;
        }
    }

    private void modificarAlumno(String nombre, String curso, String rut) throws EntidadNoEncontradaException {
        if(!this.mapaAlumnos.containsKey(rut)){
            throw new EntidadNoEncontradaException("No existe ningun alumno con el RUT " + rut);
        }
        Alumno alumnoModificar = this.mapaAlumnos.get(rut);
        alumnoModificar.setNombre(nombre);
        alumnoModificar.setCurso(curso);
        alumnoModificar.setRut(rut);
    }
    
    private void modificarAlumno(String nombre, String curso, String rut, String rutOriginal) throws EntidadNoEncontradaException {
        if(!this.mapaAlumnos.containsKey(rutOriginal)){
            throw new EntidadNoEncontradaException("No existe ningun alumno con el RUT " + rutOriginal);
        }
        Alumno alumnoModificar = this.mapaAlumnos.get(rutOriginal);
        alumnoModificar.setNombre(nombre);
        alumnoModificar.setCurso(curso);
        alumnoModificar.setRut(rut);
        if (!rutOriginal.equals(rut)) {
            this.mapaAlumnos.remove(rutOriginal);
            this.mapaAlumnos.put(rut, alumnoModificar);
        }
    }
    
    private void insertarAlumno(String nombre, String curso, String rut) throws EntidadDuplicadaException {
        if (this.mapaAlumnos.containsKey(rut)) {
            throw new EntidadDuplicadaException("Ya existe un alumno con el RUT " + rut);
        }
        Alumno nuevoAlumno = new Alumno(nombre, curso, rut);
        this.mapaAlumnos.put(rut, nuevoAlumno);
        System.out.println("El alumno " + nuevoAlumno.getNombre() + " se ha creado con éxito.");
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
    
    private void eliminarAlumno(String rut) throws EntidadNoEncontradaException {
        if (rut == null || rut.trim().isEmpty()) {
            throw new IllegalArgumentException("El RUT no puede ser nulo o vacío.");
        }
        if (!this.mapaAlumnos.containsKey(rut)) {
            throw new EntidadNoEncontradaException("No se encontró un alumno con el RUT " + rut);
        }
        this.mapaAlumnos.remove(rut);
    }
   
    private void registrarAsistenciaDiaria() throws IOException {
        System.out.println("\n--- Registro de Asistencia Diario ---");
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaDeAsistencia = leerYConvertirFecha();

        System.out.print("Ingrese el curso para pasar lista (ej: Primero basico): ");
        String cursoSeleccionado = this.lectorConsola.readLine();

        System.out.println("\nPasando lista para el curso '" + cursoSeleccionado + "' en la fecha " + fechaDeAsistencia.format(formatoFecha));

        for (Alumno alumno : this.mapaAlumnos.values()) {
            if (alumno.getCurso().equalsIgnoreCase(cursoSeleccionado)) {
                String estadoInput;
                Asistencia.EstadoAsistencia estadoFinal = null;

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
        
        System.out.print("Ingrese el curso para ver la asistencia (ej: Primero basico): ");
        String cursoSeleccionado = this.lectorConsola.readLine();

        System.out.println("\nMostrando asistencia del curso '" + cursoSeleccionado + "' en la fecha " + fechaDeAsistencia.format(formatoFecha));
        
        boolean encontrado = false;
        for (Alumno alumno : this.mapaAlumnos.values()) {
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
    
    public Map<Alumno, Integer> obtenerAlumnosConInasistenciaCronica(int umbralFaltas, int diasHaciaAtras) {
        Map<Alumno, Integer> alumnosEnRiesgo = new HashMap<>();
        LocalDate fechaFin = LocalDate.now();
        LocalDate fechaInicio = fechaFin.minusDays(diasHaciaAtras);

        for (Alumno alumno : this.mapaAlumnos.values()) {
            ArrayList<Asistencia> asistenciasEnRango = alumno.getAsistencia(fechaInicio, fechaFin);
            int contadorAusencias = 0;
            for (Asistencia asistencia : asistenciasEnRango) {
                if (asistencia.getEstado() == Asistencia.EstadoAsistencia.AUSENTE) {
                    contadorAusencias++;
                }
            }
            if (contadorAusencias >= umbralFaltas) {
                alumnosEnRiesgo.put(alumno, contadorAusencias);
            }
        }
        return alumnosEnRiesgo;
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
    
    private void insertarProfesor(String nombre, String rut, String curso) throws EntidadDuplicadaException {
        for (Profesor profesor : listaProfesores) {
            if (profesor.getRut().equalsIgnoreCase(rut)) {
                throw new EntidadDuplicadaException("Ya existe un profesor con el RUT " + rut);
            }
        }
        for (Profesor profesor : listaProfesores) {
            if (profesor.getCursoJefatura().equalsIgnoreCase(curso)) {
                throw new EntidadDuplicadaException("El curso '" + curso + "' ya está asignado al profesor " + profesor.getNombre());
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
    
    private void eliminarProfesorPorRut(String rut) throws EntidadNoEncontradaException {
        if (rut == null) throw new IllegalArgumentException("RUT inválido.");
        String target = rut.trim();
        if (target.isEmpty()) throw new IllegalArgumentException("RUT inválido.");

        for (int i = 0; i < this.listaProfesores.size(); i++) {
            Profesor p = this.listaProfesores.get(i);
            if (p.getRut() != null && p.getRut().equalsIgnoreCase(target)) {
                this.listaProfesores.remove(i);
                return;
            }
        }
        throw new EntidadNoEncontradaException("No se encontró profesor con RUT " + target + ".");
    }

    private void modificarProfesorCurso(String rut, String nuevoCurso) throws EntidadNoEncontradaException {
        if (rut == null || nuevoCurso == null) throw new IllegalArgumentException("Datos inválidos.");
        String target = rut.trim();
        String nuevo  = nuevoCurso.trim();
        if (target.isEmpty() || nuevo.isEmpty()) throw new IllegalArgumentException("Datos inválidos.");

        for (int i = 0; i < this.listaProfesores.size(); i++) {
            Profesor p = this.listaProfesores.get(i);
            String r = (p != null) ? p.getRut() : null;

            if (r != null && r.equalsIgnoreCase(target)) {
                p.setCursoJefatura(nuevo);
                System.out.println("Profesor con RUT " + target + " actualizado a curso " + nuevo + ".");
                return;
            }
        }
        throw new EntidadNoEncontradaException("No se encontró profesor con RUT " + target + ".");
    }
    
    private boolean existeProfesorPorRut(String rut) {
        if (rut == null) return false;
        String target = rut.trim();
        if (target.isEmpty()) return false;

        for (int i = 0; i < this.listaProfesores.size(); i++) {
            Profesor p = this.listaProfesores.get(i);
            if (p != null && p.getRut() != null
                    && p.getRut().trim().equalsIgnoreCase(target)) {
                return true;
            }
        }
        return false;
    }

    private void cerrarAplicacion() {
        int confirm = JOptionPane.showConfirmDialog(ventanaPrincipal,
                "¿Estás seguro de que quieres salir? Se guardarán los cambios.",
                "Confirmar Salida",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                guardarDatosEnTxt();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(ventanaPrincipal,
                        "Error al guardar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                ventanaPrincipal.dispose();
                System.exit(0);
            }
        }
    }
    
    private void mostrarLoginAdmin(Runnable accionAlExito) {
        this.accionPostLogin = accionAlExito;

        ventanaInicioSesion = new InicioSesionV();
        panelLoginAdmin = new InicioSesionAdministradorP();

        panelLoginAdmin.getjButtonIngresarAdmin().addActionListener(this);
        ventanaInicioSesion.getjButtonCancelar().addActionListener(this);

        JPanel panelPrincipal = ventanaInicioSesion.getjPanelPrincipalLogin();
        panelPrincipal.removeAll();
        panelPrincipal.add(panelLoginAdmin, BorderLayout.CENTER);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();

        ventanaInicioSesion.setVisible(true);
    }
    
    private void mostrarLoginProfesor(Runnable accionAlExito) {
        this.accionPostLogin = accionAlExito;

        ventanaInicioSesion = new InicioSesionV();
        panelLoginProfesor = new InicioSesionProfesorP();

        panelLoginProfesor.getjButtonIngresarProfesor().addActionListener(this);
        ventanaInicioSesion.getjButtonCancelar().addActionListener(this);

        JPanel panelPrincipal = ventanaInicioSesion.getjPanelPrincipalLogin();
        panelPrincipal.removeAll();
        panelPrincipal.add(panelLoginProfesor, BorderLayout.CENTER);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();

        ventanaInicioSesion.setVisible(true);
    }
    
    private void logout() {
        this.profesorLogueado = null;
        this.esAdminLogueado = false;
        System.out.println("Sesión cerrada.");
    }

    private Profesor buscarProfesorPorRut(String rut) throws EntidadNoEncontradaException {
        if (rut == null || rut.trim().isEmpty()) {
            throw new IllegalArgumentException("El RUT no puede ser nulo o vacío.");
        }
        for (Profesor p : this.listaProfesores) {
            if (p.getRut().equalsIgnoreCase(rut.trim())) {
                return p;
            }
        }
        throw new EntidadNoEncontradaException("No se encontró un profesor con el RUT " + rut);
    }
    
    private LocalDate leerYConvertirFecha()throws IOException{
        LocalDate fecha = null;
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
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
    
    private int leerEntero()throws IOException{
        int opcion = Integer.parseInt(this.lectorConsola.readLine());
        return opcion;
    }

    private void configurarEditorDeAsistencia(JTable tabla) {
        TableColumn estadoColumn = tabla.getColumnModel().getColumn(2);
        JComboBox<Asistencia.EstadoAsistencia> comboBox = new JComboBox<>();
        for (Asistencia.EstadoAsistencia estado : Asistencia.EstadoAsistencia.values()) {
            comboBox.addItem(estado);
        }
        estadoColumn.setCellEditor(new DefaultCellEditor(comboBox));
    }

    public Alumno buscarAlumno(String rut) {
        if (rut == null) return null;
        return this.mapaAlumnos.get(rut.trim());
    }
}
