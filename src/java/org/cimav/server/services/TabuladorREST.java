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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.cimav.server.entities.Tabulador;

/**
 *
 * @author juan.calderon
 */
@Stateless
@Path("tabulador")
public class TabuladorREST extends AbstractREST<Tabulador> {

    @PersistenceContext(unitName = "SigreRHFishPU")
    private EntityManager em;

    public TabuladorREST() {
        super(Tabulador.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Tabulador find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
    @GET
    @Override
    @Produces("application/json")
    public List<Tabulador> findAll() {
        return super.findAll();
    }
    
}
