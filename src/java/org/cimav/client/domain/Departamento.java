/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.domain;

//import org.codehaus.jackson.annotate.JsonProperty;
import com.google.gwt.view.client.ProvidesKey;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author juan.calderon
 */
public class Departamento implements Comparable<Departamento> {

    // https://code.google.com/p/gwt-jsonmaker/  No requerido de momento
    // public interface DepartamentoJsonizer extends Jsonizer {}

    /**
     * The key provider that provides the unique ID of a contact.
     */
    public static final ProvidesKey<Departamento> KEY_PROVIDER = new ProvidesKey<Departamento>() {
        @Override
        public Object getKey(Departamento item) {
            Object r = item == null ? null : item.getId();
            return r;
        }
    };

    @Override
    public int compareTo(Departamento item) {
        //TODO compareTo(DeptoInfo item) hacerlo bien
        int r = (item == null || item.codigo == null) ? -1 : -item.codigo.compareTo(codigo);
        return r;
    }

    private Integer id;
    private String codigo;
    @NotNull
    @Size(min = 10, message = "Nombre deber tener al menos 10 caracteres")
    private String nombre;
    private Integer status;

    public Departamento() {
    }
    
    public Departamento(Integer id, String codigo, String nombre, Integer status) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.status = status;
    }

    @Pattern(regexp="(.*), (.*)")
    public String getFullName() {
        return this.codigo + ", " + this.nombre;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Departamento)) {
            return false;
        }
        Departamento other = (Departamento) obj;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + this.id;
        return hash;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "Departamento{" + "id=" + id + ", codigo=" + codigo + '}';
    }

}
