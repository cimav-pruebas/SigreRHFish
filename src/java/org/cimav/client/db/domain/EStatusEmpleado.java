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
public enum EStatusEmpleado {
    ACTIVO(0, "Activo"),
    BAJA(1, "Baja"),
    LIC_CON_SUELDO(2, "Licencia con sueldo"),
    LIC_SIN_SUELDO(3, "Licencia sin sueldo"),
    SABATICO(4, "Sabático");
    
    private int id;
    private String nombre;

    private EStatusEmpleado(int id, String nombre) {
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
    
    public static EStatusEmpleado get(int id) {
        EStatusEmpleado result = EStatusEmpleado.ACTIVO; // default
        for (EStatusEmpleado value : EStatusEmpleado.values()) {
            if (value.getId() == id) {
                result = value;
                break;
            }
        }
        return result;
    }
    
}
