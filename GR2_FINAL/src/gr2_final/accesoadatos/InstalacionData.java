/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gr2_final.accesoadatos;

import gr2_final.entidades.Instalacion;
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
 * @author KEVIN
 */
public class InstalacionData {
     private Connection con = null;

    public InstalacionData() {
        con = Conexion.getConexion();
    }
    
    public void guardarInstalacion(Instalacion inst) {
        String sql = "INSERT INTO instalacion (nombre, detalleUso, precio30m, estado) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, inst.getNombre());
            ps.setString(2, inst.getDetalleUso());
            ps.setDouble(3, inst.getPrecio30m());
            ps.setBoolean(4, inst.isEstado());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                inst.setCodInst(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "Instalacion agregada con exito.");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar instalación: " + e.getMessage());
        }
    }
    // Busca por cod
    public Instalacion buscarInstalacion(int codInst) {
        Instalacion inst = null;
        String sql = "SELECT * FROM instalacion WHERE codInst = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codInst);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                inst = new Instalacion();
                inst.setCodInst(codInst);
                inst.setNombre(rs.getString("nombre"));
                inst.setDetalleUso(rs.getString("detalleUso"));
                inst.setPrecio30m(rs.getDouble("precio30m"));
                inst.setEstado(rs.getBoolean("estado"));
            } else {
                JOptionPane.showMessageDialog(null, "️No existe instalacion con ese codigo.");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar instalacion: " + e.getMessage());
        }
        return inst;
    }
    // crea listas de las activas
    public List<Instalacion> listarInstalacionesActivas() {
        List<Instalacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM instalacion WHERE estado = 1";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Instalacion inst = new Instalacion();
                inst.setCodInst(rs.getInt("codInst"));
                inst.setNombre(rs.getString("nombre"));
                inst.setDetalleUso(rs.getString("detalleUso"));
                inst.setPrecio30m(rs.getDouble("precio30m"));
                inst.setEstado(rs.getBoolean("estado"));
                lista.add(inst);
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar instalaciones: " + e.getMessage());
        }
        return lista;
    }
    // modifica la instalacion
    public void modificarInstalacion(Instalacion inst) {
        String sql = "UPDATE instalacion SET nombre = ?, detalleUso = ?, precio30m = ?, estado = ? WHERE codInst = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, inst.getNombre());
            ps.setString(2, inst.getDetalleUso());
            ps.setDouble(3, inst.getPrecio30m());
            ps.setBoolean(4, inst.isEstado());
            ps.setInt(5, inst.getCodInst());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Instalacion modificada con exito.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la instalacion.");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar instalacin: " + e.getMessage());
        }
    }
    // realiza la baja logica
    public void bajaLogicaInstalacion(int codInst) {
        String sql = "UPDATE instalacion SET estado = false WHERE codInst = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codInst);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Instalacion dada de baja.");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en baja logica: " + e.getMessage());
        }
    }
    // alta logica
    public void altaLogicaInstalacion(int codInst) {
        String sql = "UPDATE instalacion SET estado = true WHERE codInst = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codInst);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Instalacion reactivada.");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en alta logica: " + e.getMessage());
        }
    }
     // y aca elimina definitivo
    public void borrarInstalacion(int codInst) {
        String sql = "DELETE FROM instalacion WHERE codInst = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codInst);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Instalacion eliminada permanentemente.");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al borrar instalacion: " + e.getMessage());
        }
    }

}
