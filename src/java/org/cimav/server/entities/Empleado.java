/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.server.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Temporal;
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
    
    @Column(name = "id_sede")
    private Integer idSede;
    
    @Column(name = "status")
    private Integer idStatus;
    
    @XmlElement(name = "jefe" )
    @JoinColumn(name = "id_jefe", referencedColumnName = "id")
    @ManyToOne
    private Empleado jefe;
    
    @Column(name = "id_tipo_empleado")
    private Integer idTipoEmpleado;
    
    @Column(name = "id_tipo_contrato")
    private Integer idTipoContrato;
    
    @Column(name = "fecha_ingreso")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaIngreso;
    
    @Column(name = "fecha_inicio_contrato")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInicioContrato;
    
    @Column(name = "fecha_fin_contrato")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFinContrato;
    
    @Column(name = "fecha_baja")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaBaja;
    
    @Column(name = "id_tipo_antiguedad")
    private Integer idTipoAntiguedad;
    
    @Column(name = "fecha_antiguedad")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaAntiguedad;
    
    @Column(name = "id_tipo_sni")
    private Integer idTipoSni;
    
    @Column(name = "num_sni")
    private String numSni;
    
    @Column(name = "fecha_sni")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaSni;
    
    @PostLoad
    public void reduceJefe() {
        // TODO Buscar best approach de reducción profundidad de Entidad en Empleado.Jefe
        if( this.getJefe() != null) {
            Empleado miniJefe = new Empleado();
            miniJefe.setId(this.getJefe().getId());
            miniJefe.setCode(this.getJefe().getCode());
            miniJefe.setName(this.getJefe().getName());
            miniJefe.setUrlPhoto(this.getJefe().getUrlPhoto());
            
            this.setJefe(miniJefe);
        }
    }
    
    public Integer getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(Integer consecutivo) {
        this.consecutivo = consecutivo;
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

    public Integer getIdSede() {
        return idSede;
    }

    public void setIdSede(Integer idSede) {
        this.idSede = idSede;
    }

    public Integer getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Integer idStatus) {
        this.idStatus = idStatus;
    }

    public Empleado getJefe() {
        return jefe;
    }

    public void setJefe(Empleado jefe) {
        this.jefe = (Empleado) jefe;
    }

    public Integer getIdTipoEmpleado() {
        return idTipoEmpleado;
    }

    public void setIdTipoEmpleado(Integer idTipoEmpleado) {
        this.idTipoEmpleado = idTipoEmpleado;
    }

    public Integer getIdTipoContrato() {
        return idTipoContrato;
    }

    public void setIdTipoContrato(Integer idTipoContrato) {
        this.idTipoContrato = idTipoContrato;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaInicioContrato() {
        return fechaInicioContrato;
    }

    public void setFechaInicioContrato(Date fechaInicioContrato) {
        this.fechaInicioContrato = fechaInicioContrato;
    }

    public Date getFechaFinContrato() {
        return fechaFinContrato;
    }

    public void setFechaFinContrato(Date fechaFinContrato) {
        this.fechaFinContrato = fechaFinContrato;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public Integer getIdTipoAntiguedad() {
        return idTipoAntiguedad;
    }

    public void setIdTipoAntiguedad(Integer idTipoAntiguedad) {
        this.idTipoAntiguedad = idTipoAntiguedad;
    }

    public Date getFechaAntiguedad() {
        return fechaAntiguedad;
    }

    public void setFechaAntiguedad(Date fechaAntiguedad) {
        this.fechaAntiguedad = fechaAntiguedad;
    }

    public Integer getIdTipoSni() {
        return idTipoSni;
    }

    public void setIdTipoSni(Integer idTipoSni) {
        this.idTipoSni = idTipoSni;
    }

    public String getNumSni() {
        return numSni;
    }

    public void setNumSni(String numSni) {
        this.numSni = numSni;
    }

    public Date getFechaSni() {
        return fechaSni;
    }

    public void setFechaSni(Date fechaSni) {
        this.fechaSni = fechaSni;
    }
    
    
}
