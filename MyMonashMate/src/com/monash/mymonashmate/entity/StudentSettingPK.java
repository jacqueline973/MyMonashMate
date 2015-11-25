package com.monash.mymonashmate.entity;

import java.io.Serializable;

public class StudentSettingPK implements Serializable {
	
	private int studentId;
	private String attribute;
	
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
	public String toString() {
		return "StudentSettingPK [studentId=" + studentId + ", attribute="
				+ attribute + "]";
	}
	
	

}
