/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.monash.mymonashmate.server.service;

import com.monash.mymonashmate.server.MatchCriteria;
import com.monash.mymonashmate.server.MatchMate;
import com.monash.mymonashmate.server.MatchMateResult;
import com.monash.mymonashmate.server.Profile;
import com.monash.mymonashmate.server.Student;
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
@Path("student")
public class StudentFacadeREST extends AbstractFacade<Student> {
    @PersistenceContext(unitName = "MyMonashMateServerPU")
    private EntityManager em;

    public StudentFacadeREST() {
        super(Student.class);
    }

    @POST
    @Path("create")
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Student entity) {
        super.create(entity);
    }
    
    @POST
    @Path("profile")
    @Consumes({"application/xml", "application/json"})
    @Override
    public void createProfile(Profile entity) {
        System.out.print(entity.toString());
        super.createProfile(entity);
    }
    
    @POST
    @Path("profile/studentsetting")
    @Consumes({"application/xml", "application/json"})
    @Override
    public void updateStudentSetting(Profile entity) {
        System.out.print(entity.toString());
        super.updateStudentSetting(entity);
    }
    
    @POST
    @Path("profile/update")
    @Consumes({"application/xml", "application/json"})
    @Override
    public void updateProfile(Profile entity) {
        System.out.print(entity.toString());
        super.updateProfile(entity);
    }
    
    @POST
    @Path("match")
    @Consumes({"application/xml", "application/json"})
    @Override
    public MatchMateResult matchStudents(MatchCriteria criteria) {
        System.out.print(criteria.toString());
        return super.matchStudents(criteria);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Student entity) {
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
    public Student find(@PathParam("id") String id) {
        try {
            int integerId = Integer.valueOf(id);
            return super.find(integerId);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    @GET
    @Path("profile/{studentId}")
    @Produces({"application/xml", "application/json"})
    public Profile getProfile(@PathParam("studentId") String studentId) {
        return super.getProfile(studentId);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Student> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Student> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
