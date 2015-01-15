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
public enum ESede {
    CHIHUAHUA(0, "CHI", "Chihuahua"),
    MONTERREY(1, "MTY", "Monterrey"),
    DURANGO(2, "DGO", "Durango");
    
    private int id;
    private String abrev;
    private String nombre;

    private ESede(int id, String abrev, String nombre) {
        this.id = id;
        this.abrev = abrev;
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

    public String getAbrev() {
        return abrev;
    }

    public void setAbrev(String abrev) {
        this.abrev = abrev;
    }
    
    public static ESede get(int id) {
        ESede result = ESede.CHIHUAHUA; // default
        for (ESede value : ESede.values()) {
            if (value.getId() == id) {
                result = value;
                break;
            }
        }
        return result;
    }

}
