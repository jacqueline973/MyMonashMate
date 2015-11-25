/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.monash.mymonashmate.server;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author betty
 */
@Embeddable
public class StudentSettingPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "STUDENT_ID")
    private int studentId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "ATTRIBUTE")
    private String attribute;

    public StudentSettingPK() {
    }

    public StudentSettingPK(int studentId, String attribute) {
        this.studentId = studentId;
        this.attribute = attribute;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) studentId;
        hash += (attribute != null ? attribute.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentSettingPK)) {
            return false;
        }
        StudentSettingPK other = (StudentSettingPK) object;
        if (this.studentId != other.studentId) {
            return false;
        }
        if ((this.attribute == null && other.attribute != null) || (this.attribute != null && !this.attribute.equals(other.attribute))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.monash.mymonashmate.server.StudentSettingPK[ studentId=" + studentId + ", attribute=" + attribute + " ]";
    }
    
}
