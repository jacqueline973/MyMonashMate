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
@Table(name = "STUDENT_COURSE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StudentCourse.findAll", query = "SELECT s FROM StudentCourse s"),
    @NamedQuery(name = "StudentCourse.findByStudentId", query = "SELECT s FROM StudentCourse s WHERE s.studentCoursePK.studentId = :studentId"),
    @NamedQuery(name = "StudentCourse.findByCourseCode", query = "SELECT s FROM StudentCourse s WHERE lower(s.studentCoursePK.courseCode) = lower(:courseCode)")})
public class StudentCourse implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StudentCoursePK studentCoursePK;

    public StudentCourse() {
    }

    public StudentCourse(StudentCoursePK studentCoursePK) {
        this.studentCoursePK = studentCoursePK;
    }

    public StudentCourse(int studentId, String courseCode) {
        this.studentCoursePK = new StudentCoursePK(studentId, courseCode);
    }

    public StudentCoursePK getStudentCoursePK() {
        return studentCoursePK;
    }

    public void setStudentCoursePK(StudentCoursePK studentCoursePK) {
        this.studentCoursePK = studentCoursePK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentCoursePK != null ? studentCoursePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentCourse)) {
            return false;
        }
        StudentCourse other = (StudentCourse) object;
        if ((this.studentCoursePK == null && other.studentCoursePK != null) || (this.studentCoursePK != null && !this.studentCoursePK.equals(other.studentCoursePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.monash.mymonashmate.server.StudentCourse[ studentCoursePK=" + studentCoursePK + " ]";
    }
    
}
