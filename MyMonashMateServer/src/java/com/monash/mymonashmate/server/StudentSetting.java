/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.monash.mymonashmate.server;

import java.io.Serializable;
import javax.persistence.Column;
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
@Table(name = "STUDENT_SETTING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StudentSetting.findAll", query = "SELECT s FROM StudentSetting s"),
    @NamedQuery(name = "StudentSetting.findByStudentId", query = "SELECT s FROM StudentSetting s WHERE s.studentSettingPK.studentId = :studentId"),
    @NamedQuery(name = "StudentSetting.findByAttribute", query = "SELECT s FROM StudentSetting s WHERE s.studentSettingPK.attribute = :attribute"),
    @NamedQuery(name = "StudentSetting.findByRestricted", query = "SELECT s FROM StudentSetting s WHERE s.restricted = :restricted"),
    @NamedQuery(name = "StudentSetting.findBySettingPK", query = "SELECT s FROM StudentSetting s WHERE s.studentSettingPK = :studentSettingPK"),
    @NamedQuery(name = "StudentSetting.findByChanged", query = "SELECT s FROM StudentSetting s WHERE s.changed = :changed")})
public class StudentSetting implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StudentSettingPK studentSettingPK;
    @Column(name = "RESTRICTED")
    private boolean restricted;
    @Column(name = "CHANGED")
    private boolean changed;

    public StudentSetting() {
    }

    public StudentSetting(StudentSettingPK studentSettingPK) {
        this.studentSettingPK = studentSettingPK;
    }

    public StudentSetting(int studentId, String attribute) {
        this.studentSettingPK = new StudentSettingPK(studentId, attribute);
    }

    public StudentSettingPK getStudentSettingPK() {
        return studentSettingPK;
    }

    public void setStudentSettingPK(StudentSettingPK studentSettingPK) {
        this.studentSettingPK = studentSettingPK;
    }

    public boolean getRestricted() {
        return restricted;
    }

    public void setRestricted(boolean restricted) {
        this.restricted = restricted;
    }

    public boolean getChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentSettingPK != null ? studentSettingPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentSetting)) {
            return false;
        }
        StudentSetting other = (StudentSetting) object;
        if ((this.studentSettingPK == null && other.studentSettingPK != null) || (this.studentSettingPK != null && !this.studentSettingPK.equals(other.studentSettingPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StudentSetting{" + "studentSettingPK=" + studentSettingPK + ", restricted=" + restricted + ", changed=" + changed + '}' + "\n";
    }
    
}
