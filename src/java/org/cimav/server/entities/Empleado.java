/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.server.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@XmlRootElement(name="employees", namespace = "empleadiux")
//@XmlType(propOrder = { "id", "clave", "consecutivo", "email", "telephone" })
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e"),
    @NamedQuery(name = "Empleado.findById", query = "SELECT e FROM Empleado e WHERE e.id = :id"),
    @NamedQuery(name = "Empleado.findByClave", query = "SELECT e FROM Empleado e WHERE e.clave = :clave"),
    @NamedQuery(name = "Empleado.findByConsecutivo", query = "SELECT e FROM Empleado e WHERE e.consecutivo = :consecutivo"),
    @NamedQuery(name = "Empleado.findByStatus", query = "SELECT e FROM Empleado e WHERE e.status = :status"),
    @NamedQuery(name = "Empleado.findByCurp", query = "SELECT e FROM Empleado e WHERE e.curp = :curp"),
    @NamedQuery(name = "Empleado.findByRfc", query = "SELECT e FROM Empleado e WHERE e.rfc = :rfc"),
    @NamedQuery(name = "Empleado.findByImss", query = "SELECT e FROM Empleado e WHERE e.imss = :imss"),
    @NamedQuery(name = "Empleado.findByIdproyecto", query = "SELECT e FROM Empleado e WHERE e.idproyecto = :idproyecto"),
    @NamedQuery(name = "Empleado.findByNumcuentabanco", query = "SELECT e FROM Empleado e WHERE e.numcuentabanco = :numcuentabanco")})
public class Empleado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 5)
    @Column(name = "clave")
    private String clave;
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
    
    @XmlElement(name="king")
    @JoinColumn(name = "idnivel", referencedColumnName = "id")
    @ManyToOne
    private Tabulador nivel;
    
//    @OneToMany(mappedBy = "jefe")
//    private Collection<Empleado> empleadoCollection;
    
    @JoinColumn(name = "idjefe", referencedColumnName = "id")
    @ManyToOne
    private Empleado jefe;
    
    @XmlElement(name="departamento")
    @JoinColumn(name = "iddepartamento", referencedColumnName = "id")
    @ManyToOne
    private Departamento departamento;

    public Empleado() {
    }

    public Empleado(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

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

    public Tabulador getNivel() {
        return nivel;
    }

    public void setNivel(Tabulador nivel) {
        this.nivel = nivel;
    }

//    @XmlTransient
//    public Collection<Empleado> getEmpleadoCollection() {
//        return empleadoCollection;
//    }
//
//    public void setEmpleadoCollection(Collection<Empleado> empleadoCollection) {
//        this.empleadoCollection = empleadoCollection;
//    }

    public Empleado getJefe() {
        return jefe;
    }

    public void setJefe(Empleado jefe) {
        this.jefe = jefe;
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
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.cimav.server.entities.Empleado[ id=" + id + " - " + consecutivo + " ]";
    }
    
}
