/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gr2_final.entidades;

import java.time.LocalDateTime;

/**
 *
 * @author KEVIN
 */
public class DiaDeSpa {

    private int codPack;
    private LocalDateTime fechaHora;
    private String preferencias;
    private double monto;
    private boolean estado;
    private Cliente codCli;

    public DiaDeSpa() {
    }

    public DiaDeSpa(int codPack, LocalDateTime fechaHora, String preferencias, double monto, boolean estado, Cliente codCli) {
        this.codPack = codPack;
        this.fechaHora = fechaHora;
        this.preferencias = preferencias;
        this.monto = monto;
        this.estado = estado;
        this.codCli = codCli;
    }

    public DiaDeSpa(LocalDateTime fechaHora, String preferencias, double monto, boolean estado, Cliente codCli) {
        this.fechaHora = fechaHora;
        this.preferencias = preferencias;
        this.monto = monto;
        this.estado = estado;
        this.codCli = codCli;
    }

    public int getCodPack() {
        return codPack;
    }

    public void setCodPack(int codPack) {
        this.codPack = codPack;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(String preferencias) {
        this.preferencias = preferencias;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Cliente getCodCli() {
        return codCli;
    }

    public void setCodCli(Cliente codCli) {
        this.codCli = codCli;
    }

    @Override
    public String toString() {
        return "Pack" +codPack + " a $" + monto;
    }

}
