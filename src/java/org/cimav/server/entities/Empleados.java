/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.server.entities;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juan.calderon
 */
@XmlRootElement
//@XmlSeeAlso(Empleado.class)
public class Empleados {
  
    @XmlElement // necesario
    private List<Empleado> empleados;

    public Empleados() {
    }
    
    public Empleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }
    
    public List<Empleado> getEmpleados() {
        return empleados;
    }
    
}
