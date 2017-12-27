package com.springboot.demo.service;


import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.springboot.demo.enums.Gender;
import com.springboot.demo.model.Student;

public interface StudentService {
	
	Boolean saveStudent(Student student,MultipartFile image, Set<Integer> countrySportRelations);

	List<Student> listAll();

	void deleteStudent(int id);

	Student findById(int id);
	
	String findStudentImageById(int id);
	
	List<Student> listByGender(Gender gender);
	
	List<Student> searchByFirstNameOrLastName(String search);
	
	public String securityCheck();
	
	Boolean emailFinder(String email);
}
