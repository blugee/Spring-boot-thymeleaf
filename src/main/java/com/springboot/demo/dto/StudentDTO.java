package com.springboot.demo.dto;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.springboot.demo.model.Student;

public class StudentDTO {

	private Student student;
	
	private MultipartFile studentImagePath;

	private Set<Integer> countrySportRelation=new HashSet<Integer>();
	
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public MultipartFile getStudentImagePath() {
		return studentImagePath;
	}

	public void setStudentImagePath(MultipartFile studentImagePath) {
		this.studentImagePath = studentImagePath;
	}

	public Set<Integer> getCountrySportRelation() {
		return countrySportRelation;
	}

	public void setCountrySportRelation(Set<Integer> countrySportRelation) {
		this.countrySportRelation = countrySportRelation;
	}


}
