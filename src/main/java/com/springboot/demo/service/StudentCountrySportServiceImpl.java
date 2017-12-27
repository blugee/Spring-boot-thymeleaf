package com.springboot.demo.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.demo.model.CountrySportRelation;
import com.springboot.demo.model.Student;
import com.springboot.demo.model.StudentCountrySportRelation;
import com.springboot.demo.repositry.CountrySportRelationRepositry;
import com.springboot.demo.repositry.StudentCountrySportRelationRepositry;

@Service
public class StudentCountrySportServiceImpl implements StudentCountrySportService{

	@Autowired
	private StudentCountrySportRelationRepositry studentCountrySportRelationRepositry;
	
	@Autowired
	private CountrySportRelationRepositry countrySportRelationRepositry;
	
	StudentCountrySportRelation studentCountrySportRelation;
	
	@Override
	public void saveStudentSport(Set<Integer> countrySportRelationIds, Student student) {
		
		List<CountrySportRelation> countrySportRelations = (List<CountrySportRelation>) countrySportRelationRepositry.findAll(countrySportRelationIds);
		
		for (CountrySportRelation countrySportRelation : countrySportRelations) {
			studentCountrySportRelation=new StudentCountrySportRelation();
			studentCountrySportRelation.setCountrySportRelation(countrySportRelation);
			studentCountrySportRelation.setStudent(student);
			studentCountrySportRelationRepositry.save(studentCountrySportRelation);
		}
	}
	
	@Override
	public List<StudentCountrySportRelation> studentSport(Student student) {
	
		return studentCountrySportRelationRepositry.findByStudent(student);
	}
	
	
	@Override
	public void deleteStudentSport(Student student) {
		studentCountrySportRelationRepositry.deleteByStudent(student);
	}
}
