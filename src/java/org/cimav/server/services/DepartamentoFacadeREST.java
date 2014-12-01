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
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.cimav.server.entities.Departamento;

/**
 *
 * @author juan.calderon
 */
@Stateless
@Path("departamento")
public class DepartamentoFacadeREST extends AbstractFacade<Departamento> {
    @PersistenceContext(unitName = "SigreRHFishPU")
    private EntityManager em;

    public DepartamentoFacadeREST() {
        super(Departamento.class);
    }

    @POST
    @Override
    @Consumes("application/json")
    public void create(Departamento entity) {
        super.create(entity);
    }

    @POST
    @Path("add")
    @Consumes("application/json")
    @Produces("application/json")
    public Departamento createWithReturn(Departamento entity) {
        super.create(entity);

        // TODO Eliminar el AbstractFacade y hacerlo todo en directo o dejarlo solo para instanciar el EntityManager
        Query q = getEntityManager().createNamedQuery("Departamento.findByCodigo");
        q.setParameter("codigo", entity.getCodigo());
        Departamento result = (Departamento) q.getSingleResult();
        return result;
    }

    @PUT
    @Path("update/{id}")
    @Consumes("application/json")
    public void edit(@PathParam("id") Integer id, Departamento entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("/delete/{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Departamento find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces("application/json")
    public List<Departamento> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces("application/json")
    public List<Departamento> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
