package com.monash.mymonashmate.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
	
	public String niceFormatUnitCodes() {
		String units = "";
		for (String unitCode: selectedUnitCodes) {
			units = units + unitCode + " ";
		}
		return units;
	}
	public String newLineEachUnit() {
		String units = "";
		for (String unitCode: selectedUnitCodes) {
			units = units + unitCode + "\n";
		}
		return units;
	}
	
	public String niceFormatCourseCodes() {
		String course = "";
		for (String courseCode: selectedCourseCodes) {
			course = course + courseCode + " ";
		}
		return course;
	}
	public String newLineEachCourse() {
		String course = "";
		for (String courseCode: selectedCourseCodes) {
			course = course + courseCode + "\n";
		}
		return course;
	}
	
	public List<StudentSetting> getStudentSettings() {
		return studentSettings;
	}
	public void setStudentSettings(List<StudentSetting> studentSettings) {
		this.studentSettings = studentSettings;
	}
	
	@Override
	public String toString() {
		return "Profile [userId=" + userId + ", student=" + student
				+ ", selectedCourseCodes=" + selectedCourseCodes
				+ ", selectedUnitCodes=" + selectedUnitCodes
				+ ", studentSettings=" + studentSettings + "]";
	}
	

}
