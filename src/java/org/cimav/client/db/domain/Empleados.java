/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.client.db.domain;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author juan.calderon
 */
public class Empleados {
    
    private List<Empleado> empleados;

    public Empleados() {
    }

    public Empleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.empleados);
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
        final Empleados other = (Empleados) obj;
        if (!Objects.equals(this.empleados, other.empleados)) {
            return false;
        }
        return true;
    }
 
    
}
