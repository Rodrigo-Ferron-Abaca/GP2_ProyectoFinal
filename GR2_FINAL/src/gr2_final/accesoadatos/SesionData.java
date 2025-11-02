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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class SesionData {
    private Connection con = null;
    
    // Clases Data necesarias para construir los objetos relacionados (FKs)
    private TratamientoData tratamientoData = new TratamientoData();
    private ConsultorioData consultorioData = new ConsultorioData(); 
    private MasajistaData masajistaData = new MasajistaData();
    private DiaDeSpaData diaDeSpaData = new DiaDeSpaData(); 

    public SesionData() {
        con = Conexion.getConexion();
    }

    private Sesion crearSesionDesdeRS(ResultSet rs) throws SQLException {
        Sesion sesion = new Sesion();
        sesion.setCodSesion(rs.getInt("codSesion"));
        
        sesion.setFechaHoraInicio(rs.getDate("fechaHoraInicio").toLocalDate()); 
        sesion.setFechaHoraFin(rs.getDate("fechaHoraFin").toLocalDate());
        sesion.setEstado(rs.getBoolean("estado"));

        // 1. Relación con Tratamiento
        int codTratam = rs.getInt("codTratam");
        Tratamiento tratamiento = tratamientoData.buscarTratamiento(codTratam);
        sesion.setCodigoTratam(tratamiento);

        // 2. Relación con Consultorio
        int nroConsultorio = rs.getInt("nroConsultorio");
        Consultorio consultorio = consultorioData.buscarConsultorio(nroConsultorio);
        sesion.setNroConsutorio(consultorio);

        // 3. Relación con Masajista
        int matricula = rs.getInt("matricula");
        Masajista masajista = masajistaData.buscarMasajista(matricula);
        sesion.setMatricula(masajista);
        
        // 4. Relación con DiaDeSpa (codPack) - ¡Ahora sí se mapea!
        int codPack = rs.getInt("codPack");
        if (!rs.wasNull() && codPack > 0) { 
             DiaDeSpa diaDeSpa = diaDeSpaData.buscarDiaDeSpa(codPack);
             sesion.setCodPack(diaDeSpa);
        } else {
             sesion.setCodPack(null);
        }

        return sesion;
    }
    
    public void guardarSesion(Sesion sesion) {
        String sql = "INSERT INTO sesion (fechaHoraInicio, fechaHoraFin, estado, codTratam, nroConsultorio, matricula, codPack) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDate(1, Date.valueOf(sesion.getFechaHoraInicio())); 
            ps.setDate(2, Date.valueOf(sesion.getFechaHoraFin()));
            ps.setBoolean(3, sesion.isEstado());
            
            ps.setInt(4, sesion.getCodigoTratam().getCodTratam());
            ps.setInt(5, sesion.getNroConsutorio().getNroConsultorio());
            ps.setInt(6, sesion.getMatricula().getMatricula());
            
            // Usamos el nuevo atributo codPack
            if (sesion.getCodPack() != null && sesion.getCodPack().getCodPack() > 0) {
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

    public void modificarSesion(Sesion sesion) {
        String sql = "UPDATE sesion SET fechaHoraInicio = ?, fechaHoraFin = ?, estado = ?, codTratam = ?, nroConsultorio = ?, matricula = ?, codPack = ? WHERE codSesion = ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(sesion.getFechaHoraInicio()));
            ps.setDate(2, Date.valueOf(sesion.getFechaHoraFin()));
            ps.setBoolean(3, sesion.isEstado());
            ps.setInt(4, sesion.getCodigoTratam().getCodTratam());
            ps.setInt(5, sesion.getNroConsutorio().getNroConsultorio());
            ps.setInt(6, sesion.getMatricula().getMatricula());
            
            // Usamos el nuevo atributo codPack
            if (sesion.getCodPack() != null && sesion.getCodPack().getCodPack() > 0) {
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
    
    public void eliminarSesion(int codSesion) {
        String sql = "UPDATE sesion SET estado = 0 WHERE codSesion = ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codSesion);
            int exito = ps.executeUpdate();
            
            if (exito == 1) {
                JOptionPane.showMessageDialog(null, "Sesión anulada (baja lógica) con éxito.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la Sesión para anular.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al anular la Sesión: " + ex.getMessage());
        }
    }

    public Sesion buscarSesion(int codSesion) {
        Sesion sesion = null;
        String sql = "SELECT codSesion, fechaHoraInicio, fechaHoraFin, estado, codTratam, nroConsultorio, matricula, codPack FROM sesion WHERE codSesion = ?";
        
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
    
    public List<Sesion> listarSesiones() {
        List<Sesion> sesiones = new ArrayList<>();
        String sql = "SELECT codSesion, fechaHoraInicio, fechaHoraFin, estado, codTratam, nroConsultorio, matricula, codPack FROM sesion";
        
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Sesion sesion = crearSesionDesdeRS(rs);
                sesiones.add(sesion);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar Sesiones: " + ex.getMessage());
        }
        return sesiones;
    }
    
    public List<Sesion> listarSesionesPorFranja(LocalDate inicio, LocalDate fin) {
        List<Sesion> sesiones = new ArrayList<>();
        String sql = "SELECT codSesion, fechaHoraInicio, fechaHoraFin, estado, codTratam, nroConsultorio, matricula, codPack FROM sesion WHERE fechaHoraInicio >= ? AND fechaHoraFin <= ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(inicio));
            ps.setDate(2, Date.valueOf(fin));
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Sesion sesion = crearSesionDesdeRS(rs);
                    sesiones.add(sesion);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar Sesiones por franja horaria: " + ex.getMessage());
        }
        return sesiones;
    }

    public List<Sesion> listarSesionesPorMasajista(int matricula) {
        List<Sesion> sesiones = new ArrayList<>();
        String sql = "SELECT codSesion, fechaHoraInicio, fechaHoraFin, estado, codTratam, nroConsultorio, matricula, codPack FROM sesion WHERE matricula = ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, matricula);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Sesion sesion = crearSesionDesdeRS(rs);
                    sesiones.add(sesion);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al listar Sesiones por masajista: " + ex.getMessage());
        }
        return sesiones;
    }
}