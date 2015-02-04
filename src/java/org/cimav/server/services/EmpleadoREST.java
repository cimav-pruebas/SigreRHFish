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
import org.cimav.server.entities.Empleado;
import org.cimav.server.entities.Empleados;

/**
 *
 * @author juan.calderon
 */
@Stateless // requerido; si no, getEntityManager().persist(entity); lanza TransactionRequiredException
@Path("empleado")
public class EmpleadoREST extends AbstractREST<Empleado> {

    @PersistenceContext(unitName = "SigreRHFishPU")
    private EntityManager em;

    public EmpleadoREST() {
        super(Empleado.class);
    }

//    @POST
//    @Override
//    @Consumes("application/json")
//    public void create(Empleado entity) {
//        this.setName(entity);
//        super.create(entity);
//    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Empleado insert(Empleado entity) {
        this.setName(entity);
        super.insert(entity); // <-- regresa con el Id nuevo
        return entity; 
    }

    private void setName(Empleado entity) {
        // al guardar (POST y PUT), actualizar el Name
        String name = entity.getApellidoPaterno() + " " + entity.getApellidoMaterno() + " " + entity.getNombre();
        entity.setName(name);
    }

    @PUT
    //@Path("{id}")
    @Consumes("application/json")
    @Override
    public void edit(/*@PathParam("id") Integer id,*/Empleado entity) {
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
        long tStart = System.nanoTime();
        
        List<Empleado> result = super.findAll();
        
        Empleados empleados = new Empleados(result);
        
        long tTot = Math.round((System.nanoTime() - tStart) / 1000.0);
        System.out.println(" Entity.findAll >>> " + tTot);
        
        return result;
    }
    
    @GET
    @Produces("application/json")
    @Path("/test")
    public Empleados findAllEmpleados() {
        long tStart = System.nanoTime();
        
        List<Empleado> result = super.findAll();
        
        Empleados empleados = new Empleados(result);
        
        long tTot = Math.round((System.nanoTime() - tStart) / 1000.0);
        System.out.println(" Entity.findAllEmpleados >>> " + tTot);
        
        return empleados;
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

    @GET
    @Path("base")
    // @JsonView(View.Base.class) no funciona
    @Produces("application/json")
    public List<Empleado> findAllBase() {
        
        // usa SELECT NEW CONSTRUCTOR en lugar del @JsonView que no funcionó
        Query query = getEntityManager().createQuery("SELECT NEW org.cimav.server.entities.Empleado(e.id, e.code, e.name, e.urlPhoto) FROM Empleado AS e", Empleado.class);
        List<Empleado> results = query.getResultList();
        
        return results;
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
