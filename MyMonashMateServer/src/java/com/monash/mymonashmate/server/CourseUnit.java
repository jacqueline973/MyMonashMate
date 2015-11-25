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
@Table(name = "COURSE_UNIT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CourseUnit.findAll", query = "SELECT c FROM CourseUnit c"),
    @NamedQuery(name = "CourseUnit.findByCourseCode", query = "SELECT c FROM CourseUnit c WHERE c.courseUnitPK.courseCode = :courseCode"),
    @NamedQuery(name = "CourseUnit.findByUnitCode", query = "SELECT c FROM CourseUnit c WHERE c.courseUnitPK.unitCode = :unitCode")})
public class CourseUnit implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CourseUnitPK courseUnitPK;

    public CourseUnit() {
    }

    public CourseUnit(CourseUnitPK courseUnitPK) {
        this.courseUnitPK = courseUnitPK;
    }

    public CourseUnit(String courseCode, String unitCode) {
        this.courseUnitPK = new CourseUnitPK(courseCode, unitCode);
    }

    public CourseUnitPK getCourseUnitPK() {
        return courseUnitPK;
    }

    public void setCourseUnitPK(CourseUnitPK courseUnitPK) {
        this.courseUnitPK = courseUnitPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (courseUnitPK != null ? courseUnitPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CourseUnit)) {
            return false;
        }
        CourseUnit other = (CourseUnit) object;
        if ((this.courseUnitPK == null && other.courseUnitPK != null) || (this.courseUnitPK != null && !this.courseUnitPK.equals(other.courseUnitPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.monash.mymonashmate.server.CourseUnit[ courseUnitPK=" + courseUnitPK + " ]";
    }
    
}
