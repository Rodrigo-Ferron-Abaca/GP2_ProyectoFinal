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

import gr2_final.entidades.SesionInstalacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class SesionInstalacionData {

    private Connection con = null;

    public SesionInstalacionData() {
        con = Conexion.getConexion();
    }

    public void guardarSesionInstalacion(SesionInstalacion si) {
        String sql = "INSERT INTO sesion_instalacion (codSesion, codInstal) VALUES (?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, si.getCodSesion());
            ps.setInt(2, si.getCodInstal());   // usa codInstal
            ps.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar sesion_instalacion: " + e.getMessage());
        }
    }

    public List<SesionInstalacion> listarInstalacionesDeSesion(int codSesion) {
        List<SesionInstalacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM sesion_instalacion WHERE codSesion = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codSesion);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SesionInstalacion si = new SesionInstalacion();
                    si.setCodSesion(rs.getInt("codSesion"));
                    si.setCodInstal(rs.getInt("codInstal")); // usa codInstal
                    lista.add(si);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar instalaciones de sesión: " + e.getMessage());
        }

        return lista;
    }

    public void eliminarInstalacionesDeSesion(int codSesion) {
        String sql = "DELETE FROM sesion_instalacion WHERE codSesion = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codSesion);
            ps.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar instalaciones de sesión: " + e.getMessage());
        }
    }
}