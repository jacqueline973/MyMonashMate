package com.monash.mymonashmate.entity;

import java.io.Serializable;

public class Unit implements Serializable {
	
	private String unitCode;
	private String unitName;
	
	
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	@Override
	public String toString() {
		return unitCode + "\n" + unitName;
	}

}
