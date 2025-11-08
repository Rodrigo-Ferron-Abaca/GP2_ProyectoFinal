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
import gr2_final.accesoadatos.MasajistaData;
import gr2_final.accesoadatos.TratamientoData;
import gr2_final.entidades.Cliente;
import gr2_final.entidades.Consultorio;
import gr2_final.entidades.DiaDeSpa;
import gr2_final.entidades.Instalacion;
import gr2_final.entidades.Masajista;
import gr2_final.entidades.Tratamiento;
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
*/      //CONSULTAS CONSULTORIO 
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
    
        //CONSULTAS INSTALACION
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
        //Listar instalaciones activas
       /* InstalacionData instalacionData = new InstalacionData();
        List<Instalacion> lista = instalacionData.listarInstalacionesActivas();

    if (lista.isEmpty()) {
        System.out.println("No hay instalaciones activas.");
    } else {
        System.out.println("Lista de instalaciones activas:\n");
        for (Instalacion inst : lista) {
            System.out.println("Codigo: " + inst.getCodInst());
            System.out.println("Nombre: " + inst.getNombre());
            System.out.println("Detalle de Uso: " + inst.getDetalleUso());
            System.out.println("Precio por 30 min: $" + inst.getPrecio30m());
            System.out.println("Estado: " + (inst.isEstado() ? "Activa" : "No Activa"));
        }
    }*/
      //esto es para modificar la instalacion 
     /*InstalacionData instalacionData = new InstalacionData();  
     Instalacion inst = instalacionData.buscarInstalacion(1);

    if (inst != null) {
        System.out.println("Datos actuales:");
        System.out.println("Código: " + inst.getCodInst());
        System.out.println("Nombre: " + inst.getNombre());
        System.out.println("Detalle: " + inst.getDetalleUso());
        System.out.println("Precio 30m: " + inst.getPrecio30m());
        System.out.println("Estado: " + inst.isEstado());
       
        // Modificamos 
        inst.setNombre("Masajes Relajantes Premium");
        inst.setDetalleUso("Sala destinada a masajes relajantes de alta calidad.");
        inst.setPrecio30m(3500.00);
        inst.setEstado(true); 

        // realizamos la modificación en la base de datos
        instalacionData.modificarInstalacion(inst);

        System.out.println("Instalacion modificada con exito.");
    } else {
        System.out.println("No se encontro la instalacion.");
    }*/
     
     //dar de baja logica, alta logica y eliminar del todoooo
    /*InstalacionData instalacionData = new InstalacionData();
    //instalacionData.bajaLogicaInstalacion(2);
    //instalacionData.altaLogicaInstalacion(2);
    instalacionData.borrarInstalacion(2); 
    */
    
    //CONSULTAS MASAJISTA
    //para agregar un masajista
    //Masajista masajista = new Masajista (1111, "Hector Manosuave", "2665789665", "Facial", true);
    /*Masajista masajista1 = new Masajista (2222, "Roque Faces", "2664565656", "corporal", true);
    MasajistaData masajistaData = new MasajistaData();
    masajistaData.guardarMasajista(masajista1);*/
    
    //buscar masajista por matri
    /*MasajistaData masajistaData = new MasajistaData();
    int matricula = 1111;  
    Masajista m = masajistaData.buscarMasajista(matricula);
    if (m != null) {
        System.out.println("MASAJISTA ENCONTRADO:");
        System.out.println("Matricula: " + m.getMatricula());
        System.out.println("Nombre Completo: " + m.getNombreCompleto());
        System.out.println("Telefono: " + m.getTelefono());
        System.out.println("Especialidad: " + m.getEspecialidad());
        System.out.println("Estado: " + (m.isEstado() ? "Activo" : "Inactivo"));
    } else {
        System.out.println("No existe masajista con esa matricula.");
    }*/
    
    // para listar los masajistas activos
    /*MasajistaData masajistaData = new MasajistaData();
    List<Masajista> lista = masajistaData.listarMasajistasActivos();

    if (lista.isEmpty()) {
        System.out.println("No hay masajistas activos.");
    } else {
        System.out.println("MASAJISTAS ACTIVOS:");
        for (Masajista m : lista) {
            System.out.println("Matricula: " + m.getMatricula());
            System.out.println("Nombre: " + m.getNombreCompleto());
            System.out.println("Telefono: " + m.getTelefono());
            System.out.println("Especialidad: " + m.getEspecialidad());
        }
    }*/
    //modificar masajista
    /*MasajistaData masajistaData = new MasajistaData();
    int matricula = 1111; 
    Masajista m = masajistaData.buscarMasajista(matricula);

    if (m != null) {

        System.out.println("Datos ANTES de modificar:");
        System.out.println(m);

        //modificamos 
        m.setNombreCompleto("Carlos Manosuave");
        m.setTelefono("2665434343");
        m.setEspecialidad("estetico");
        m.setEstado(true);

        masajistaData.modificarMasajista(m);

        System.out.println("Datos DESPUES de modificar:");
        System.out.println(m);

    } else {
        System.out.println("Lo siento pero no existe masajista con la matricula: " + matricula);
    }*/
    
    //dar de baja logica, alta logica y eliminar
    /*MasajistaData masajistaData = new MasajistaData();
    int matricula = 1111; 
    //masajistaData.bajaLogicaMasajista(matricula);
    //masajistaData.altaLogicaMasajista(matricula);
    masajistaData.borrarMasajista(matricula);*/ 
    
    //CONSULTAS DE TRATAMIENTO
    //Agregar tratamiento
        //Tratamiento tratamiento = new Tratamiento (1,"full trateminto", "facial", "rejuvenecimiento facial", 30, 15000, true);
       /* Tratamiento tratamiento2 = new Tratamiento (2,"especial relax", "relajacion", "spa relax", 60, 30000, true);
        TratamientoData tratamientoData = new TratamientoData();
        tratamientoData.guardarTratamiento(tratamiento2);*/
       
       //modificar un tratamiento
       /*TratamientoData tratamientoData = new TratamientoData();
        Tratamiento t = new Tratamiento();
        t.setCodTratam(1); 
        t.setNombre("Masaje de Relajacion Profunda");
        t.setTipo("relajacion");
        t.setDetalle("Masaje completo con aceites esenciales.");
        t.setDuracion(45);
        t.setCosto(18500); 
        t.setActivo(true);
        
        tratamientoData.modificarTratamiento(t);*/
       
       //buscar algun tratamiento
       /*TratamientoData tratamientoData = new TratamientoData();
        int codigoBuscado = 2;
        
        Tratamiento t = tratamientoData.buscarTratamiento(codigoBuscado);
        
        if (t != null) {
            System.out.println("Tratamiento encontrado:");
            System.out.println("Codigo: " + t.getCodTratam());
            System.out.println("Nombre: " + t.getNombre());
            System.out.println("Tipo: " + t.getTipo());
            System.out.println("Detalle: " + t.getDetalle());
            System.out.println("Duracion: " + t.getDuracion() + " min");
            System.out.println("Costo: $" + t.getCosto());
            System.out.println("Activo: " + (t.isActivo() ? "Si" : "No"));
        } else {
            System.out.println("No se encontro el tratamiento con el codigo: " + codigoBuscado);
        }*/
       
       //listar tratamientos
       /*TratamientoData tratamientoData = new TratamientoData();
        List<Tratamiento> lista = tratamientoData.listarTratamientos();

        if (lista.isEmpty()) {
            System.out.println("No hay tratamientos activos cargados.");
        } else {
            System.out.println("Tratamientos disponibles:");
            for (Tratamiento t : lista) {
                
                System.out.println("Codigo: " + t.getCodTratam());
                System.out.println("Nombre: " + t.getNombre());
                System.out.println("Tipo: " + t.getTipo());
                System.out.println("Detalle: " + t.getDetalle());
                System.out.println("Duracion: " + t.getDuracion() + " min");
                System.out.println("Costo: $" + t.getCosto());
                System.out.println("Activo: " + (t.isActivo() ? "Si" : "No"));
            }
        }*/
       
       //listar por tipo
       /*TratamientoData tratamientoData = new TratamientoData();
        String tipoBuscado = "relajacion";

        List<Tratamiento> lista = tratamientoData.listarTratamientosPorTipo(tipoBuscado);

        if (lista.isEmpty()) {
            System.out.println("No se encontraron tratamientos del tipo: " + tipoBuscado);
        } else {
            System.out.println("Tratamientos tipo: " + tipoBuscado);
            for (Tratamiento t : lista) {
               
                System.out.println("Codigo: " + t.getCodTratam());
                System.out.println("Nombre: " + t.getNombre());
                System.out.println("Detalle: " + t.getDetalle());
                System.out.println("Duracion: " + t.getDuracion() + " min");
                System.out.println("Costo: $" + t.getCosto());
            }
        }*/
    
    
       
    }
}        


