/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.monash.mymonashmate.server.service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author betty
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.monash.mymonashmate.server.service.CourseFacadeREST.class);
        resources.add(com.monash.mymonashmate.server.service.CourseUnitFacadeREST.class);
        resources.add(com.monash.mymonashmate.server.service.StudentCourseFacadeREST.class);
        resources.add(com.monash.mymonashmate.server.service.StudentFacadeREST.class);
        resources.add(com.monash.mymonashmate.server.service.StudentSettingFacadeREST.class);
        resources.add(com.monash.mymonashmate.server.service.StudentUnitFacadeREST.class);
        resources.add(com.monash.mymonashmate.server.service.UnitFacadeREST.class);
        resources.add(com.monash.mymonashmate.server.service.UserAccessFacadeREST.class);
    }
    
}
