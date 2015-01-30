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
public enum ETipoEmpleado {

    NORMAL(0, "Normal"),
    CATEDRA(1, "Cátedra/Repatriación");

    private int id;
    private String nombre;

    private ETipoEmpleado(int id, String nombre) {
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

    public static ETipoEmpleado get(int id) {
        ETipoEmpleado result = ETipoEmpleado.NORMAL; // default
        for (ETipoEmpleado value : ETipoEmpleado.values()) {
            if (value.getId() == id) {
                result = value;
                break;
            }
        }
        return result;
    }

}
