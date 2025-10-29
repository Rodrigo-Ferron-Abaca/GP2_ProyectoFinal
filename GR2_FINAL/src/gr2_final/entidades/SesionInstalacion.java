/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gr2_final.entidades;

/**
 *
 * @author KEVIN
 */
public class SesionInstalacion {
    private int codSesion;
    private Instalacion codInst;

    public SesionInstalacion() {
    }

    public SesionInstalacion(int codSesion, Instalacion codInst) {
        this.codSesion = codSesion;
        this.codInst = codInst;
    }

    @Override
    public String toString() {
        return "SesionInstalacion{" + "codSesion=" + codSesion + ", codInst=" + codInst + '}';
    }
    
    
}
