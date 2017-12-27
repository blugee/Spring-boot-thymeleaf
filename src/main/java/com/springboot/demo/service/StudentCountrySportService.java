package com.springboot.demo.service;

import java.util.List;
import java.util.Set;

import com.springboot.demo.model.Student;
import com.springboot.demo.model.StudentCountrySportRelation;

public interface StudentCountrySportService {

	void saveStudentSport(Set<Integer> countrySportRelation,Student student);
	
	List<StudentCountrySportRelation> studentSport(Student student);
	
	void deleteStudentSport(Student student);

}
