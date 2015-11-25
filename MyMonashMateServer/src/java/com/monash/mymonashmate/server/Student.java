/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.monash.mymonashmate.server;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author betty
 */
@Entity
@Table(name = "STUDENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s"),
    @NamedQuery(name = "Student.findByStudentId", query = "SELECT s FROM Student s WHERE s.studentId = :studentId"),
    @NamedQuery(name = "Student.findByName", query = "SELECT s FROM Student s WHERE s.name = :name"),
    @NamedQuery(name = "Student.findByNickname", query = "SELECT s FROM Student s WHERE s.nickname = :nickname"),
    @NamedQuery(name = "Student.findByLatitude", query = "SELECT s FROM Student s WHERE s.latitude = :latitude"),
    @NamedQuery(name = "Student.findByLongitude", query = "SELECT s FROM Student s WHERE s.longitude = :longitude"),
    @NamedQuery(name = "Student.findByNativeLanguage", query = "SELECT s FROM Student s WHERE lower(s.nativeLanguage) = lower(:nativeLanguage)"),
    @NamedQuery(name = "Student.findByFavouriteFood", query = "SELECT s FROM Student s WHERE lower(s.favouriteFood) = lower(:favouriteFood)"),
    @NamedQuery(name = "Student.findBySuburb", query = "SELECT s FROM Student s WHERE lower(s.suburb) = lower(:suburb)")})
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "STUDENT_ID")
    private Integer studentId;
    @Size(max = 15)
    @Column(name = "NAME")
    private String name;
    @Size(max = 15)
    @Column(name = "NICKNAME")
    private String nickname;
    @Size(max = 25)
    @Column(name = "LATITUDE")
    private String latitude;
    @Size(max = 25)
    @Column(name = "LONGITUDE")
    private String longitude;
    @Size(max = 15)
    @Column(name = "NATIVE_LANGUAGE")
    private String nativeLanguage;
    @Size(max = 25)
    @Column(name = "FAVOURITE_FOOD")
    private String favouriteFood;
    @Size(max = 15)
    @Column(name = "SUBURB")
    private String suburb;

    public Student() {
    }

    public Student(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getNativeLanguage() {
        return nativeLanguage;
    }

    public void setNativeLanguage(String nativeLanguage) {
        this.nativeLanguage = nativeLanguage;
    }

    public String getFavouriteFood() {
        return favouriteFood;
    }

    public void setFavouriteFood(String favouriteFood) {
        this.favouriteFood = favouriteFood;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentId != null ? studentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.studentId == null && other.studentId != null) || (this.studentId != null && !this.studentId.equals(other.studentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.monash.mymonashmate.server.Student[ studentId=" + studentId + " ]";
    }
    
}
