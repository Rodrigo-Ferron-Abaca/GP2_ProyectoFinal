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
import gr2_final.accesoadatos.ConsultorioData;
import gr2_final.accesoadatos.DiaDeSpaData;
import gr2_final.accesoadatos.InstalacionData;
import gr2_final.entidades.Cliente;
import gr2_final.entidades.Consultorio;
import gr2_final.entidades.DiaDeSpa;
import gr2_final.entidades.Instalacion;
import javax.swing.JOptionPane;

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

        //Cliente cl = new Cliente (37132982, "Kevin Orozco", "4335533", 32, "escoliosis lumbar", true);
        //Cliente cl2 = new Cliente (36543123, "Robert Galati", "4447722", 35, "desviación de columna", true);
       // Cliente cl3 = new Cliente (38999333, "Maxi Robertson", "5544433", 30, "lumbares", true);
        //ClienteData clienteData = new ClienteData();
        //clienteData.guardarCliente(cl);
        //clienteData.guardarCliente(cl2);
        //clienteData.guardarCliente(cl3);
        
 //----Buscar cliente por DNI
        /*ClienteData clienteData = new ClienteData();
        Cliente encontrado = clienteData.buscarClientePorDni(37779998);
        if(encontrado != null){
            System.out.println("Nombre: "+ encontrado.getNombreCompleto());
        }else{
            System.out.println("no se encontro cliente");
        }*/
        //baja Lógica 
        /*ClienteData clienteData = new ClienteData();
        Cliente encontrado = clienteData.buscarClientePorDni(38999333);
        if (encontrado != null) {
            clienteData.borradoLogicoCliente(encontrado.getCodCli());
         }else{
            System.out.println("no se encontro cliente");
        }*/
        
        //alta logica
        //ClienteData clienteData = new ClienteData();
        //clienteData.altaLogicaCliente(6);
    //}
        //Modificar un cliente
        /*ClienteData clienteData = new ClienteData();
        Cliente encontrado = clienteData.buscarClientePorDni(38999333);
        if (encontrado != null) {
            encontrado.setTelefono("11223355");
            encontrado.setEdad(24);
            clienteData.modificarCliente(encontrado);
         }else{
            System.out.println("no se encontro cliente");
        }
        }*/
        //eliminar cliente fisicamente por ID
        //ClienteData clienteData = new ClienteData();
        //clienteData.borrarCliente(6);
         
       //ClienteData clienteData = new ClienteData();
        //DiaDeSpaData diaData = new DiaDeSpaData();
        /* 
        Cliente cliente = clienteData.buscarClientePorDni(37132982);

        if (cliente != null) {
            LocalDate fechaElegida = LocalDate.of(2025, 11, 15);
            DiaDeSpa nuevoDia = new DiaDeSpa(fechaElegida, "Masaje + Sauna", 5000.0, true, cliente);
            diaData.guardarDiaDeSpa(nuevoDia);
            System.out.println("Nuevo Día de Spa guardado: " + nuevoDia);
        } else {
            System.out.println("No se encontró el cliente.");
        }
        
        // buscar dia de spa
        DiaDeSpa diaBuscado = new DiaDeSpaData().buscarDiaDeSpa(1);
        System.out.println(diaBuscado != null ? diaBuscado : "No se encontró la reserva");

        DiaDeSpa dia = diaData.buscarDiaDeSpa(1);
        if (dia != null) {
            dia.setEstado(true);
            dia.setMonto(7001.0);
            dia.setPreferencias("Masaje");
            diaData.modificarDiaDeSpa(dia);
            System.out.println("Día de Spa modificado: " + dia);
        } else {
            System.out.println("No se encontró el Día de Spa con codPack :" + dia);
        }
     
        
        for (DiaDeSpa dia : diaData.listarDiasDeSpa()) { 
            System.out.println(dia);
        }

        LocalDate fecha = LocalDate.of(2025, 11, 2); // por fecha
        for (DiaDeSpa dia : diaData.listarDiasDeSpaPorFecha(fecha)) {
            System.out.println(dia);
        }
*/      //PRUEBAS CONSULTORIO 
        //Agregar Consultorio
        //Consultorio consu = new Consultorio (1,"masajes", "maquina de ralajacion", true);
        /*Consultorio consu1 = new Consultorio (2,"sauna", "sala de sauna", true);
        ConsultorioData consultorioData = new ConsultorioData();
        consultorioData.guardarConsultorio(consu1);
*/
        //buscaaar consultorio por nro
        /*ConsultorioData consultorioData = new ConsultorioData();
        Consultorio encontrado = consultorioData.buscarConsultorio(1);
        if(encontrado != null){
            System.out.println("Consultorio Nro: "+ encontrado.getNroConsultorio());
        }else{
            System.out.println("no se encontro consultorio");
    }
*/
        //listar solo aptos
        /*ConsultorioData consultorioData = new ConsultorioData();
        List<Consultorio> lista = consultorioData.listarConsultoriosAptos();
    
    for (Consultorio c : lista) {
        System.out.println("Nro Consultorio: " + c.getNroConsultorio());
        System.out.println("Usos: " + c.getUsos());
        System.out.println("Equipamiento: " + c.getEquipamiento());
        System.out.println("Apto: " + c.isApto());
        
    }*/
        //modificar consultorio
       /*ConsultorioData consultorioData = new ConsultorioData();
        Consultorio c = consultorioData.buscarConsultorio(1); 

    if (c != null) {
        System.out.println("Datos antes de modificar:");
        System.out.println(c);

        // Modifica
        c.setUsos("Rehabilitacion");
        c.setEquipamiento("Camilla hidraulica y equipos de ultrasonido");
        c.setApto(true);

        // Guardamos
        consultorioData.modificarConsultorio(c);

        // Confirmamos
        System.out.println("Datos despues de modificar:");
        System.out.println(c);
    } else {
        System.out.println("No se encontro el consultorio");
    }*/
       //dar de baja/alta/elimina consultorio
        //ConsultorioData consultorioData = new ConsultorioData();
        //consultorioData.bajaLogicaConsultorio(2);
        //consultorioData.altaLogicaConsultorio(1);
        //consultorioData.borrarConsultorio(2);
    
        //PRUEBAS INSTALACION
        //Agregar instalacion
        //Instalacion insta = new Instalacion (1,"sala central", "masoterapia",30000, true);
        /*Instalacion insta1 = new Instalacion (2,"sala VIP", "sauna",450000, true);
        InstalacionData instalacionData = new InstalacionData();
        instalacionData.guardarInstalacion(insta1);
*/
        //Buscar instalacion
        /*InstalacionData instalacionData = new InstalacionData();
        Instalacion encontrado = instalacionData.buscarInstalacion(1);
        if(encontrado != null){
            System.out.println("Consultorio Nro: "+ encontrado.getCodInst());
        }else{
            System.out.println("no se encontro instalacion");
    }*/
        
    }
    
}
        


