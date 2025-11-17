/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gr2_final.accesoadatos;

import gr2_final.entidades.Masajista;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author KEVIN
 */
public class MasajistaData {

    private Connection con = null;

    public MasajistaData() {
        con = Conexion.getConexion();

    }

    // guarda masajista
    public void guardarMasajista(Masajista m) {
        String sql = "INSERT INTO masajista (matricula, nombreCompleto, telefono, especialidad, estado) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, m.getMatricula());
            ps.setString(2, m.getNombreCompleto());
            ps.setString(3, m.getTelefono());
            ps.setString(4, m.getEspecialidad());
            ps.setBoolean(5, m.isEstado());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Masajista agregado con exito.");
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar masajista: " + e.getMessage());
        }
    }

    // busca por matricula
    public Masajista buscarMasajista(int matricula) {
        Masajista m = null;
        String sql = "SELECT * FROM masajista WHERE matricula = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, matricula);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                m = new Masajista();
                m.setMatricula(matricula);
                m.setNombreCompleto(rs.getString("nombreCompleto"));
                m.setTelefono(rs.getString("telefono"));
                m.setEspecialidad(rs.getString("especialidad"));
                m.setEstado(rs.getBoolean("estado"));
            } else {
                JOptionPane.showMessageDialog(null, "No existe masajista con esa matricula.");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar masajista: " + e.getMessage());
        }
        return m;
    }

    // para modificar
    public void modificarMasajista(Masajista m) {
        String sql = "UPDATE masajista SET nombreCompleto = ?, telefono = ?, especialidad = ?, estado = ? WHERE matricula = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, m.getNombreCompleto());
            ps.setString(2, m.getTelefono());
            ps.setString(3, m.getEspecialidad());
            ps.setBoolean(4, m.isEstado());
            ps.setInt(5, m.getMatricula());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Masajista modificado correctamente.");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar masajista: " + e.getMessage());
        }
    }

    // para hacer la baja logica
    public void bajaLogicaMasajista(int matricula) {
        String sql = "UPDATE masajista SET estado = false WHERE matricula = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, matricula);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Masajista dado de baja.");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en baja logica: " + e.getMessage());
        }
    }

    // para dar de alta logica
    public void altaLogicaMasajista(int matricula) {
        String sql = "UPDATE masajista SET estado = true WHERE matricula = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, matricula);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Masajista reactivado correctamente.");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en alta logica: " + e.getMessage());
        }
    }

    // para borrar definitivo
    public void borrarMasajista(int matricula) {
        String sql = "DELETE FROM masajista WHERE matricula = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, matricula);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Masajista eliminado permanentemente.");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al borrar masajista: " + e.getMessage());
        }
    }

        // hace lista de activos
    public List<Masajista> listarMasajistasActivos() {
        List<Masajista> lista = new ArrayList<>();
        String sql = "SELECT * FROM masajista WHERE estado = 1";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Masajista m = new Masajista();
                m.setMatricula(rs.getInt("matricula"));
                m.setNombreCompleto(rs.getString("nombreCompleto"));
                m.setTelefono(rs.getString("telefono"));
                m.setEspecialidad(rs.getString("especialidad"));
                m.setEstado(rs.getBoolean("estado"));
                lista.add(m);
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar masajistas: " + e.getMessage());
        }
        return lista;
    }
    
    public List<Masajista> listarMasajistasPorEspecialidad(String especialidad) {
        List<Masajista> masajistas = new ArrayList<>();
        String sql = "SELECT * FROM masajista WHERE especialidad = ? AND estado = 1";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, especialidad);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Masajista m = new Masajista();
                m.setMatricula(rs.getInt("matricula"));
                m.setNombreCompleto(rs.getString("nombreCompleto"));
                m.setTelefono(rs.getString("telefono"));
                m.setEspecialidad(rs.getString("especialidad"));
                m.setEstado(rs.getBoolean("estado"));
                masajistas.add(m);
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar masajistas por especialidad: " + ex.getMessage());
        }
        return masajistas;
    }

    public List<Masajista> listarMasajistasLibresEntre(Date inicio, Date fin) {
        List<Masajista> libres = new ArrayList<>();
        String sql = "SELECT * FROM masajista m WHERE m.estado = 1 AND m.matricula NOT IN ("
                + "SELECT s.matricula FROM sesion s "
                + "WHERE (s.fechaHoraInicio BETWEEN ? AND ?) OR (s.fechaHoraFin BETWEEN ? AND ?))";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(inicio.getTime()));
            ps.setDate(2, new java.sql.Date(fin.getTime()));
            ps.setDate(3, new java.sql.Date(inicio.getTime()));
            ps.setDate(4, new java.sql.Date(fin.getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Masajista m = new Masajista();
                m.setMatricula(rs.getInt("matricula"));
                m.setNombreCompleto(rs.getString("nombreCompleto"));
                m.setTelefono(rs.getString("telefono"));
                m.setEspecialidad(rs.getString("especialidad"));
                m.setEstado(rs.getBoolean("estado"));
                libres.add(m);
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar masajistas libres: " + ex.getMessage());
        }
        return libres;
    }
}
