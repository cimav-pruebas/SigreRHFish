/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.domain;

import com.google.gwt.view.client.ProvidesKey;
import java.util.Objects;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author juan.calderon
 * @param <T>
 */
public class BaseDomain<T> implements Comparable<BaseDomain> {

    private Integer id;
    
    @Pattern(regexp="^[A-Z][a-zA-Z]{1,7}$", message = "Código debe ser de 2 a 8 letras empezando con mayúscula.")
    private String code;
    
    @Size(min = 10, message = "Nombre deber tener al menos 10 caracteres")
    private String name;

    public BaseDomain() {
        this.id = -1; // evita que la Gwt-Validation falle por nulo
    }

    /**
     * The key provider that provides the unique ID of a contact.
     */
    public static final ProvidesKey<BaseDomain> KEY_PROVIDER = new ProvidesKey<BaseDomain>() {
        @Override
        public Object getKey(BaseDomain item) {
            Object r = item == null ? null : item.getId();
            return r;
        }
    };
    
    @Override
    public int compareTo(BaseDomain obj) {
        // TODO REvisar el Comparable del BaseDomain
        int r = (obj == null || obj.code == null) ? -1 : -obj.code.compareTo(this.code);
        return r;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        if (code == null) {
            code = "";
        }
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        if (name == null) {
            name = "";
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BaseDomain{" + "id=" + id + ", code=" + code + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.id);
        hash = 11 * hash + Objects.hashCode(this.code);
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
        final BaseDomain<?> other = (BaseDomain<?>) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        return true;
    }
    
    
    
}
