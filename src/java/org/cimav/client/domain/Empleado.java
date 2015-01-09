/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author juan.calderon
 */
public class Empleado extends BaseDomain<Empleado> { 

    private String rfc;
    private String urlPhoto;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    
    @JsonIgnore
    private EClinica clinica;
    @JsonIgnore
    private EBanco banco;
    
    private Grupo grupo;
    private Tabulador nivel;
    private Departamento departamento;

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public Tabulador getNivel() {
        return nivel;
    }

    public void setNivel(Tabulador nivel) {
        this.nivel = nivel;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public EClinica getClinica() {
        return clinica;
    }

    public void setClinica(EClinica clinica) {
        this.clinica = clinica;
    }

    public EBanco getBanco() {
        return banco;
    }

    public void setBanco(EBanco banco) {
        this.banco = banco;
    }
    
}
