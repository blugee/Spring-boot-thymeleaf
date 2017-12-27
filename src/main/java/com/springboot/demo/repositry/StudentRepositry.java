package com.springboot.demo.repositry;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.demo.enums.Gender;
import com.springboot.demo.model.Student;

@Transactional
@Repository
public interface StudentRepositry extends CrudRepository<Student, Integer> {

	public List<Student> findByGender(Gender gender);

	@Query("from Student where firstName LIKE %:firstName% OR lastName LIKE %:lastName% OR email LIKE %:email%")
	public List<Student> findByFirstNameLikeOrLastNameOrEmailLike(@Param("firstName") String firstName,
			@Param("lastName") String lastName,@Param("email") String email);

	public Student findByEmail(String email);
	
}
