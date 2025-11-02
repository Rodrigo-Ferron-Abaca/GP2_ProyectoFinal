/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gr2_final.accesoadatos;

import gr2_final.entidades.Consultorio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author KEVIN
 */
public class ConsultorioData {

    private Connection con = null;

    public ConsultorioData() {
        con = Conexion.getConexion();
    }

    // para guardar
    public void guardarConsultorio(Consultorio c) {
        String sql = "INSERT INTO consultorio (nroConsultorio, usos, equipamiento, apto) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, c.getNroConsultorio());
            ps.setString(2, c.getUsos());
            ps.setString(3, c.getEquipamiento());
            ps.setBoolean(4, c.isApto());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Consultorio agregado con exito.");
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar consultorio: " + e.getMessage());
        }
    }

    // busca por nro
    public Consultorio buscarConsultorio(int nroConsultorio) {
        Consultorio c = null;
        String sql = "SELECT * FROM consultorio WHERE nroConsultorio = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, nroConsultorio);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                c = new Consultorio();
                c.setNroConsultorio(nroConsultorio);
                c.setUsos(rs.getString("usos"));
                c.setEquipamiento(rs.getString("equipamiento"));
                c.setApto(rs.getBoolean("apto"));
            } else {
                JOptionPane.showMessageDialog(null, "No existe consultorio con ese numero.");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar consultorio: " + e.getMessage());
        }
        return c;
    }

    // lista de solo aptos
    public List<Consultorio> listarConsultoriosAptos() {
        List<Consultorio> lista = new ArrayList<>();
        String sql = "SELECT * FROM consultorio WHERE apto = 1";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Consultorio c = new Consultorio();
                c.setNroConsultorio(rs.getInt("nroConsultorio"));
                c.setUsos(rs.getString("usos"));
                c.setEquipamiento(rs.getString("equipamiento"));
                c.setApto(rs.getBoolean("apto"));
                lista.add(c);
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar consultorios: " + e.getMessage());
        }
        return lista;
    }

    // para modificar
    public void modificarConsultorio(Consultorio c) {
        String sql = "UPDATE consultorio SET usos = ?, equipamiento = ?, apto = ? WHERE nroConsultorio = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, c.getUsos());
            ps.setString(2, c.getEquipamiento());
            ps.setBoolean(3, c.isApto());
            ps.setInt(4, c.getNroConsultorio());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Consultorio modificado correctamente.");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar consultorio: " + e.getMessage());
        }
    }
    // para dar de baja logica

    public void bajaLogicaConsultorio(int nroConsultorio) {
        String sql = "UPDATE consultorio SET apto = false WHERE nroConsultorio = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, nroConsultorio);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Consultorio marcado como NO apto.");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en baja logica: " + e.getMessage());
        }
    }

    // para dar de alta logica
    public void altaLogicaConsultorio(int nroConsultorio) {
        String sql = "UPDATE consultorio SET apto = true WHERE nroConsultorio = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, nroConsultorio);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Consultorio reactivado.");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en alta logica: " + e.getMessage());
        }
    }
    // Para borrarlo para siempreeee

    public void borrarConsultorio(int nroConsultorio) {
        String sql = "DELETE FROM consultorio WHERE nroConsultorio = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, nroConsultorio);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Consultorio eliminado permanentemente.");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al borrar consultorio: " + e.getMessage());
        }
    }

    public List<Consultorio> listarConsultorios() {
        List<Consultorio> consultorios = new ArrayList<>();
        String sql = "SELECT * FROM consultorio WHERE apto = 1";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Consultorio c = new Consultorio();
                c.setNroConsultorio(rs.getInt("nroConsultorio"));
                c.setUsos(rs.getString("usos"));
                c.setEquipamiento(rs.getString("equipamiento"));
                c.setApto(rs.getBoolean("apto"));
                consultorios.add(c);
            }

            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar consultorios: " + ex.getMessage());
        }

        return consultorios;
    }

    public Consultorio buscarConsultorioPorNumero(int nro) {
        Consultorio c = null;
        String sql = "SELECT * FROM consultorio WHERE nroConsultorio = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, nro);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                c = new Consultorio();
                c.setNroConsultorio(rs.getInt("nroConsultorio"));
                c.setUsos(rs.getString("usos"));
                c.setEquipamiento(rs.getString("equipamiento"));
                c.setApto(rs.getBoolean("apto"));
            }

            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar consultorio: " + ex.getMessage());
        }

        return c;
    }
}
