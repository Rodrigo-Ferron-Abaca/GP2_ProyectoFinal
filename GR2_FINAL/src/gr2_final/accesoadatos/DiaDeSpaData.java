/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr2_final.accesoadatos;

/**
 *
 * @author rodrigo
 */
import gr2_final.entidades.Cliente;
import gr2_final.entidades.DiaDeSpa;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class DiaDeSpaData {
    private Connection con = null;
    private ClienteData clienteData = new ClienteData();

    public DiaDeSpaData() {
        con = Conexion.getConexion();
    }

    public void guardarDiaDeSpa(DiaDeSpa diaDeSpa) {
        String sql = "INSERT INTO diadespa (fechaHora, preferencias, monto, estado, codCli) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDate(1, Date.valueOf(diaDeSpa.getFechaHora())); 
            ps.setString(2, diaDeSpa.getPreferencias());
            ps.setDouble(3, diaDeSpa.getMonto());
            ps.setBoolean(4, diaDeSpa.isEstado());
            ps.setInt(5, diaDeSpa.getCodCli().getCodCli()); 
            
            int exito = ps.executeUpdate();
            
            if (exito == 1) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        diaDeSpa.setCodPack(rs.getInt(1));
                        JOptionPane.showMessageDialog(null, "Día de Spa Cód. " + diaDeSpa.getCodPack() + " generado con éxito.");
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el Día de Spa: " + ex.getMessage());
        }
    }

    public void modificarDiaDeSpa(DiaDeSpa diaDeSpa) {
        String sql = "UPDATE diadespa SET fechaHora = ?, preferencias = ?, monto = ?, estado = ?, codCli = ? WHERE codPack = ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(diaDeSpa.getFechaHora()));
            ps.setString(2, diaDeSpa.getPreferencias());
            ps.setDouble(3, diaDeSpa.getMonto());
            ps.setBoolean(4, diaDeSpa.isEstado());
            ps.setInt(5, diaDeSpa.getCodCli().getCodCli()); 
            ps.setInt(6, diaDeSpa.getCodPack());
            
            int exito = ps.executeUpdate();
            
            if (exito == 1) {
                JOptionPane.showMessageDialog(null, "Día de Spa modificado con éxito.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el Día de Spa para modificar.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al modificar el Día de Spa: " + ex.getMessage());
        }
    }
    
    public void eliminarDiaDeSpa(int codPack) {
        String sql = "UPDATE diadespa SET estado = 0 WHERE codPack = ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codPack);
            int exito = ps.executeUpdate();
            
            if (exito == 1) {
                JOptionPane.showMessageDialog(null, "Día de Spa dado de baja (lógica) con éxito.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el Día de Spa para eliminar.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al realizar la baja del Día de Spa: " + ex.getMessage());
        }
    }

    public DiaDeSpa buscarDiaDeSpa(int codPack) {
        DiaDeSpa diaDeSpa = null;
        String sql = "SELECT fechaHora, preferencias, monto, estado, codCli FROM diadespa WHERE codPack = ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codPack);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    diaDeSpa = new DiaDeSpa();
                    diaDeSpa.setCodPack(codPack);
                    diaDeSpa.setFechaHora(rs.getDate("fechaHora").toLocalDate()); 
                    diaDeSpa.setPreferencias(rs.getString("preferencias"));
                    diaDeSpa.setMonto(rs.getDouble("monto"));
                    diaDeSpa.setEstado(rs.getBoolean("estado"));
                    
                    int codCli = rs.getInt("codCli");
                    Cliente cliente = clienteData.buscarClientePorCod(codCli);
                    diaDeSpa.setCodCli(cliente);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar el Día de Spa: " + ex.getMessage());
        }
        return diaDeSpa;
    }
    
    public List<DiaDeSpa> listarDiasDeSpa() {
        List<DiaDeSpa> diasDeSpa = new ArrayList<>();
        String sql = "SELECT codPack, fechaHora, preferencias, monto, estado, codCli FROM diadespa WHERE estado = 1";
        
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                DiaDeSpa diaDeSpa = new DiaDeSpa();
                diaDeSpa.setCodPack(rs.getInt("codPack"));
                diaDeSpa.setFechaHora(rs.getDate("fechaHora").toLocalDate());
                diaDeSpa.setPreferencias(rs.getString("preferencias"));
                diaDeSpa.setMonto(rs.getDouble("monto"));
                diaDeSpa.setEstado(rs.getBoolean("estado"));
                
                int codCli = rs.getInt("codCli");
                Cliente cliente = clienteData.buscarClientePorCod(codCli);
                diaDeSpa.setCodCli(cliente);
                
                diasDeSpa.add(diaDeSpa);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar los Días de Spa: " + ex.getMessage());
        }
        return diasDeSpa;
    }
    
    public List<DiaDeSpa> listarDiasDeSpaPorFecha(LocalDate fecha) {
        List<DiaDeSpa> diasDeSpa = new ArrayList<>();
        String sql = "SELECT codPack, fechaHora, preferencias, monto, estado, codCli FROM diadespa WHERE fechaHora = ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(fecha));
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DiaDeSpa diaDeSpa = new DiaDeSpa();
                    diaDeSpa.setCodPack(rs.getInt("codPack"));
                    diaDeSpa.setFechaHora(rs.getDate("fechaHora").toLocalDate());
                    diaDeSpa.setPreferencias(rs.getString("preferencias"));
                    diaDeSpa.setMonto(rs.getDouble("monto"));
                    diaDeSpa.setEstado(rs.getBoolean("estado"));
                    
                    int codCli = rs.getInt("codCli");
                    Cliente cliente = clienteData.buscarClientePorCod(codCli);
                    diaDeSpa.setCodCli(cliente);
                    
                    diasDeSpa.add(diaDeSpa);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar Días de Spa por fecha: " + ex.getMessage());
        }
        return diasDeSpa;
    }
}