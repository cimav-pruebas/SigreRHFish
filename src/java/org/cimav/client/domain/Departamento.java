/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.domain;

//import org.codehaus.jackson.annotate.JsonProperty;
import com.google.gwt.view.client.ProvidesKey;
import java.util.Objects;
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
    @Pattern(regexp="^[A-Z][a-zA-Z]{1,7}$", message = "Código debe ser de 2 a 8 letras empezando con mayúscula.")
    private String codigo;
    @Size(min = 10, message = "Nombre deber tener al menos 10 caracteres")
    private String nombre;
    private Integer status;

    public Departamento() {
        this.id = -1; // evita que la Gwt-Validation falle por nulo
    }
    
    public Departamento(Integer id, String codigo, String nombre, Integer status) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        hash = 83 * hash + Objects.hashCode(this.codigo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Departamento other = (Departamento) obj;
        if (this.id > 0)  {
            // si hay Id, igual por Id
            if (!Objects.equals(this.id, other.id)) {
                return false;
            }
        } else {
            // Si no hay ID, igual por Codigo
            if (!Objects.equals(this.codigo, other.codigo)) {
                return false;
            }
        }
        return true;
    }
    
    
//    @Override
//    public boolean equals(Object obj) {
//        if (!(obj instanceof Departamento)) {
//            return false;
//        }
//        Departamento other = (Departamento) obj;
//        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 5;
//        hash = 89 * hash + this.id;
//        return hash;
//    }

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
