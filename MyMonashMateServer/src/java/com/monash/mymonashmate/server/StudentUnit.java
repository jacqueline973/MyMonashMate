/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.monash.mymonashmate.server;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author betty
 */
@Entity
@Table(name = "STUDENT_UNIT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StudentUnit.findAll", query = "SELECT s FROM StudentUnit s"),
    @NamedQuery(name = "StudentUnit.findByStudentId", query = "SELECT s FROM StudentUnit s WHERE s.studentUnitPK.studentId = :studentId"),
    @NamedQuery(name = "StudentUnit.findAllByUnitCode", query = "SELECT s FROM StudentUnit s WHERE lower(s.studentUnitPK.unitCode) = lower(:unitCode)"),
    @NamedQuery(name = "StudentUnit.findByUnitCode", query = "SELECT s FROM StudentUnit s WHERE s.studentUnitPK.unitCode = :unitCode")})
public class StudentUnit implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StudentUnitPK studentUnitPK;

    public StudentUnit() {
    }

    public StudentUnit(StudentUnitPK studentUnitPK) {
        this.studentUnitPK = studentUnitPK;
    }

    public StudentUnit(int studentId, String unitCode) {
        this.studentUnitPK = new StudentUnitPK(studentId, unitCode);
    }

    public StudentUnitPK getStudentUnitPK() {
        return studentUnitPK;
    }

    public void setStudentUnitPK(StudentUnitPK studentUnitPK) {
        this.studentUnitPK = studentUnitPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentUnitPK != null ? studentUnitPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentUnit)) {
            return false;
        }
        StudentUnit other = (StudentUnit) object;
        if ((this.studentUnitPK == null && other.studentUnitPK != null) || (this.studentUnitPK != null && !this.studentUnitPK.equals(other.studentUnitPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.monash.mymonashmate.server.StudentUnit[ studentUnitPK=" + studentUnitPK + " ]";
    }
    
}
