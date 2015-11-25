/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.monash.mymonashmate.server.service;

import com.monash.mymonashmate.server.UserAccess;
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
@Path("useraccess")
public class UserAccessFacadeREST extends AbstractFacade<UserAccess> {
    @PersistenceContext(unitName = "MyMonashMateServerPU")
    private EntityManager em;

    public UserAccessFacadeREST() {
        super(UserAccess.class);
    }

    @POST
    @Path("add")
    @Override
    @Consumes({"application/xml", "application/json"})
    public UserAccess createReturn(UserAccess entity) {
        return super.createReturn(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, UserAccess entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public UserAccess find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
    @GET
    @Path("username/{name}")
    @Produces({"application/json"})
    public UserAccess findUserByName(@PathParam("name") String name) {
        return super.findUserByName(name);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<UserAccess> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<UserAccess> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
