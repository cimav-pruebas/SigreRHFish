/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.domain;

import com.google.gwt.view.client.ProvidesKey;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author juan.calderon
 */
public class Empleado implements Comparable<Empleado> {

    private Integer id;
    private String clave;
    private String urlPhoto;

    public Empleado() {
        this.id = -1; // evita que la Gwt-Validation falle por nulo
    }
    
    /**
     * The key provider that provides the unique ID of a contact.
     */
    public static final ProvidesKey<Empleado> KEY_PROVIDER = new ProvidesKey<Empleado>() {
        @Override
        public Object getKey(Empleado item) {
            Object r = item == null ? null : item.getId();
            return r;
        }
    };
    
    @Override
    public int compareTo(Empleado item) {
        //TODO el -item es correcto?
        int r = (item == null || item.clave == null) ? -1 : -item.clave.compareTo(clave);
        return r;
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

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.clave);
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
        final Empleado other = (Empleado) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.clave, other.clave)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Empleado{" + "id=" + id + ", clave=" + clave + '}';
    }
    
}
