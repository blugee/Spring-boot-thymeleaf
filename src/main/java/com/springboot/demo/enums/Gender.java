package com.springboot.demo.enums;

public enum Gender {
	
	MALE("Male"), FEMALE("Female");
	
	public String label;
	
	Gender(String label) {
		this.label = label;
	}
	
	public String getValue(){
		return label;
	}
}