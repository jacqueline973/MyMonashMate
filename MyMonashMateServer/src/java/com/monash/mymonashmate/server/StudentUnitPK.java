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
public class StudentUnitPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "STUDENT_ID")
    private int studentId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "UNIT_CODE")
    private String unitCode;

    public StudentUnitPK() {
    }

    public StudentUnitPK(int studentId, String unitCode) {
        this.studentId = studentId;
        this.unitCode = unitCode;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) studentId;
        hash += (unitCode != null ? unitCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentUnitPK)) {
            return false;
        }
        StudentUnitPK other = (StudentUnitPK) object;
        if (this.studentId != other.studentId) {
            return false;
        }
        if ((this.unitCode == null && other.unitCode != null) || (this.unitCode != null && !this.unitCode.equals(other.unitCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.monash.mymonashmate.server.StudentUnitPK[ studentId=" + studentId + ", unitCode=" + unitCode + " ]";
    }
    
}
