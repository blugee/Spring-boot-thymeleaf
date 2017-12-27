package com.springboot.demo.repositry;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.demo.model.Student;
import com.springboot.demo.model.StudentCountrySportRelation;

@Repository
public interface StudentCountrySportRelationRepositry extends CrudRepository<StudentCountrySportRelation, Integer>{

	public List<StudentCountrySportRelation> findByStudent(Student student);
	
	@Modifying
	@Transactional
	@Query("delete from StudentCountrySportRelation s where s.student=:student")
	public void deleteByStudent(@Param("student") Student student);
}
