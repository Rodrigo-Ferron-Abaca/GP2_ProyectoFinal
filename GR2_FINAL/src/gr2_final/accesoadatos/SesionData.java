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
import gr2_final.entidades.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class SesionData {

    private Connection con = null;

    private TratamientoData tratamientoData = new TratamientoData();
    private ConsultorioData consultorioData = new ConsultorioData();
    private MasajistaData masajistaData = new MasajistaData();
    private DiaDeSpaData diaDeSpaData = new DiaDeSpaData();

    public SesionData() {
        con = Conexion.getConexion();
    }

    /**
     * CREA UNA SESIÓN DESDE EL RESULTSET *
     */
    private Sesion crearSesionDesdeRS(ResultSet rs) throws SQLException {

        Sesion sesion = new Sesion();

        sesion.setCodSesion(rs.getInt("codSesion"));
        sesion.setFechaHoraInicio(rs.getTimestamp("fechaHoraInicio").toLocalDateTime());
        sesion.setFechaHoraFin(rs.getTimestamp("fechaHoraFin").toLocalDateTime());
        sesion.setEstado(rs.getBoolean("estado"));

        // Tratamiento
        sesion.setCodigoTratam(tratamientoData.buscarTratamiento(rs.getInt("codTratam")));

        // Consultorio
        sesion.setNroConsutorio(consultorioData.buscarConsultorio(rs.getInt("nroConsultorio")));

        // Masajista
        sesion.setMatricula(masajistaData.buscarMasajista(rs.getInt("matricula")));

        // Dia de SPA (pack)
        int codPack = rs.getInt("codPack");
        if (!rs.wasNull() && codPack > 0) {
            sesion.setCodPack(diaDeSpaData.buscarDiaDeSpa(codPack));
        } else {
            sesion.setCodPack(null);
        }

        return sesion;
    }

    //GUARDAR SESIÓN
    public void guardarSesion(Sesion sesion) {

        String sql = "INSERT INTO sesion "
                + "(fechaHoraInicio, fechaHoraFin, estado, codTratam, nroConsultorio, matricula, codPack) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setTimestamp(1, Timestamp.valueOf(sesion.getFechaHoraInicio()));
            ps.setTimestamp(2, Timestamp.valueOf(sesion.getFechaHoraFin()));
            ps.setBoolean(3, sesion.isEstado());
            ps.setInt(4, sesion.getCodigoTratam().getCodTratam());
            ps.setInt(5, sesion.getNroConsutorio().getNroConsultorio());
            ps.setInt(6, sesion.getMatricula().getMatricula());

            if (sesion.getCodPack() != null) {
                ps.setInt(7, sesion.getCodPack().getCodPack());
            } else {
                ps.setNull(7, java.sql.Types.INTEGER);
            }

            int exito = ps.executeUpdate();

            if (exito == 1) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        sesion.setCodSesion(rs.getInt(1));
                        JOptionPane.showMessageDialog(null, "Sesión Cód. " + sesion.getCodSesion() + " guardada con éxito.");
                    }
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar la Sesión: " + ex.getMessage());
        }
    }

    //MODIFICAR SESIÓN
    public void modificarSesion(Sesion sesion) {

        String sql = "UPDATE sesion SET fechaHoraInicio = ?, fechaHoraFin = ?, estado = ?, "
                + "codTratam = ?, nroConsultorio = ?, matricula = ?, codPack = ? "
                + "WHERE codSesion = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(sesion.getFechaHoraInicio()));
            ps.setTimestamp(2, Timestamp.valueOf(sesion.getFechaHoraFin()));
            ps.setBoolean(3, sesion.isEstado());
            ps.setInt(4, sesion.getCodigoTratam().getCodTratam());
            ps.setInt(5, sesion.getNroConsutorio().getNroConsultorio());
            ps.setInt(6, sesion.getMatricula().getMatricula());

            if (sesion.getCodPack() != null) {
                ps.setInt(7, sesion.getCodPack().getCodPack());
            } else {
                ps.setNull(7, java.sql.Types.INTEGER);
            }

            ps.setInt(8, sesion.getCodSesion());

            int exito = ps.executeUpdate();

            if (exito == 1) {
                JOptionPane.showMessageDialog(null, "Sesión modificada con éxito.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la Sesión para modificar.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al modificar la Sesión: " + ex.getMessage());
        }
    }

    //BAJA LÓGICA 
    public void eliminarSesion(int codSesion) {

        String sql = "DELETE FROM sesion WHERE codSesion = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codSesion);

            int exito = ps.executeUpdate();

            if (exito == 1) {
                JOptionPane.showMessageDialog(null, "Sesión eliminada definitivamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la sesión para eliminar.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la sesión: " + ex.getMessage());
        }
    }

    //BUSCAR SESIÓN
    public Sesion buscarSesion(int codSesion) {

        Sesion sesion = null;

        String sql = "SELECT * FROM sesion WHERE codSesion = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codSesion);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    sesion = crearSesionDesdeRS(rs);
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar la Sesión: " + ex.getMessage());
        }

        return sesion;
    }

    //LISTAR TODAS
    public List<Sesion> listarSesiones() {

        List<Sesion> sesiones = new ArrayList<>();
        String sql = "SELECT * FROM sesion";

        try (PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                sesiones.add(crearSesionDesdeRS(rs));
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar Sesiones: " + ex.getMessage());
        }

        return sesiones;
    }

    //LISTAR POR FRANJA *
    public List<Sesion> listarSesionesPorFranja(LocalDate inicio, LocalDate fin) {

        List<Sesion> sesiones = new ArrayList<>();

        String sql = "SELECT * FROM sesion WHERE fechaHoraInicio >= ? AND fechaHoraFin <= ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(inicio.atStartOfDay()));
            ps.setTimestamp(2, Timestamp.valueOf(fin.atTime(23, 59)));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    sesiones.add(crearSesionDesdeRS(rs));
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar Sesiones por fecha: " + ex.getMessage());
        }

        return sesiones;
    }

    //LISTAR POR MASAJISTA
    public List<Sesion> listarSesionesPorMasajista(int matricula) {

        List<Sesion> sesiones = new ArrayList<>();

        String sql = "SELECT * FROM sesion WHERE matricula = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, matricula);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    sesiones.add(crearSesionDesdeRS(rs));
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar Sesiones por masajista: " + ex.getMessage());
        }

        return sesiones;
    }

    public boolean masajistaDisponible(int matricula, LocalDateTime inicio, LocalDateTime fin, Integer codSesionActual) {

        String sql = "SELECT * FROM sesion WHERE matricula = ? "
                + "AND NOT (fechaHoraFin <= ? OR fechaHoraInicio >= ?)";

        if (codSesionActual != null) {
            sql += " AND codSesion <> " + codSesionActual;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, matricula);
            ps.setTimestamp(2, Timestamp.valueOf(inicio));
            ps.setTimestamp(3, Timestamp.valueOf(fin));

            try (ResultSet rs = ps.executeQuery()) {
                return !rs.next(); //true = disponible
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error validando disponibilidad de masajista: " + ex.getMessage());
            return false;
        }
    }

    public boolean consultorioDisponible(int nroConsultorio, LocalDateTime inicio, LocalDateTime fin, Integer codSesionActual) {

        String sql = "SELECT * FROM sesion WHERE nroConsultorio = ? "
                + "AND NOT (fechaHoraFin <= ? OR fechaHoraInicio >= ?)";

        if (codSesionActual != null) {
            sql += " AND codSesion <> " + codSesionActual;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, nroConsultorio);
            ps.setTimestamp(2, Timestamp.valueOf(inicio));
            ps.setTimestamp(3, Timestamp.valueOf(fin));

            try (ResultSet rs = ps.executeQuery()) {
                return !rs.next(); // true = disponible
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error validando disponibilidad de consultorio: " + ex.getMessage());
            return false;
        }
    }

}
