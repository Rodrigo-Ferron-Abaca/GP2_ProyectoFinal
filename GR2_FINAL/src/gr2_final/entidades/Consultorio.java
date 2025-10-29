/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gr2_final.entidades;

/**
 *
 * @author KEVIN
 */
public class Consultorio {
    private int nroConsultorio;
    private String usos;
    private String equipamiento;
    private boolean apto;

    public Consultorio() {
    }

    public Consultorio(int nroConsultorio, String usos, String equipamiento, boolean apto) {
        this.nroConsultorio = nroConsultorio;
        this.usos = usos;
        this.equipamiento = equipamiento;
        this.apto = apto;
    }

    public int getNroConsultorio() {
        return nroConsultorio;
    }

    public void setNroConsultorio(int nroConsultorio) {
        this.nroConsultorio = nroConsultorio;
    }

    public String getUsos() {
        return usos;
    }

    public void setUsos(String usos) {
        this.usos = usos;
    }

    public String getEquipamiento() {
        return equipamiento;
    }

    public void setEquipamiento(String equipamiento) {
        this.equipamiento = equipamiento;
    }

    public boolean isApto() {
        return apto;
    }

    public void setApto(boolean apto) {
        this.apto = apto;
    }

    @Override
    public String toString() {
        return "Consultorio{" + "nroConsultorio=" + nroConsultorio + ", usos=" + usos + ", equipamiento=" + equipamiento + ", apto=" + apto + '}';
    }
    
    
    
}
