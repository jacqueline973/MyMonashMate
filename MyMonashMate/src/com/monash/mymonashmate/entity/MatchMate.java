package com.monash.mymonashmate.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MatchMate implements Serializable {
	
	private Student student;
	private List<StudentSetting> studentSettings = new ArrayList<StudentSetting>();
    private List<Course> courses = new ArrayList<Course>();
    private List<Unit> units = new ArrayList<Unit>();

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }
    
	public String niceFormatUnitCodes() {
		String unitString = "";
		for (Unit unit : units) {
			unitString = unitString + unit.getUnitCode() + " ";
		}
		return unitString;
	}
	public String niceFormatCourseCodes() {
		String courseString = "";
		for (Course course : courses) {
			courseString = courseString + course.getCourseCode() + " ";
		}
		return courseString;
	}
	public List<StudentSetting> getStudentSettings() {
		return studentSettings;
	}

	public void setStudentSettings(List<StudentSetting> studentSettings) {
		this.studentSettings = studentSettings;
	}

	@Override
	public String toString() {
		return "MatchMate [student=" + student + ", courses=" + courses
				+ ", units=" + units + "]";
	}
	
}
