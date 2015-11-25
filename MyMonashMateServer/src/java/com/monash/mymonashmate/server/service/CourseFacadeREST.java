/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.monash.mymonashmate.server.service;

import com.monash.mymonashmate.server.Course;
import com.monash.mymonashmate.server.Unit;
import java.util.List;
import java.util.Set;
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
@Path("course")
public class CourseFacadeREST extends AbstractFacade<Course> {
    @PersistenceContext(unitName = "MyMonashMateServerPU")
    private EntityManager em;

    public CourseFacadeREST() {
        super(Course.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Course entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") String id, Course entity) {
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
    public Course find(@PathParam("id") String id) {
        return super.find(id);
    }
    
    @GET
    @Path("studentId/{id}")
    @Produces({"application/xml", "application/json"})
    public List<Course> findAllCoursesByStudentId(@PathParam("id") String id) {
        return super.findAllCoursesByStudentId(id);
    }
    
    @GET
    @Path("unit/{courseCodeList}")
    @Produces({"application/xml", "application/json"})
    public List<Unit> getUnits(@PathParam("courseCodeList") String courseCodeList) {
        return super.findUnitsByCourse(courseCodeList.split(","));
    }

    @GET
    @Path("all")
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Course> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Course> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
