/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr2_final.vistas;

import gr2_final.accesoadatos.ConsultorioData;
import gr2_final.accesoadatos.DiaDeSpaData;
import gr2_final.accesoadatos.MasajistaData;
import gr2_final.accesoadatos.SesionData;
import gr2_final.accesoadatos.TratamientoData;
import gr2_final.entidades.Consultorio;
import gr2_final.entidades.DiaDeSpa;
import gr2_final.entidades.Masajista;
import gr2_final.entidades.Sesion;
import gr2_final.entidades.Tratamiento;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rodrigo
 */
public class SesionVista extends javax.swing.JInternalFrame {

    /**
     * Creates new form SesionVista
     */
    private DefaultTableModel modelo;
    private SesionData sData = new SesionData();

    public SesionVista() {
        initComponents();
        modelo = new DefaultTableModel();
        armarCabecera();
        cargarComboTratamientos();
        cargarComboConsultorios();
        //cargarComboMasajistas();
        cargarComboDiaDeSpa();

        jCTratamiento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtrarMasajistasPorTratamiento();
            }
        });
    }

    private void cargarComboTratamientos() {
        jCTratamiento.removeAllItems();
        jCTratamiento.addItem(null);
        TratamientoData td = new TratamientoData();
        for (Tratamiento t : td.listarTratamientos()) {
            jCTratamiento.addItem(t);
        }
    }

    private void cargarComboConsultorios() {
        jCConsultorio.removeAllItems();
        jCConsultorio.addItem(null);
        ConsultorioData cd = new ConsultorioData();
        for (Consultorio c : cd.listarConsultorios()) {
            jCConsultorio.addItem(c);
        }
    }

    private void cargarComboMasajistas() {
        jCMasajista.removeAllItems();
        MasajistaData md = new MasajistaData();
        for (Masajista m : md.listarMasajistasActivos()) {
            jCMasajista.addItem(m);
        }
    }

    private void cargarComboDiaDeSpa() {
        jCDiadespa.removeAllItems();
        jCDiadespa.addItem(null);  // OPCIONAL
        DiaDeSpaData dd = new DiaDeSpaData();
        for (DiaDeSpa d : dd.listarDiasDeSpa()) {
            jCDiadespa.addItem(d);
        }
    }

    private void armarCabecera() {
        modelo.addColumn("Código");
        modelo.addColumn("Fecha Inicio");
        modelo.addColumn("Fecha Fin");
        modelo.addColumn("Tratamiento");
        modelo.addColumn("Consultorio");
        modelo.addColumn("Masajista");
        modelo.addColumn("Día de Spa");
        modelo.addColumn("Estado");

        jTable1.setModel(modelo);
    }

    private void cargarTablaGeneral() {

        limpiarCampos();
        limpiarTabla();
        List<Sesion> lista = sData.listarSesiones();

        for (Sesion s : lista) {

            String tratamiento = (s.getCodigoTratam() != null)
                    ? s.getCodigoTratam().getNombre()
                    : "—";

            String consultorio = (s.getNroConsutorio() != null)
                    ? String.valueOf(s.getNroConsutorio().getNroConsultorio())
                    : "—";

            String masajista = (s.getMatricula() != null)
                    ? s.getMatricula().getNombreCompleto()
                    : "—";

            String diaSpa = (s.getCodPack() != null)
                    ? s.getCodPack().toString() // hasta ver tus getters reales
                    : "—";

            modelo.addRow(new Object[]{
                s.getCodSesion(),
                s.getFechaHoraInicio(),
                s.getFechaHoraFin(),
                tratamiento,
                consultorio,
                masajista,
                diaSpa,
                s.isEstado() ? "Activo" : "Inactivo"
            });
        }
    }

    private void filtrarMasajistasPorTratamiento() {
        jCMasajista.removeAllItems();

        Tratamiento trat = (Tratamiento) jCTratamiento.getSelectedItem();
        if (trat == null) {
            return;
        }

        String tipoNecesario = trat.getTipo();

        MasajistaData md = new MasajistaData();
        for (Masajista m : md.listarMasajistasActivos()) {
            if (m.getEspecialidad().equalsIgnoreCase(tipoNecesario)) {
                jCMasajista.addItem(m);
            }
        }
    }

    private void limpiarTabla() {
        int filas = modelo.getRowCount() - 1;
        for (int i = filas; i >= 0; i--) {
            modelo.removeRow(i);
        }
    }

    private void limpiarCampos() {
        jTCodigo.setText("");

        jDCFechaInicio.setDate(null);
        jDCFechaFin.setDate(null);
        jCHoraInicio.setSelectedItem(null);
        jCHoraFin.setSelectedItem(null);
        jCMinutoInicio.setSelectedItem(null);
        jCMinutoFin.setSelectedItem(null);

        jCTratamiento.setSelectedItem(null);
        jCConsultorio.setSelectedItem(null);
        jCMasajista.setSelectedItem(null);
        jCDiadespa.setSelectedItem(null);

        jCBEstado.setSelected(true);

        jBModificar.setEnabled(false);
        jBEliminar.setEnabled(false);
        jBGuardar.setEnabled(true);

        jTCodigo.setEditable(false);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jCBEstado = new javax.swing.JCheckBox();
        jCTratamiento = new javax.swing.JComboBox<Tratamiento>();
        jCConsultorio = new javax.swing.JComboBox<Consultorio>();
        jCMasajista = new javax.swing.JComboBox<Masajista>();
        jCDiadespa = new javax.swing.JComboBox<DiaDeSpa>();
        jDCFechaFin = new com.toedter.calendar.JDateChooser();
        jDCFechaInicio = new com.toedter.calendar.JDateChooser();
        jTCodigo = new javax.swing.JTextField();
        jBNuevo = new javax.swing.JButton();
        jBEliminar = new javax.swing.JButton();
        jBGuardar = new javax.swing.JButton();
        jBModificar = new javax.swing.JButton();
        jBBuscar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jTBuscar = new javax.swing.JTextField();
        jCHoraFin = new javax.swing.JComboBox<>();
        jCMinutoFin = new javax.swing.JComboBox<>();
        jCHoraInicio = new javax.swing.JComboBox<>();
        jCMinutoInicio = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jBListar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        jLabel1.setText("Codigo Sesion");

        jLabel2.setText("Dia de Fin");

        jLabel3.setText("Dia de inicio");

        jLabel4.setText("Tratamiento");

        jLabel5.setText("Estado");

        jLabel6.setText("Dia de Spa");

        jLabel7.setText("Masajista");

        jLabel8.setText("Consultorio");

        jBNuevo.setText("Nuevo");
        jBNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBNuevoActionPerformed(evt);
            }
        });

        jBEliminar.setText("Eliminar");
        jBEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEliminarActionPerformed(evt);
            }
        });

        jBGuardar.setText("Guardar");
        jBGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGuardarActionPerformed(evt);
            }
        });

        jBModificar.setText("Modificar");
        jBModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBModificarActionPerformed(evt);
            }
        });

        jBBuscar.setText("Buscar");
        jBBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscarActionPerformed(evt);
            }
        });

        jLabel9.setText("Buscar por Codigo");

        jCHoraFin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20" }));

        jCMinutoFin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "30" }));

        jCHoraInicio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", " " }));

        jCMinutoInicio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "30" }));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jBListar.setText("Listar");
        jBListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBListarActionPerformed(evt);
            }
        });

        jLabel10.setText("Hora de inicio");

        jLabel11.setText("Minuto de inicio");

        jLabel12.setText("Minuto de Fin");

        jLabel13.setText("Hora de Fin");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBListar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jBNuevo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCTratamiento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCDiadespa, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCConsultorio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCMasajista, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTCodigo)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jCBEstado)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTBuscar)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGap(38, 38, 38)
                                        .addComponent(jBGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 220, Short.MAX_VALUE)
                                        .addComponent(jBModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(36, 36, 36)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jBEliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jBBuscar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jDCFechaInicio, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                    .addComponent(jCHoraInicio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jCMinutoInicio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jCMinutoFin, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jCHoraFin, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jDCFechaFin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)))))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jDCFechaInicio, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                        .addComponent(jDCFechaFin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jCHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCHoraFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jCMinutoFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jCMinutoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCTratamiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCConsultorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCMasajista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCDiadespa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCBEstado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBGuardar)
                    .addComponent(jBNuevo)
                    .addComponent(jBModificar)
                    .addComponent(jBEliminar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBListar)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(110, 110, 110))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 797, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNuevoActionPerformed
        jTCodigo.setText("");

        jDCFechaInicio.setDate(null);
        jDCFechaFin.setDate(null);
        jCHoraInicio.setSelectedItem(null);
        jCHoraFin.setSelectedItem(null);
        jCMinutoInicio.setSelectedItem(null);
        jCMinutoFin.setSelectedItem(null);

        jCTratamiento.setSelectedItem(null);
        jCConsultorio.setSelectedItem(null);
        jCMasajista.setSelectedItem(null);
        jCDiadespa.setSelectedItem(null);

        jCBEstado.setSelected(true);

        jBModificar.setEnabled(false);
        jBEliminar.setEnabled(false);
        jBGuardar.setEnabled(true);

        jTCodigo.setEditable(false);
    }//GEN-LAST:event_jBNuevoActionPerformed

    private void jBGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuardarActionPerformed
        if (jDCFechaInicio.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una fecha de inicio.");
            return;
        }

        if (jDCFechaFin.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una fecha de fin.");
            return;
        }

        if (jCTratamiento.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un tratamiento.");
            return;
        }

        if (jCConsultorio.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un consultorio.");
            return;
        }

        if (jCMasajista.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un masajista.");
            return;
        }

        DiaDeSpa pack = null;
        if (jCDiadespa.getSelectedItem() != null) {
            pack = (DiaDeSpa) jCDiadespa.getSelectedItem();
        }

        LocalDate fechaInicio = jDCFechaInicio.getDate()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        int horaInicio = Integer.parseInt(jCHoraInicio.getSelectedItem().toString());
        int minutoInicio = Integer.parseInt(jCMinutoInicio.getSelectedItem().toString());

        LocalDateTime inicio = LocalDateTime.of(fechaInicio,
                LocalTime.of(horaInicio, minutoInicio));

        LocalDate fechaFin = jDCFechaFin.getDate()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        int horaFin = Integer.parseInt(jCHoraFin.getSelectedItem().toString());
        int minutoFin = Integer.parseInt(jCMinutoFin.getSelectedItem().toString());

        LocalDateTime fin = LocalDateTime.of(fechaFin,
                LocalTime.of(horaFin, minutoFin));

        if (!fin.isAfter(inicio)) {
            JOptionPane.showMessageDialog(this,
                    "La fecha/hora de fin debe ser posterior al inicio.");
            return;
        }

        Tratamiento trat = (Tratamiento) jCTratamiento.getSelectedItem();
        Consultorio cons = (Consultorio) jCConsultorio.getSelectedItem();
        Masajista masaj = (Masajista) jCMasajista.getSelectedItem();

        boolean estado = jCBEstado.isSelected();

        //validad especialidad MASAJISTA con TRATAMIENTO
        if (!trat.getTipo().equalsIgnoreCase(masaj.getEspecialidad())) {
            JOptionPane.showMessageDialog(this,
                    "El masajista no coincide con la especialidad del tratamiento.");
            return;
        }

        SesionData sData = new SesionData();

        //validar que el consultorio este libre
        if (!sData.masajistaDisponible(masaj.getMatricula(), inicio, fin, null)) {
            JOptionPane.showMessageDialog(this,
                    "El masajista está ocupado en ese horario.");
            return;
        }

        //validar que el consultorio este libre 
        if (!sData.consultorioDisponible(cons.getNroConsultorio(), inicio, fin, null)) {
            JOptionPane.showMessageDialog(this,
                    "El consultorio está ocupado en ese horario.");
            return;
        }

        //crear y guardar sesion
        Sesion s = new Sesion();
        s.setFechaHoraInicio(inicio);
        s.setFechaHoraFin(fin);
        s.setEstado(estado);
        s.setCodigoTratam(trat);
        s.setNroConsutorio(cons);
        s.setMatricula(masaj);
        s.setCodPack(pack);

        sData.guardarSesion(s);

        jTCodigo.setText(String.valueOf(s.getCodSesion()));
        jBModificar.setEnabled(true);
        jBEliminar.setEnabled(true);
        limpiarCampos();
    }//GEN-LAST:event_jBGuardarActionPerformed

    private void jBModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBModificarActionPerformed

        if (jTCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Primero busque una sesión para modificar.");
            return;
        }

        if (jDCFechaInicio.getDate() == null || jDCFechaFin.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Complete ambas fechas.");
            return;
        }

        LocalDate fechaInicio = jDCFechaInicio.getDate()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        int horaInicio = Integer.parseInt(jCHoraInicio.getSelectedItem().toString());
        int minutoInicio = Integer.parseInt(jCMinutoInicio.getSelectedItem().toString());

        LocalDateTime inicio = LocalDateTime.of(
                fechaInicio,
                LocalTime.of(horaInicio, minutoInicio)
        );

        LocalDate fechaFin = jDCFechaFin.getDate()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        int horaFin = Integer.parseInt(jCHoraFin.getSelectedItem().toString());
        int minutoFin = Integer.parseInt(jCMinutoFin.getSelectedItem().toString());

        LocalDateTime fin = LocalDateTime.of(
                fechaFin,
                LocalTime.of(horaFin, minutoFin)
        );

        if (!fin.isAfter(inicio)) {
            JOptionPane.showMessageDialog(this, "La fecha/hora de fin debe ser posterior a la de inicio.");
            return;
        }

        Tratamiento trat = (Tratamiento) jCTratamiento.getSelectedItem();
        Consultorio cons = (Consultorio) jCConsultorio.getSelectedItem();
        Masajista masaj = (Masajista) jCMasajista.getSelectedItem();

        DiaDeSpa pack = null;
        if (jCDiadespa.getSelectedItem() != null) {
            pack = (DiaDeSpa) jCDiadespa.getSelectedItem();
        }

        boolean estado = jCBEstado.isSelected();

        int codSesionActual = Integer.parseInt(jTCodigo.getText());

        //VALIDAR ESPECIALIDAD
        if (!trat.getTipo().equalsIgnoreCase(masaj.getEspecialidad())) {
            JOptionPane.showMessageDialog(this,
                    "El masajista no coincide con la especialidad del tratamiento.");
            return;
        }

        SesionData sData = new SesionData();

        // masajista = 1 persona = 1 sesion = 1 consultorio para esa sesion
        // validar que el masajista no este ocupado
        if (!sData.masajistaDisponible(masaj.getMatricula(), inicio, fin, codSesionActual)) {
            JOptionPane.showMessageDialog(this,
                    "El masajista está ocupado en ese horario.");
            return;
        }

        //validar que el consultorio no este ocupado
        if (!sData.consultorioDisponible(cons.getNroConsultorio(), inicio, fin, codSesionActual)) {
            JOptionPane.showMessageDialog(this,
                    "El consultorio está ocupado en ese horario.");
            return;
        }

        //modficar sesion
        Sesion s = new Sesion();
        s.setCodSesion(codSesionActual);
        s.setFechaHoraInicio(inicio);
        s.setFechaHoraFin(fin);
        s.setEstado(estado);
        s.setCodigoTratam(trat);
        s.setNroConsutorio(cons);
        s.setMatricula(masaj);
        s.setCodPack(pack);

        sData.modificarSesion(s);

        JOptionPane.showMessageDialog(this, "Sesión modificada con éxito.");
    }//GEN-LAST:event_jBModificarActionPerformed

    private void jBBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscarActionPerformed
        if (jTBuscar.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un código para buscar.");
            return;
        }

        int codigo = Integer.parseInt(jTBuscar.getText());

        SesionData sd = new SesionData();
        Sesion s = sd.buscarSesion(codigo);

        if (s == null) {
            JOptionPane.showMessageDialog(this, "No existe una sesión con ese código.");
            return;
        }

        jTCodigo.setText(String.valueOf(s.getCodSesion()));

        // FECHA INICIO - FIN
        jDCFechaInicio.setDate(Date.from(
                s.getFechaHoraInicio().atZone(ZoneId.systemDefault()).toInstant()
        ));

        jDCFechaFin.setDate(Date.from(
                s.getFechaHoraFin().atZone(ZoneId.systemDefault()).toInstant()
        ));

        //HORAS (sin ceros adelante pq explota y rompemo todo)
        String horaInicioStr = String.valueOf(s.getFechaHoraInicio().getHour());
        String horaFinStr = String.valueOf(s.getFechaHoraFin().getHour());

        jCHoraInicio.setSelectedItem(horaInicioStr);
        jCHoraFin.setSelectedItem(horaFinStr);

        String minutoInicioStr = String.format("%02d", s.getFechaHoraInicio().getMinute());
        String minutoFinStr = String.format("%02d", s.getFechaHoraFin().getMinute());

        jCMinutoInicio.setSelectedItem(minutoInicioStr);
        jCMinutoFin.setSelectedItem(minutoFinStr);

        for (int i = 0; i < jCTratamiento.getItemCount(); i++) {
            Tratamiento t = jCTratamiento.getItemAt(i);
            if (t.getCodTratam() == s.getCodigoTratam().getCodTratam()) {
                jCTratamiento.setSelectedIndex(i);
                break;
            }
        }

        // CONSULTORIO
        for (int i = 0; i < jCConsultorio.getItemCount(); i++) {
            Consultorio c = jCConsultorio.getItemAt(i);
            if (c.getNroConsultorio() == s.getNroConsutorio().getNroConsultorio()) {
                jCConsultorio.setSelectedIndex(i);
                break;
            }
        }

        // MASAJISTA
        for (int i = 0; i < jCMasajista.getItemCount(); i++) {
            Masajista m = jCMasajista.getItemAt(i);
            if (m.getMatricula() == s.getMatricula().getMatricula()) {
                jCMasajista.setSelectedIndex(i);
                break;
            }
        }

        // DIA DE SPA
        if (s.getCodPack() != null) {
            for (int i = 0; i < jCDiadespa.getItemCount(); i++) {
                DiaDeSpa d = jCDiadespa.getItemAt(i);
                if (d != null && d.getCodPack() == s.getCodPack().getCodPack()) {
                    jCDiadespa.setSelectedIndex(i);
                    break;
                }
            }
        } else {
            jCDiadespa.setSelectedIndex(0);
        }

        jCBEstado.setSelected(s.isEstado());

        jBModificar.setEnabled(true);
        jBEliminar.setEnabled(true);
    }//GEN-LAST:event_jBBuscarActionPerformed

    private void jBEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEliminarActionPerformed
        if (jTCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Busque una sesión a eliminar.");
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar esta sesión permanentemente?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        int codigo = Integer.parseInt(jTCodigo.getText());

        SesionData sd = new SesionData();
        sd.eliminarSesion(codigo);

        JOptionPane.showMessageDialog(this, "Sesión eliminada con éxito.");

        limpiarCampos();
    }//GEN-LAST:event_jBEliminarActionPerformed

    private void jBListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBListarActionPerformed
        cargarTablaGeneral();
    }//GEN-LAST:event_jBListarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBBuscar;
    private javax.swing.JButton jBEliminar;
    private javax.swing.JButton jBGuardar;
    private javax.swing.JButton jBListar;
    private javax.swing.JButton jBModificar;
    private javax.swing.JButton jBNuevo;
    private javax.swing.JCheckBox jCBEstado;
    private javax.swing.JComboBox<Consultorio> jCConsultorio;
    private javax.swing.JComboBox<DiaDeSpa> jCDiadespa;
    private javax.swing.JComboBox<String> jCHoraFin;
    private javax.swing.JComboBox<String> jCHoraInicio;
    private javax.swing.JComboBox<Masajista> jCMasajista;
    private javax.swing.JComboBox<String> jCMinutoFin;
    private javax.swing.JComboBox<String> jCMinutoInicio;
    private javax.swing.JComboBox<Tratamiento> jCTratamiento;
    private com.toedter.calendar.JDateChooser jDCFechaFin;
    private com.toedter.calendar.JDateChooser jDCFechaInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTBuscar;
    private javax.swing.JTextField jTCodigo;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
