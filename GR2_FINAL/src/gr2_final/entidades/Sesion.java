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
public class Sesion {

    private int codSesion;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private boolean estado;
    private Tratamiento codigoTratam;
    private Consultorio nroConsutorio;
    private Masajista matricula;
    private DiaDeSpa codPack;

    public Sesion() {
    }

    public Sesion(int codSesion, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin,boolean estado, Tratamiento codigoTratam, Consultorio nroConsutorio,
            Masajista matricula, DiaDeSpa codPack) {
        this.codSesion = codSesion;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.estado = estado;
        this.codigoTratam = codigoTratam;
        this.nroConsutorio = nroConsutorio;
        this.matricula = matricula;
        this.codPack = codPack;
    }

    public int getCodSesion() {
        return codSesion;
    }

    public void setCodSesion(int codSesion) {
        this.codSesion = codSesion;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Tratamiento getCodigoTratam() {
        return codigoTratam;
    }

    public void setCodigoTratam(Tratamiento codigoTratam) {
        this.codigoTratam = codigoTratam;
    }

    public Consultorio getNroConsutorio() {
        return nroConsutorio;
    }

    public void setNroConsutorio(Consultorio nroConsutorio) {
        this.nroConsutorio = nroConsutorio;
    }

    public Masajista getMatricula() {
        return matricula;
    }

    public void setMatricula(Masajista matricula) {
        this.matricula = matricula;
    }

    public DiaDeSpa getCodPack() {
        return codPack;
    }

    public void setCodPack(DiaDeSpa codPack) {
        this.codPack = codPack;
    }

    @Override
    public String toString() {
        return "Sesion{"+ "codSesion=" + codSesion + ", fechaHoraInicio=" + fechaHoraInicio + ", fechaHoraFin=" + fechaHoraFin+ ", estado="
                + estado + ", codigoTratam=" + codigoTratam + ", nroConsutorio=" + nroConsutorio + ", matricula=" + matricula + ", codPack=" + codPack + '}';
    }
}
