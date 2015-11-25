/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.monash.mymonashmate.server.service;

import com.monash.mymonashmate.server.Unit;
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

/**
 *
 * @author betty
 */
@Stateless
@Path("unit")
public class UnitFacadeREST extends AbstractFacade<Unit> {
    @PersistenceContext(unitName = "MyMonashMateServerPU")
    private EntityManager em;

    public UnitFacadeREST() {
        super(Unit.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Unit entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") String id, Unit entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Unit find(@PathParam("id") String id) {
        return super.find(id);
    }
    
    @GET
    @Path("studentId/{id}")
    @Produces({"application/xml", "application/json"})
    public List<Unit> findAllUnitsByStudentId(@PathParam("id") String id) {
        return super.findAllUnitsByStudentId(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Unit> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Unit> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
