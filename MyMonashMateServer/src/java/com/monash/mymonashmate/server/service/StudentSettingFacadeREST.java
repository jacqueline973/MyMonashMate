/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.monash.mymonashmate.server.service;

import com.monash.mymonashmate.server.StudentSetting;
import com.monash.mymonashmate.server.StudentSettingPK;
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
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author betty
 */
@Stateless
@Path("com.monash.mymonashmate.server.studentsetting")
public class StudentSettingFacadeREST extends AbstractFacade<StudentSetting> {
    @PersistenceContext(unitName = "MyMonashMateServerPU")
    private EntityManager em;

    private StudentSettingPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;studentId=studentIdValue;attribute=attributeValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        com.monash.mymonashmate.server.StudentSettingPK key = new com.monash.mymonashmate.server.StudentSettingPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> studentId = map.get("studentId");
        if (studentId != null && !studentId.isEmpty()) {
            key.setStudentId(new java.lang.Integer(studentId.get(0)));
        }
        java.util.List<String> attribute = map.get("attribute");
        if (attribute != null && !attribute.isEmpty()) {
            key.setAttribute(attribute.get(0));
        }
        return key;
    }

    public StudentSettingFacadeREST() {
        super(StudentSetting.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(StudentSetting entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") PathSegment id, StudentSetting entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        com.monash.mymonashmate.server.StudentSettingPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public StudentSetting find(@PathParam("id") PathSegment id) {
        com.monash.mymonashmate.server.StudentSettingPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<StudentSetting> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<StudentSetting> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
