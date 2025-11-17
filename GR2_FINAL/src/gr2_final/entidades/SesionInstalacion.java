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
    private int codInstal;

    public SesionInstalacion() {}

    public SesionInstalacion(int codSesion, int codInstal) {
        this.codSesion = codSesion;
        this.codInstal = codInstal;
    }

    public int getCodSesion() {
        return codSesion;
    }

    public void setCodSesion(int codSesion) {
        this.codSesion = codSesion;
    }

    public int getCodInstal() {
        return codInstal;
    }

    public void setCodInstal(int codInstal) {
        this.codInstal = codInstal;
    }
}
