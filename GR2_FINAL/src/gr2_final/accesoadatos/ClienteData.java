package gr2_final.accesoadatos;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import gr2_final.entidades.Cliente;

/**
 *
 * @author rodrigo
 */
public class ClienteData {

    private Connection con = null;

    public ClienteData() {
        con = Conexion.getConexion();
    }

    public void guardarCliente(Cliente cliente) {
        String query = "INSERT INTO cliente (dni, nombreCompleto, telefono, edad, afecciones, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, cliente.getDni());
            preparedStatement.setString(2, cliente.getNombreCompleto());
            preparedStatement.setString(3, cliente.getTelefono());
            preparedStatement.setInt(4, cliente.getEdad());
            preparedStatement.setString(5, cliente.getAfecciones());
            preparedStatement.setBoolean(6, cliente.isEstado());

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                cliente.setCodCli(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "Se agregó el cliente con código: " + cliente.getCodCli());
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo obtener el código del cliente.");
            }
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla cliente: " + e.getMessage());
        }
    }

    public Cliente buscarClientePorCod(int codCli) {
        String sql = "SELECT * FROM cliente WHERE codCli = ? AND estado = 1";
        Cliente cliente = null;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codCli);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                cliente.setCodCli(codCli);
                cliente.setDni(rs.getInt("dni"));
                cliente.setNombreCompleto(rs.getString("nombreCompleto"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setEdad(rs.getInt("edad"));
                cliente.setAfecciones(rs.getString("afecciones"));
                cliente.setEstado(rs.getBoolean("estado"));
            } else {
                JOptionPane.showMessageDialog(null, "No existe un cliente con ese Código");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Cliente: " + e.getMessage());
        }
        return cliente;
    }

    public Cliente buscarClientePorDni(int dni) {
        Cliente cliente = null;
        String query = "SELECT * FROM cliente WHERE dni = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, dni);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                cliente = new Cliente();
                cliente.setCodCli(resultSet.getInt("codCli"));
                cliente.setDni(dni);
                cliente.setNombreCompleto(resultSet.getString("nombreCompleto"));
                cliente.setTelefono(resultSet.getString("telefono"));
                cliente.setEdad(resultSet.getInt("edad"));
                cliente.setAfecciones(resultSet.getString("afecciones"));
                cliente.setEstado(resultSet.getBoolean("estado"));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar cliente por DNI: " + e.getMessage());
        }
        return cliente;
    }

    public List<Cliente> listaClientes() {
        List<Cliente> listaClientes = new ArrayList<>();
        String query = "SELECT * FROM cliente WHERE estado = 1";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Cliente cliente = new Cliente();
                cliente.setCodCli(resultSet.getInt("codCli"));
                cliente.setDni(resultSet.getInt("dni"));
                cliente.setNombreCompleto(resultSet.getString("nombreCompleto"));
                cliente.setTelefono(resultSet.getString("telefono"));
                cliente.setEdad(resultSet.getInt("edad"));
                cliente.setAfecciones(resultSet.getString("afecciones"));
                cliente.setEstado(resultSet.getBoolean("estado"));

                listaClientes.add(cliente);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar clientes: " + e.getMessage());
        }
        return listaClientes;
    }

    public void modificarCliente(Cliente cliente) {
        String query = "UPDATE cliente SET dni=?, nombreCompleto=?, telefono=?, edad=?, afecciones=?, estado=? WHERE codCli=?";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, cliente.getDni());
            preparedStatement.setString(2, cliente.getNombreCompleto());
            preparedStatement.setString(3, cliente.getTelefono());
            preparedStatement.setInt(4, cliente.getEdad());
            preparedStatement.setString(5, cliente.getAfecciones());
            preparedStatement.setBoolean(6, cliente.isEstado());
            preparedStatement.setInt(7, cliente.getCodCli());

            int filasModificadas = preparedStatement.executeUpdate();

            if (filasModificadas > 0) {
                JOptionPane.showMessageDialog(null, "Se modificaron los datos del cliente");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el cliente con el Código que ingresaste");
            }
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error para modificar datos del cliente: " + e.getMessage());
        }
    }

    public void borradoLogicoCliente(int codCli) {
        String query = "UPDATE cliente SET estado = false WHERE codCli = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, codCli);

            int filasModificadas = preparedStatement.executeUpdate();

            if (filasModificadas > 0) {
                JOptionPane.showMessageDialog(null, "El cliente fue dado de baja (borrado lógico)");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el cliente con el Código proporcionado");
            }
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al realizar el borrado lógico del cliente: " + e.getMessage());
        }
    }

    //agregado tambien
    public void altaLogicaCliente(int codCli) {
        String query = "UPDATE cliente SET estado = true WHERE codCli = ?";

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, codCli);

            int filasModificadas = ps.executeUpdate();

            if (filasModificadas > 0) {
                JOptionPane.showMessageDialog(null, "El cliente fue dado de alta correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontro el cliente con el codigo proporcionado.");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al realizar el alta logica del cliente: " + e.getMessage());
        }
    }

    //faltaba este metodo chicos
    public void borrarCliente(int codCli) {
        String sql = "DELETE FROM cliente WHERE codCli = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codCli);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente de la base de datos.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontro ningun cliente con ese codigo.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el cliente: " + e.getMessage());
        }
    }

    public Cliente buscarClientePorId(int codCli) {
        return buscarClientePorCod(codCli);
    }
}
