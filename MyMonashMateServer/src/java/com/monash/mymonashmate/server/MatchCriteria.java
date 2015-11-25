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
public class MatchCriteria implements Serializable {

    private String unitCodes;
    private String courseCodes;
    private String nativeLanguage;
    private String favouriteFood;
    private String suburb;
    private String currentUserStudentId;

    public String getUnitCodes() {
        return unitCodes;
    }

    public void setUnitCodes(String unitCodes) {
        this.unitCodes = unitCodes;
    }

    public String getCourseCodes() {
        return courseCodes;
    }

    public void setCourseCodes(String courseCodes) {
        this.courseCodes = courseCodes;
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

    public String getCurrentUserStudentId() {
        return currentUserStudentId;
    }

    public void setCurrentUserStudentId(String currentUserStudentId) {
        this.currentUserStudentId = currentUserStudentId;
    }

    @Override
    public String toString() {
        return "MatchCriteria{" + "unitCodes=" + unitCodes + ", courseCodes=" + courseCodes + ", nativeLanguage=" + nativeLanguage + ", favouriteFood=" + favouriteFood + ", suburb=" + suburb + '}';
    }

}
