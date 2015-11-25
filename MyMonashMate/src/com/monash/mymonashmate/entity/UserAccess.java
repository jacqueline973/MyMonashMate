package com.monash.mymonashmate.entity;

import java.io.Serializable;

public class UserAccess implements Serializable  {
	
	private Integer id;
	private String name;
	private String password;
	private String studentId;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	@Override
	public String toString() {
		return "UserAccess [id=" + id + ", name=" + name + ", password="
				+ password + ", studentId=" + studentId + "]";
	}
	
}
