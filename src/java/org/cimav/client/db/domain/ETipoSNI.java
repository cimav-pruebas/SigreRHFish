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
public enum ETipoSNI {
    NO_APLICA(0, "No aplica"),
    CANDIDATO(1, "Candidato"),
    NIVEL_I(2, "Nivel I"),
    NIVEL_II(3, "Nivel II"),
    NIVEL_III(4, "Nivel III");
    
    private int id;
    private String nombre;

    private ETipoSNI(int id, String nombre) {
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
    
    public static ETipoSNI get(int id) {
        ETipoSNI result = ETipoSNI.NO_APLICA; // default
        for (ETipoSNI value : ETipoSNI.values()) {
            if (value.getId() == id) {
                result = value;
                break;
            }
        }
        return result;
    }
        
}
