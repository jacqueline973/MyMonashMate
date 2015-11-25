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
public class Profile implements Serializable {

    private String userId;
    private Student student;
    private List<String> selectedCourseCodes = new ArrayList<String>();
    private List<String> selectedUnitCodes = new ArrayList<String>();
    private List<StudentSetting> studentSettings = new ArrayList<StudentSetting>();

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<String> getSelectedCourseCodes() {
        return selectedCourseCodes;
    }

    public void setSelectedCourseCodes(List<String> selectedCourseCodes) {
        this.selectedCourseCodes = selectedCourseCodes;
    }

    public List<String> getSelectedUnitCodes() {
        return selectedUnitCodes;
    }

    public void setSelectedUnitCodes(List<String> selectedUnitCodes) {
        this.selectedUnitCodes = selectedUnitCodes;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<StudentSetting> getStudentSettings() {
        return studentSettings;
    }

    public void setStudentSettings(List<StudentSetting> studentSettings) {
        this.studentSettings = studentSettings;
    }

    @Override
    public String toString() {
        return "Profile{" + "userId=" + userId + ", student=" + student + ", selectedCourseCodes=" + selectedCourseCodes + ", selectedUnitCodes=" + selectedUnitCodes + ", studentSettings=" + studentSettings + '}';
    }
    
}
