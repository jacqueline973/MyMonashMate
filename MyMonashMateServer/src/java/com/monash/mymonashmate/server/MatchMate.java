/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.monash.mymonashmate.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author betty
 */
public class MatchMate implements Serializable {
    private Student student;
    private List<Course> courses = new ArrayList<Course>();
    private List<Unit> units = new ArrayList<Unit>();
    private List<StudentSetting> studentSettings = new ArrayList<StudentSetting>();

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public List<StudentSetting> getStudentSettings() {
        return studentSettings;
    }

    public void setStudentSettings(List<StudentSetting> studentSettings) {
        this.studentSettings = studentSettings;
    }
    
}
