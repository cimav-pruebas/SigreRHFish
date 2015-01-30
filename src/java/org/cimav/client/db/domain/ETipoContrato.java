/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.db.domain;

/**
 *
 * @author juan.calderon
 */
public enum ETipoContrato {
    DETERMINADO(0, "Determinado"),
    INDETERMINADO(1, "Indeterminado");
    
    private int id;
    private String nombre;

    private ETipoContrato(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public static ETipoContrato get(int id) {
        ETipoContrato result = ETipoContrato.DETERMINADO; // default
        for (ETipoContrato value : ETipoContrato.values()) {
            if (value.getId() == id) {
                result = value;
                break;
            }
        }
        return result;
    }
    
}

