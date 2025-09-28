/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.proyectosia.vista.FuncionalidadAsistenciaCronica;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTable;

/**
 *
 * @author geoff
 */
public class AlertaInasistenciaV extends javax.swing.JFrame {

    /**
     * Creates new form AlertaInasistenciaV
     */
    public AlertaInasistenciaV() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelCriterios = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSpinnerAlertaInasistenciaPUmbralFaltas = new javax.swing.JSpinner();
        jLabelDias = new javax.swing.JLabel();
        jSpinnerAlertaInasistenciaPDias = new javax.swing.JSpinner();
        jLabelDias2 = new javax.swing.JLabel();
        jButtonAlertaInasistenciaPBuscar = new javax.swing.JButton();
        jPanelResultados = new javax.swing.JPanel();
        jScrollPaneResultados = new javax.swing.JScrollPane();
        jTableResultados = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jButtonAlertaInasistenciaPCerrar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabelContador = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Número mínimo de ausencias:");

        jSpinnerAlertaInasistenciaPUmbralFaltas.setModel(new javax.swing.SpinnerNumberModel(5, 1, 30, 1));
        jSpinnerAlertaInasistenciaPUmbralFaltas.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jSpinnerAlertaInasistenciaPUmbralFaltas.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabelDias.setText("En los últimos:");

        jSpinnerAlertaInasistenciaPDias.setModel(new javax.swing.SpinnerNumberModel(1, 1, 30, 1));
        jSpinnerAlertaInasistenciaPDias.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jSpinnerAlertaInasistenciaPDias.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabelDias2.setText("(días)");

        jButtonAlertaInasistenciaPBuscar.setBackground(new java.awt.Color(204, 204, 204));
        jButtonAlertaInasistenciaPBuscar.setText("Buscar Alumnos");
        jButtonAlertaInasistenciaPBuscar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanelCriteriosLayout = new javax.swing.GroupLayout(jPanelCriterios);
        jPanelCriterios.setLayout(jPanelCriteriosLayout);
        jPanelCriteriosLayout.setHorizontalGroup(
            jPanelCriteriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCriteriosLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanelCriteriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonAlertaInasistenciaPBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelCriteriosLayout.createSequentialGroup()
                        .addGroup(jPanelCriteriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDias))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelCriteriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelCriteriosLayout.createSequentialGroup()
                                .addComponent(jSpinnerAlertaInasistenciaPDias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelDias2))
                            .addComponent(jSpinnerAlertaInasistenciaPUmbralFaltas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(152, Short.MAX_VALUE))
        );
        jPanelCriteriosLayout.setVerticalGroup(
            jPanelCriteriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCriteriosLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanelCriteriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jSpinnerAlertaInasistenciaPUmbralFaltas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelCriteriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDias)
                    .addComponent(jSpinnerAlertaInasistenciaPDias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDias2))
                .addGap(18, 18, 18)
                .addComponent(jButtonAlertaInasistenciaPBuscar)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanelResultados.setPreferredSize(new java.awt.Dimension(440, 290));

        jTableResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Rut", "Nombre", "Curso", "Ausencias Registradas"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPaneResultados.setViewportView(jTableResultados);

        javax.swing.GroupLayout jPanelResultadosLayout = new javax.swing.GroupLayout(jPanelResultados);
        jPanelResultados.setLayout(jPanelResultadosLayout);
        jPanelResultadosLayout.setHorizontalGroup(
            jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelResultadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneResultados)
                .addContainerGap())
        );
        jPanelResultadosLayout.setVerticalGroup(
            jPanelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelResultadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneResultados, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel4.setBackground(new java.awt.Color(153, 153, 153));
        jLabel4.setFont(new java.awt.Font("Quicksand", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Alerta de insasistencia cronica");
        jLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButtonAlertaInasistenciaPCerrar.setBackground(new java.awt.Color(231, 231, 231));
        jButtonAlertaInasistenciaPCerrar.setText("Cerrar");
        jButtonAlertaInasistenciaPCerrar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setText("Alumnos encontrados:");

        jLabelContador.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelContador, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonAlertaInasistenciaPCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(98, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(179, 179, 179))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanelCriterios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(83, 83, 83))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelResultados, javax.swing.GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel4)
                .addGap(28, 28, 28)
                .addComponent(jPanelCriterios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jPanelResultados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAlertaInasistenciaPCerrar)
                    .addComponent(jLabel2)
                    .addComponent(jLabelContador))
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public JButton getjButtonAlertaInasistenciaPBuscar() {
        return jButtonAlertaInasistenciaPBuscar;
    }

    public JButton getjButtonAlertaInasistenciaPCerrar() {
        return jButtonAlertaInasistenciaPCerrar;
    }

    public JLabel getJLabelContador() {
        return jLabelContador;
    }

    public JSpinner getjSpinnerAlertaInasistenciaPDias() {
        return jSpinnerAlertaInasistenciaPDias;
    }

    public JSpinner getjSpinnerAlertaInasistenciaPUmbralFaltas() {
        return jSpinnerAlertaInasistenciaPUmbralFaltas;
    }

    public JTable getJTableResultados() {
        return jTableResultados;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAlertaInasistenciaPBuscar;
    private javax.swing.JButton jButtonAlertaInasistenciaPCerrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelContador;
    private javax.swing.JLabel jLabelDias;
    private javax.swing.JLabel jLabelDias2;
    private javax.swing.JPanel jPanelCriterios;
    private javax.swing.JPanel jPanelResultados;
    private javax.swing.JScrollPane jScrollPaneResultados;
    private javax.swing.JSpinner jSpinnerAlertaInasistenciaPDias;
    private javax.swing.JSpinner jSpinnerAlertaInasistenciaPUmbralFaltas;
    private javax.swing.JTable jTableResultados;
    // End of variables declaration//GEN-END:variables
}
