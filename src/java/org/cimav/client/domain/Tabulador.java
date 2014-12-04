/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.domain;

import com.google.gwt.view.client.ProvidesKey;
import java.util.Objects;

/**
 *
 * @author juan.calderon
 */
public class Tabulador implements Comparable<Tabulador> {

    private Integer id;
    private String nivel;
    private String nombre;

    public Tabulador() {
    }

    public Tabulador(Integer id, String nivel, String nombre) {
        this.id = id;
        this.nivel = nivel;
        this.nombre = nombre;
    }
    
    /**
     * The key provider that provides the unique ID of a contact.
     */
    public static final ProvidesKey<Tabulador> KEY_PROVIDER = new ProvidesKey<Tabulador>() {
        @Override
        public Object getKey(Tabulador item) {
            Object r = item == null ? null : item.getId();
            return r;
        }
    };

    
    @Override
    public int compareTo(Tabulador item) {
        int r = (item == null || item.nombre == null) ? -1 : -item.nombre.compareTo(nombre);
        return r;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final Tabulador other = (Tabulador) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Tabulador{" + "id=" + id + ", nivel=" + nivel + ", nombre=" + nombre + '}';
    }
    
    
}
