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
public enum EClinica {

    MORELOS(0, "Morelos"),
    SAN_FELIPE(1, "San Felipe");

    private int id;
    private String nombre;

    private EClinica(int id, String nombre) {
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

    public static EClinica get(int id) {
        EClinica result = EClinica.MORELOS; // default
        for (EClinica value : EClinica.values()) {
            if (value.getId() == id) {
                result = value;
                break;
            }
        }
        return result;
    }

}
