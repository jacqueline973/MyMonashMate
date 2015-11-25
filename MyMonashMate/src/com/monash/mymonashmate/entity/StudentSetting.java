package com.monash.mymonashmate.entity;

import java.io.Serializable;

public class StudentSetting implements Serializable {
	
	protected StudentSettingPK studentSettingPK;
	
	private boolean restricted;
	
	private boolean changed;

	public StudentSettingPK getStudentSettingPK() {
		return studentSettingPK;
	}

	public void setStudentSettingPK(StudentSettingPK studentSettingPK) {
		this.studentSettingPK = studentSettingPK;
	}

	public boolean isRestricted() {
		return restricted;
	}

	public void setRestricted(boolean restricted) {
		this.restricted = restricted;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	@Override
	public String toString() {
		return "StudentSetting [studentSettingPK=" + studentSettingPK
				+ ", restricted=" + restricted + ", changed=" + changed + "]";
	}
	
	
	
}
