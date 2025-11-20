/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package gr2_final.vistas;

import gr2_final.accesoadatos.ClienteData;
import gr2_final.accesoadatos.DiaDeSpaData;
import gr2_final.accesoadatos.MasajistaData;
import gr2_final.accesoadatos.SesionData;
import gr2_final.entidades.Cliente;
import gr2_final.entidades.DiaDeSpa;
import gr2_final.entidades.Masajista;
import gr2_final.entidades.Sesion;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author KEVIN
 */
public class DiaDeSpaVista extends javax.swing.JInternalFrame {

    private DiaDeSpaData dsData = new DiaDeSpaData();
    private ClienteData cData = new ClienteData();
    private MasajistaData mData = new MasajistaData();
    private DefaultTableModel modeloSpa;
    private DefaultTableModel modeloA2;

    /**
     * Creates new form DiaDeSpaVista
     */
    public DiaDeSpaVista() {
        initComponents();
        modeloSpa = new DefaultTableModel();
        modeloA2 = new DefaultTableModel();
        armarCabeceraSpa();
        armarCabeceraA2();
        cargarClientes();
        cargarHoras();
        cargarTablaGeneralSpa();
        jTCodigo.setEditable(false);
        jTMonto.setEditable(false);

        jCCliente.addActionListener(e -> recalcularMontoAutomatico());

        jDChoserFechas.getDateEditor().addPropertyChangeListener(evt -> {
            if ("date".equals(evt.getPropertyName())) {
                recalcularMontoAutomatico();
            }
        });

        jCHora.addActionListener(e -> recalcularMontoAutomatico());
    }

    private void armarCabeceraSpa() {
        modeloSpa.addColumn("Código");
        modeloSpa.addColumn("Cliente");
        modeloSpa.addColumn("Fecha - Hora");
        modeloSpa.addColumn("Monto");
        modeloSpa.addColumn("Estado");
        jTablaSpa.setModel(modeloSpa);
    }

    private void armarCabeceraA2() {
        modeloA2.addColumn("Cod Sesión");
        modeloA2.addColumn("Inicio");
        modeloA2.addColumn("Fin");
        modeloA2.addColumn("Tratamiento");
        modeloA2.addColumn("Consultorio");
        modeloA2.addColumn("Masajista");
        modeloA2.addColumn("Estado");

        jTable2.setModel(modeloA2);
    }

    private void limpiarTablaA2() {
        modeloA2.setRowCount(0);
    }

    private void cargarClientes() {
        List<Cliente> lista = cData.listaClientes();
        for (Cliente c : lista) {
            jCCliente.addItem(c);
        }
    }

    private void cargarHoras() {
        jCHora.removeAllItems();
        for (int h = 8; h <= 21; h++) {
            jCHora.addItem(String.format("%02d:00", h));
        }
    }

    private LocalDateTime obtenerFechaHora() {

        Date fecha = jDChoserFechas.getDate();
        if (fecha == null) {
            return null;
        }

        LocalDate f = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        String horaSel = (String) jCHora.getSelectedItem();
        if (horaSel == null) {
            return null;
        }

        LocalTime hora = LocalTime.parse(horaSel);

        return LocalDateTime.of(f, hora);
    }

    private void cargarTablaGeneralSpa() {
        modeloSpa.setRowCount(0);
        List<DiaDeSpa> lista = dsData.listarDiasDeSpa();

        for (DiaDeSpa ds : lista) {

            // Calcular monto dinámico actual
            double monto = calcularMontoDiaSpa(ds.getCodPack());

            modeloSpa.addRow(new Object[]{
                ds.getCodPack(),
                ds.getCodCli().getNombreCompleto(),
                ds.getFechaHora(),
                monto,
                ds.isEstado() ? "Activo" : "Anulado"
            });
        }
    }

    private double calcularMontoDiaSpa(int codPack) {

        SesionData sd = new SesionData();
        List<Sesion> sesiones = sd.listarSesionesPorPack(codPack);

        double total = 0;

        for (Sesion s : sesiones) {

            long minutos = java.time.Duration.between(
                    s.getFechaHoraInicio(),
                    s.getFechaHoraFin()
            ).toMinutes();

            int unidades = (int) Math.ceil(minutos / 30.0);

            double precio = s.getCodigoTratam().getCosto();

            total += unidades * precio;
        }

        return total;
    }

    private void recalcularMontoAutomatico() {

        if (jTCodigo.getText().isEmpty()) {
            return; // no hay pack seleccionado aún
        }

        int codPack = Integer.parseInt(jTCodigo.getText());

        // recalcular
        double total = calcularMontoDiaSpa(codPack);

        // mostrarlo
        jTMonto.setText(String.valueOf(total));

        // refrescar la tabla principal
        cargarTablaGeneralSpa();
    }

    private void recalcularMontoSiHayPack() {
        if (jTCodigo.getText().isEmpty()) {
            return;
        }

        int codPack = Integer.parseInt(jTCodigo.getText());

        double monto = calcularMontoDiaSpa(codPack);

        jTMonto.setText(String.valueOf(monto));

        // Actualizar la tabla general
        cargarTablaGeneralSpa();
    }

    private void limpiarCampos() {
        jTCodigo.setText("");
        jTMonto.setText("");
        jTPreferencias.setText("");
        jDChoserFechas.setDate(null);
        jCHora.setSelectedIndex(-1);
        modeloA2.setRowCount(0);
    }

    private void listarMasajistasLibres() {

        limpiarTablaA2();

        LocalDateTime fechaHora = obtenerFechaHora();
        if (fechaHora == null) {
            JOptionPane.showMessageDialog(this, "Seleccione fecha y hora.");
            return;
        }

        Date inicio = Date.from(fechaHora.atZone(ZoneId.systemDefault()).toInstant());
        Date fin = Date.from(fechaHora.plusHours(1).atZone(ZoneId.systemDefault()).toInstant());

        List<Masajista> libres = mData.listarMasajistasLibresEntre(inicio, fin);

        for (Masajista m : libres) {
            modeloA2.addRow(new Object[]{
                m.getMatricula(),
                m.getNombreCompleto(),
                m.getEspecialidad(),
                m.getTelefono()
            });
        }

        if (libres.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay masajistas libres en ese horario.");
        }

    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTCodigo = new javax.swing.JTextField();
        jCCliente = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTMonto = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTPreferencias = new javax.swing.JTextArea();
        jCheckActivo = new javax.swing.JCheckBox();
        jBNuevo = new javax.swing.JButton();
        jBGuardar = new javax.swing.JButton();
        jBModificar = new javax.swing.JButton();
        jBEliminar = new javax.swing.JButton();
        jBuscar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTablaSpa = new javax.swing.JTable();
        jBBuscarCodPack = new javax.swing.JButton();
        jDChoserFechas = new com.toedter.calendar.JDateChooser();
        jCHora = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jTBuscarCodPack = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

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

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setText("DIA DE SPA");

        jLabel2.setText("Codigo:");

        jLabel3.setText("Cliente:");

        jLabel4.setText("Fecha y hora:");

        jLabel5.setText("Monto:");

        jLabel6.setText("Preferencias:");

        jTPreferencias.setColumns(20);
        jTPreferencias.setRows(5);
        jScrollPane1.setViewportView(jTPreferencias);

        jCheckActivo.setText("Activo");

        jBNuevo.setText("Nuevo");
        jBNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBNuevoActionPerformed(evt);
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

        jBEliminar.setText("Eliminar");
        jBEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEliminarActionPerformed(evt);
            }
        });

        jBuscar.setText("Buscar");
        jBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBuscarActionPerformed(evt);
            }
        });

        jTablaSpa.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTablaSpa);

        jBBuscarCodPack.setText("Buscar info");
        jBBuscarCodPack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscarCodPackActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(jTable2);

        jLabel7.setText("Mostrar info de Dia de Spa");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(174, 174, 174)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckActivo)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel7))
                                .addGap(48, 48, 48)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jCCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1)
                                    .addComponent(jTCodigo)
                                    .addComponent(jTMonto)
                                    .addComponent(jScrollPane1)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jDChoserFechas, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                                        .addComponent(jCHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jTBuscarCodPack))
                                .addGap(60, 60, 60)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jBModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jBEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jBGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jBNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jBBuscarCodPack)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 791, Short.MAX_VALUE)
                            .addComponent(jScrollPane4))))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel1)
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBNuevo))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jBGuardar))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jBModificar))
                    .addComponent(jDChoserFechas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBEliminar))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBuscar))
                .addGap(14, 14, 14)
                .addComponent(jCheckActivo)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBBuscarCodPack)
                    .addComponent(jTBuscarCodPack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(109, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuardarActionPerformed
        try {
            LocalDateTime fechaHora = obtenerFechaHora();
            if (fechaHora == null) {
                JOptionPane.showMessageDialog(this, "Seleccione fecha y hora.");
                return;
            }

            DiaDeSpa ds = new DiaDeSpa();
            ds.setCodCli((Cliente) jCCliente.getSelectedItem());
            ds.setFechaHora(fechaHora);
            ds.setPreferencias(jTPreferencias.getText());
            ds.setEstado(true);

            dsData.guardarDiaDeSpa(ds);

            JOptionPane.showMessageDialog(this, "Día de Spa guardado.");
            limpiarCampos();
            cargarTablaGeneralSpa();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage());
        }
    }//GEN-LAST:event_jBGuardarActionPerformed

    private void jBModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBModificarActionPerformed
        try {
            LocalDateTime fechaHora = obtenerFechaHora();
            if (fechaHora == null) {
                JOptionPane.showMessageDialog(this, "Seleccione fecha y hora.");
                return;
            }

            int codigo;
            try {
                codigo = Integer.parseInt(jTCodigo.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Código inválido.");
                return;
            }

            Cliente cli = (Cliente) jCCliente.getSelectedItem();
            if (cli == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente.");
                return;
            }

            DiaDeSpa ds = new DiaDeSpa();
            ds.setCodPack(codigo);
            ds.setCodCli(cli);
            ds.setFechaHora(fechaHora);
            ds.setPreferencias(jTPreferencias.getText());
            ds.setMonto(Double.parseDouble(jTMonto.getText()));
            ds.setEstado(jCheckActivo.isSelected());

            dsData.modificarDiaDeSpa(ds);

            JOptionPane.showMessageDialog(this, "Día de Spa modificado");
            cargarTablaGeneralSpa();

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Monto inválido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al modificar: " + e.getMessage());
        }
    }//GEN-LAST:event_jBModificarActionPerformed

    private void jBEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEliminarActionPerformed
        try {
            int codigo = Integer.parseInt(jTCodigo.getText());
            dsData.eliminarDiaDeSpa(codigo);

            JOptionPane.showMessageDialog(this, "Día de Spa eliminado");
            cargarTablaGeneralSpa();

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Código inválido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
        }
    }//GEN-LAST:event_jBEliminarActionPerformed

    private void jBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBuscarActionPerformed
        try {
            int cod = Integer.parseInt(jTCodigo.getText());
            DiaDeSpa ds = dsData.buscarDiaDeSpa(cod);

            if (ds == null) {
                JOptionPane.showMessageDialog(this, "No existe el Día de Spa.");
                return;
            }

            jCCliente.setSelectedItem(ds.getCodCli());
            jTPreferencias.setText(ds.getPreferencias());
            jTMonto.setText(String.valueOf(ds.getMonto()));

            // convertir LocalDateTime a Date para el JDateChooser
            Date fecha = Date.from(ds.getFechaHora().atZone(ZoneId.systemDefault()).toInstant());
            jDChoserFechas.setDate(fecha);

            // cargar hora en combo
            jCHora.setSelectedItem(String.format("%02d:00", ds.getFechaHora().getHour()));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al buscar.");
        }
    }//GEN-LAST:event_jBuscarActionPerformed

    private void jBBuscarCodPackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscarCodPackActionPerformed
        if (jTBuscarCodPack.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un código de Pack.");
            return;
        }

        int codPack = Integer.parseInt(jTBuscarCodPack.getText());

        DiaDeSpaData dd = new DiaDeSpaData();
        DiaDeSpa pack = dd.buscarDiaDeSpa(codPack);

        if (pack == null) {
            JOptionPane.showMessageDialog(this, "No existe un Día de Spa con ese código.");
            return;
        }

        jCCliente.setSelectedItem(pack.getCodCli());

        // Cargar fecha
        java.util.Date fecha = java.util.Date.from(
                pack.getFechaHora().atZone(ZoneId.systemDefault()).toInstant()
        );
        jDChoserFechas.setDate(fecha);

        jCHora.setSelectedItem(pack.getFechaHora().getHour());

        jTPreferencias.setText(pack.getPreferencias());

        jTMonto.setText(String.valueOf(pack.getMonto()));

        // Estado
        jCheckActivo.setSelected(pack.isEstado());

        limpiarTablaA2(); // tu método actual

        SesionData sd = new SesionData();
        List<Sesion> sesiones = sd.listarSesionesPorPack(codPack);

        for (Sesion s : sesiones) {

            modeloA2.addRow(new Object[]{
                s.getCodSesion(),
                s.getFechaHoraInicio(),
                s.getFechaHoraFin(),
                s.getCodigoTratam().getNombre(),
                s.getNroConsutorio().getNroConsultorio(),
                s.getMatricula().getNombreCompleto(),
                s.isEstado() ? "Activa" : "Inactiva"
            });
        }
    }//GEN-LAST:event_jBBuscarCodPackActionPerformed

    private void jBNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNuevoActionPerformed
        jTCodigo.setEditable(false);
        jTMonto.setEditable(false);
        jCCliente.setSelectedIndex(-1);
        jDChoserFechas.setDate(null);
        jCHora.setSelectedIndex(-1);
        jTPreferencias.setText("");
        jBEliminar.setEnabled(false);
        jBModificar.setEnabled(false);
    }//GEN-LAST:event_jBNuevoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBBuscarCodPack;
    private javax.swing.JButton jBEliminar;
    private javax.swing.JButton jBGuardar;
    private javax.swing.JButton jBModificar;
    private javax.swing.JButton jBNuevo;
    private javax.swing.JButton jBuscar;
    private javax.swing.JComboBox<Cliente> jCCliente;
    private javax.swing.JComboBox<String> jCHora;
    private javax.swing.JCheckBox jCheckActivo;
    private com.toedter.calendar.JDateChooser jDChoserFechas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTBuscarCodPack;
    private javax.swing.JTextField jTCodigo;
    private javax.swing.JTextField jTMonto;
    private javax.swing.JTextArea jTPreferencias;
    private javax.swing.JTable jTablaSpa;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
