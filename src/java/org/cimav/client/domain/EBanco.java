/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.domain;

/**
 *
 * @author juan.calderon
 */
public enum EBanco {
    BANORTE(0, "Banorte"),
    BANCOMER(1, "Bancomer"),
    BANAMEX(2, "Banamex");
    
    private int id;
    private String nombre;

    private EBanco(int id, String nombre) {
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
    
    public static EBanco get(int id) {
        EBanco result = EBanco.BANORTE; // default
        for (EBanco value : EBanco.values()) {
            if (value.getId() == id) {
                result = value;
                break;
            }
        }
        return result;
    }
}
