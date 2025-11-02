package gr2_final.accesoadatos;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import gr2_final.entidades.Tratamiento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author rodrigo
 */

public class TratamientoData {
    private Connection con = null;

    public TratamientoData() {
        con = Conexion.getConexion();
    }

    public void guardarTratamiento(Tratamiento tratamiento) {
        String sql = "INSERT INTO tratamiento (nombre, tipo, detalle, duracion, costo, activo) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, tratamiento.getNombre());
            ps.setString(2, tratamiento.getTipo());
            ps.setString(3, tratamiento.getDetalle());
            ps.setInt(4, tratamiento.getDuracion());
            ps.setDouble(5, tratamiento.getCosto());
            ps.setBoolean(6, tratamiento.isActivo()); 
            
            int exito = ps.executeUpdate();
            
            if (exito == 1) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        tratamiento.setCodTratam(rs.getInt(1));
                        JOptionPane.showMessageDialog(null, "Tratamiento Cód. " + tratamiento.getCodTratam() + " guardado con éxito.");
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar el tratamiento: " + ex.getMessage());
        }
    }

    public void modificarTratamiento(Tratamiento tratamiento) {
        String sql = "UPDATE tratamiento SET nombre = ?, tipo = ?, detalle = ?, duracion = ?, costo = ?, activo = ? WHERE codTratam = ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tratamiento.getNombre());
            ps.setString(2, tratamiento.getTipo());
            ps.setString(3, tratamiento.getDetalle());
            ps.setInt(4, tratamiento.getDuracion());
            ps.setDouble(5, tratamiento.getCosto());
            ps.setBoolean(6, tratamiento.isActivo());
            ps.setInt(7, tratamiento.getCodTratam());
            
            int exito = ps.executeUpdate();
            
            if (exito == 1) {
                JOptionPane.showMessageDialog(null, "Tratamiento modificado con éxito.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el tratamiento para modificar.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al modificar el tratamiento: " + ex.getMessage());
        }
    }
    
    public void eliminarTratamiento(int codTratam) {
        String sql = "UPDATE tratamiento SET activo = 0 WHERE codTratam = ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codTratam);
            int exito = ps.executeUpdate();
            
            if (exito == 1) {
                JOptionPane.showMessageDialog(null, "Tratamiento dado de baja (lógica) con éxito.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el tratamiento para eliminar.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al realizar la baja del tratamiento: " + ex.getMessage());
        }
    }

    public Tratamiento buscarTratamiento(int codTratam) {
        Tratamiento tratamiento = null;
        String sql = "SELECT nombre, tipo, detalle, duracion, costo, activo FROM tratamiento WHERE codTratam = ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codTratam);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    tratamiento = new Tratamiento();
                    tratamiento.setCodTratam(codTratam);
                    tratamiento.setNombre(rs.getString("nombre"));
                    tratamiento.setTipo(rs.getString("tipo"));
                    tratamiento.setDetalle(rs.getString("detalle"));
                    tratamiento.setDuracion(rs.getInt("duracion"));
                    tratamiento.setCosto(rs.getDouble("costo"));
                    tratamiento.setActivo(rs.getBoolean("activo"));
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar el tratamiento: " + ex.getMessage());
        }
        return tratamiento;
    }
    
    public List<Tratamiento> listarTratamientos() {
        List<Tratamiento> tratamientos = new ArrayList<>();
        String sql = "SELECT codTratam, nombre, tipo, detalle, duracion, costo, activo FROM tratamiento WHERE activo = 1";
        
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Tratamiento tratamiento = new Tratamiento();
                tratamiento.setCodTratam(rs.getInt("codTratam"));
                tratamiento.setNombre(rs.getString("nombre"));
                tratamiento.setTipo(rs.getString("tipo"));
                tratamiento.setDetalle(rs.getString("detalle"));
                tratamiento.setDuracion(rs.getInt("duracion"));
                tratamiento.setCosto(rs.getDouble("costo"));
                tratamiento.setActivo(rs.getBoolean("activo")); 
                
                tratamientos.add(tratamiento);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar tratamientos: " + ex.getMessage());
        }
        return tratamientos;
    }
    
    public List<Tratamiento> listarTratamientosPorTipo(String tipo) {
        List<Tratamiento> tratamientos = new ArrayList<>();
        String sql = "SELECT codTratam, nombre, tipo, detalle, duracion, costo, activo FROM tratamiento WHERE tipo = ? AND activo = 1";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipo);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Tratamiento tratamiento = new Tratamiento();
                    tratamiento.setCodTratam(rs.getInt("codTratam"));
                    tratamiento.setNombre(rs.getString("nombre"));
                    tratamiento.setTipo(rs.getString("tipo"));
                    tratamiento.setDetalle(rs.getString("detalle"));
                    tratamiento.setDuracion(rs.getInt("duracion"));
                    tratamiento.setCosto(rs.getDouble("costo"));
                    tratamiento.setActivo(rs.getBoolean("activo")); 
                    
                    tratamientos.add(tratamiento);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar tratamientos por tipo: " + ex.getMessage());
        }
        return tratamientos;
    }
}