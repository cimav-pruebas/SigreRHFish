/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.server.services;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.cimav.server.entities.Empleado;

/**
 *
 * @author juan.calderon
 */
@Stateless
@Path("empleado")
public class EmpleadoREST extends AbstractREST<Empleado> {
    
    @PersistenceContext(unitName = "SigreRHFishPU")
    private EntityManager em;

    public EmpleadoREST() {
        super(Empleado.class);
    }

    @POST
    @Override
    @Consumes("application/json")
    public void create(Empleado entity) {
        this.setName(entity);
        super.create(entity);
    }

    private void setName(Empleado entity) {
        // al guardar (POST y PUT), actualizar el Name
        String name = entity.getApellidoPaterno() + " " + entity.getApellidoMaterno() + " " + entity.getNombre();
        entity.setName(name);
    }
    
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public void edit(@PathParam("id") Integer id, Empleado entity) {
        this.setName(entity);
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Empleado find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces("application/json")
    public List<Empleado> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces("application/json")
    public List<Empleado> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
