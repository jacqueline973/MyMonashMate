package com.monash.mymonashmate.entity;

import java.io.Serializable;

public class Student implements Serializable {
	
	private Integer studentId;
	private String name;
	private String nickname;
	private String latitude;
	private String longitude;
	private String nativeLanguage;
	private String favouriteFood;
	private String suburb;
	
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
	public String toString() {
		return "Student [studentId=" + studentId + ", name=" + name
				+ ", nickname=" + nickname + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", nativeLanguage="
				+ nativeLanguage + ", favouriteFood=" + favouriteFood
				+ ", suburb=" + suburb + "]";
	}
	
	

}
