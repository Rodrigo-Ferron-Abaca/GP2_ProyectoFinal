/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr2_final;

import gr2_final.accesoadatos.ClienteData;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import gr2_final.accesoadatos.Conexion;
import gr2_final.entidades.Cliente;
/**
 *
 * @author rodrigo
 */
public class GR2_FINAL {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Connection con = Conexion.getConexion();
        
        /*Cliente cl = new Cliente (37132982, "Kevin Orozco", "4335533", 32, "escoliosis lumbar", true);
        Cliente cl2 = new Cliente (36543123, "Robert Galati", "4447722", 35, "desviación de columna", true);
        ClienteData clienteData = new ClienteData();
        clienteData.guardarCliente(cl);
        clienteData.guardarCliente(cl2);*/
        
        /*----Buscar cliente por DNI
        ClienteData clienteData = new ClienteData();
        Cliente encontrado = clienteData.buscarClientePorDni(36543123);
        if(encontrado != null){
            System.out.println("Nombre: "+ encontrado.getNombreCompleto());
        }else{
            System.out.println("no se encontro cliente");
        }*/
        
        //baja Lógica 
        /*ClienteData clienteData = new ClienteData();
        Cliente encontrado = clienteData.buscarClientePorDni(36543123);
        if (encontrado != null) {
            clienteData.borradoLogicoCliente(encontrado.getCodCli());
        }*/
        //
        
        }

    

}
