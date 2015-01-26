/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import java.util.Date;

/**
 *
 * @author juan.calderon
 */
public final class Empleado extends BaseDomain<Empleado> { 
    
    private String rfc;
    private String curp;
    private String numCredito;
    private String cuentaCimav;
    private String cuentaBanco;
    private String imss;
    private String urlPhoto;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    
    @JsonIgnore private EClinica clinica;
    private Integer idClinica;

    @JsonIgnore private EBanco banco;
    private Integer idBanco;
    
    private Boolean hasCredito;
    
    private Grupo grupo;
    private Tabulador nivel;
    private Departamento departamento;
    
    @JsonIgnore private ESede sede;
    private Integer idSede;
    
    @JsonIgnore private EStatusEmpleado status;
    private Integer idStatus;
    
    @JsonIgnore private ETipoEmpleado tipoEmpleado;
    private Integer idTipoEmpleado;
    
    @JsonIgnore private ETipoContrato tipoContrato;
    private Integer idTipoContrato;
    
    @JsonIgnore private ETipoAntiguedad tipoAntiguedad;
    private Integer idTipoAntiguedad;
    
    @JsonIgnore private ETipoSNI tipoSNI;
    private Integer idTipoSni;
    
    private Empleado jefe;
    
    private Date fechaIngreso;
    private Date fechaInicioContrato;
    private Date fechaFinContrato;
    private Date fechaBaja;
    private Date fechaAntiguedad;
    private Date fechaSni;

    public EBanco getBanco() {
        return banco;
    }

    public void setBanco(EBanco banco) {
        this.banco = banco;
        this.idBanco = banco != null ? banco.getId() : 0;
    }

    public Integer getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Integer idBanco) {
        this.banco = EBanco.get(idBanco);
        this.idBanco = idBanco;
    }
    
    private String numSni;

    public Empleado() {

        this.setStatus(EStatusEmpleado.ACTIVO);
        this.setTipoEmpleado(ETipoEmpleado.NORMAL);
        this.setTipoContrato(ETipoContrato.DETERMINADO);
        
        Date today = new Date();
        this.setFechaIngreso(today);
        this.setFechaInicioContrato(today);
        Date oneYear = new Date(); // un año después
        CalendarUtil.addMonthsToDate(oneYear, 12);
        this.setFechaFinContrato(oneYear);
        
        this.setTipoAntiguedad(ETipoAntiguedad.ADMINISTRATIVA);
        this.setTipoSNI(ETipoSNI.NO_APLICA);
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
        this.idClinica = clinica != null ? clinica.getId() : 0;
    }

    public Integer getIdClinica() {
        return idClinica;
    }

    public void setIdClinica(Integer idClinica) {
        this.clinica = EClinica.get(idClinica);
        this.idClinica = idClinica;
    }

    public Boolean getHasCredito() {
        return hasCredito;
    }

    public void setHasCredito(Boolean hasCredito) {
        this.hasCredito = hasCredito;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
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

    public String getCuentaBanco() {
        return cuentaBanco;
    }

    public void setCuentaBanco(String cuentaBanco) {
        this.cuentaBanco = cuentaBanco;
    }

    public String getImss() {
        return imss;
    }

    public void setImss(String imss) {
        this.imss = imss;
    }

    public ESede getSede() {
        return sede;
    }

    public void setSede(ESede sede) {
        this.sede = sede;
        this.idSede = sede != null ? sede.getId() : 0;
    }

    public Integer getIdSede() {
        return idSede;
    }

    public void setIdSede(Integer idSede) {
        this.sede = ESede.get(idSede);
        this.idSede = idSede;
    }

    public EStatusEmpleado getStatus() {
        return status;
    }

    public void setStatus(EStatusEmpleado status) {
        this.status = status;
        this.idStatus = status != null ? status.getId() : 0;
    }

    public Integer getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Integer idStatus) {
        this.status = EStatusEmpleado.get(idStatus);
        this.idStatus = idStatus;
    }

    public ETipoEmpleado getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(ETipoEmpleado tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
        this.idTipoEmpleado = tipoEmpleado != null ? tipoEmpleado.getId() : 0;
    }

    public Integer getIdTipoEmpleado() {
        return idTipoEmpleado;
    }

    public void setIdTipoEmpleado(Integer idTipoEmpleado) {
        this.tipoEmpleado = ETipoEmpleado.get(idTipoEmpleado);
        this.idTipoEmpleado = idTipoEmpleado;
    }

    public ETipoContrato getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(ETipoContrato tipoContrato) {
        this.tipoContrato = tipoContrato;
        this.idTipoContrato = tipoContrato != null ? tipoContrato.getId() : 0;
    }

    public Integer getIdTipoContrato() {
        return idTipoContrato;
    }

    public void setIdTipoContrato(Integer idTipoContrato) {
        this.tipoContrato = ETipoContrato.get(idTipoContrato);
       this.idTipoContrato = idTipoContrato;
    }

    public ETipoAntiguedad getTipoAntiguedad() {
        return tipoAntiguedad;
    }

    public void setTipoAntiguedad(ETipoAntiguedad tipoAntiguedad) {
        this.tipoAntiguedad = tipoAntiguedad;
        this.idTipoAntiguedad = tipoAntiguedad != null ? tipoAntiguedad.getId() : 0;
    }

    public Integer getIdTipoAntiguedad() {
        return idTipoAntiguedad;
    }

    public void setIdTipoAntiguedad(Integer idTipoAntiguedad) {
        this.tipoAntiguedad = ETipoAntiguedad.get(idTipoAntiguedad);
        this.idTipoAntiguedad = idTipoAntiguedad;
    }

    public ETipoSNI getTipoSNI() {
        return tipoSNI;
    }

    public void setTipoSNI(ETipoSNI tipoSNI) {
        this.tipoSNI = tipoSNI;
        this.idTipoSni = tipoSNI != null ? tipoSNI.getId() : 0;
    }

    public Integer getIdTipoSni() {
        return idTipoSni;
    }

    public void setIdTipoSni(Integer idTipoSni) {
        this.tipoSNI = ETipoSNI.get(idTipoSni);
        this.idTipoSni = idTipoSni;
    }

    public Empleado getJefe() {
        return jefe;
    }

    public void setJefe(Empleado jefe) {
        this.jefe = jefe;
    }

    public String getNumSni() {
        return numSni;
    }

    public void setNumSni(String numSni) {
        this.numSni = numSni;
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

    public Date getFechaAntiguedad() {
        return fechaAntiguedad;
    }

    public void setFechaAntiguedad(Date fechaAntiguedad) {
        this.fechaAntiguedad = fechaAntiguedad;
    }

    public Date getFechaSni() {
        return fechaSni;
    }

    public void setFechaSni(Date fechaSni) {
        this.fechaSni = fechaSni;
    }
    
}
