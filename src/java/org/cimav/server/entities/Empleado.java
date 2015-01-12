/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.server.entities;

import java.io.Serializable;
import javax.persistence.Cacheable;
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
import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author juan.calderon
 */
@Entity
@Cacheable(false) 
@Table(name = "empleados")
@XmlRootElement(name = "employees")
//@XmlType(propOrder = { "id", "clave", "consecutivo", "email", "telephone" })
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e"),
    @NamedQuery(name = "Empleado.findById", query = "SELECT e FROM Empleado e WHERE e.id = :id")})
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

    @Column(name = "id_clinica")
    private Short idClinica;

    @Column(name = "id_proyecto")
    private Short idProyecto;

    @Size(max = 40)
    @Column(name = "cuenta_banco")
    private String cuentaBanco;

    @Column(name = "url_photo")
    private String urlPhoto;

    @XmlElement(name = "departamento")
    @JoinColumn(name = "id_departamento", referencedColumnName = "id")
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
    
    @Column(name = "id_banco")
    private Short idBanco;
    
    @Column(name = "has_credito")
    private Boolean hasCredito;
    
    @Column(name = "num_credito")
    private String numCredito;

    @Column(name = "cuenta_cimav")
    private String cuentaCimav;
    
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

    public Short getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Short idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getCuentaBanco() {
        return cuentaBanco;
    }

    public void setCuentaBanco(String cuentaBanco) {
        this.cuentaBanco = cuentaBanco;
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
        if (nombre == null) {
            nombre = "";
        }
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = WordUtils.capitalizeFully(nombre);
    }

    public String getApellidoMaterno() {
        if (apellidoMaterno == null) {
            apellidoMaterno = "";
        }
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno.toUpperCase();
    }

    public String getApellidoPaterno() {
        if (apellidoPaterno == null) {
            apellidoPaterno = "";
        }
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno.toUpperCase();
    }

    public Short getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Short idBanco) {
        this.idBanco = idBanco;
    }

    public Boolean getHasCredito() {
        return hasCredito;
    }

    public void setHasCredito(Boolean hasCredito) {
        this.hasCredito = hasCredito;
    }

    public Short getIdClinica() {
        return idClinica;
    }

    public void setIdClinica(Short idClinica) {
        this.idClinica = idClinica;
    }

    public String getNumCredito() {
        return numCredito;
    }

    public void setNumCredito(String numCredito) {
        this.numCredito = numCredito;
    }

    public String getCuentaCimav() {
        return cuentaCimav;
    }

    public void setCuentaCimav(String cuentaCimav) {
        this.cuentaCimav = cuentaCimav;
    }
    
}
