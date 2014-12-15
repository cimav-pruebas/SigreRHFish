/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.server.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juan.calderon
 */
@Entity
@Table(name = "empleados")
@XmlRootElement(name = "employees", namespace = "empleadiux")
//@XmlType(propOrder = { "id", "clave", "consecutivo", "email", "telephone" })
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e"),
    @NamedQuery(name = "Empleado.findById", query = "SELECT e FROM Empleado e WHERE e.id = :id"),
    @NamedQuery(name = "Empleado.findByCode", query = "SELECT e FROM Empleado e WHERE e.code = :code"),
    @NamedQuery(name = "Empleado.findByName", query = "SELECT e FROM Empleado e WHERE e.name = :name"),
    @NamedQuery(name = "Empleado.findByConsecutivo", query = "SELECT e FROM Empleado e WHERE e.consecutivo = :consecutivo"),
    @NamedQuery(name = "Empleado.findByStatus", query = "SELECT e FROM Empleado e WHERE e.status = :status"),
    @NamedQuery(name = "Empleado.findByCurp", query = "SELECT e FROM Empleado e WHERE e.curp = :curp"),
    @NamedQuery(name = "Empleado.findByRfc", query = "SELECT e FROM Empleado e WHERE e.rfc = :rfc"),
    @NamedQuery(name = "Empleado.findByImss", query = "SELECT e FROM Empleado e WHERE e.imss = :imss"),
    @NamedQuery(name = "Empleado.findByIdproyecto", query = "SELECT e FROM Empleado e WHERE e.idproyecto = :idproyecto"),
    @NamedQuery(name = "Empleado.findByNumcuentabanco", query = "SELECT e FROM Empleado e WHERE e.numcuentabanco = :numcuentabanco")})
public class Empleado extends BaseEntity implements Serializable {

    @Column(name = "consecutivo")
    private Integer consecutivo;

    @Column(name = "status")
    private Short status;

    @Size(max = 100)
    @Column(name = "curp")

    private String curp;
    @Size(max = 50)
    @Column(name = "rfc")
    private String rfc;

    @Size(max = 100)
    @Column(name = "imss")
    private String imss;

    @Column(name = "idproyecto")
    private Short idproyecto;

    @Size(max = 40)
    @Column(name = "numcuentabanco")
    private String numcuentabanco;

    @Column(name = "urlPhoto")
    private String urlPhoto;

    @XmlElement(name = "departamento")
    @JoinColumn(name = "iddepartamento", referencedColumnName = "id")
    @ManyToOne
    private Departamento departamento;

    @JoinColumn(name = "id_grupo", referencedColumnName = "id")
    @ManyToOne
    private Grupo grupo;

    @JoinColumn(name = "id_tabulador", referencedColumnName = "id")
    @ManyToOne
    private Tabulador nivel;

    @Size(max = 40)
    @Column(name = "nombre")
    private String nombre;

    @Size(max = 40)
    @Column(name = "apellido_paterno")
    private String apellidoPaterno;

    @Size(max = 40)
    @Column(name = "apellido_materno")
    private String apellidoMaterno;

    public Integer getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(Integer consecutivo) {
        this.consecutivo = consecutivo;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getImss() {
        return imss;
    }

    public void setImss(String imss) {
        this.imss = imss;
    }

    public Short getIdproyecto() {
        return idproyecto;
    }

    public void setIdproyecto(Short idproyecto) {
        this.idproyecto = idproyecto;
    }

    public String getNumcuentabanco() {
        return numcuentabanco;
    }

    public void setNumcuentabanco(String numcuentabanco) {
        this.numcuentabanco = numcuentabanco;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

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

    public Tabulador getNivel() {
        return nivel;
    }

    public void setNivel(Tabulador nivel) {
        this.nivel = nivel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        this.setFullName();
    }

    private void setFullName() {
        // String fullName = this.getApellidoPaterno().toUpperCase() + " " + this.getApellidoMaterno().toUpperCase() + " " + WordUtils.capitalize(this.nombre);
        String fullName = this.getApellidoPaterno().toUpperCase() + " " + this.getApellidoMaterno().toUpperCase() + " " + this.nombre;
        this.setName(fullName);
    }
    
    public String getApellidoMaterno() {
        if (apellidoMaterno == null) {
            return "";
        }
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
        
        this.setFullName();
    }

    public String getApellidoPaterno() {
        if (apellidoPaterno == null) {
            return "";
        }
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
        
        this.setFullName();
    }

}
