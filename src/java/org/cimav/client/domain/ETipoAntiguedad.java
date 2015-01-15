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
public enum ETipoAntiguedad {
    SIN_ANTIGUEDAD(0, "Sin antigüedad"),
    INVESTIGACION(1, "Investigación"),
    ADMINISTRATIVA(2, "Administrativa");
    
    private int id;
    private String nombre;

    private ETipoAntiguedad(int id, String nombre) {
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
    
    public static ETipoAntiguedad get(int id) {
        ETipoAntiguedad result = ETipoAntiguedad.SIN_ANTIGUEDAD; // default
        for (ETipoAntiguedad value : ETipoAntiguedad.values()) {
            if (value.getId() == id) {
                result = value;
                break;
            }
        }
        return result;
    }
    
}

