package com.springboot.demo.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springboot.demo.enums.Gender;
import com.springboot.demo.enums.Role;

@Entity
@Table(name = "student")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Size(min = 3, max = 20, message = "Please Enter Atleast 3 character")
	@NotNull(message = "Please Enter First Name")
	@Column
	private String firstName;

	@Size(min = 3, max = 20, message = "Please Enter Atleast 3 character")
	@NotNull(message = "Please Enter Last Name")
	@Column
	private String lastName;

	@DateTimeFormat(pattern = "MM/dd/yyyy")
	@NotNull
	@Past
	@Column
	private Date birthDate;

	@NotNull
	@Column
	private Gender gender;

	@NotEmpty
	@Email(message = "Please Enter the valid Email")
	@Column(unique=true)
	private String email;
	
	@NotEmpty(message = "Please Enter Password")
	@Column
	private String password;
	
	@Column
	private Role role;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "student")
	Set<Image> images = new HashSet<Image>();
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "student")
	Set<StudentCountrySportRelation> studentCountrySportRelations = new HashSet<StudentCountrySportRelation>();
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Image> getImages() {
		return images;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}

	public Set<StudentCountrySportRelation> getStudentCountrySportRelations() {
		return studentCountrySportRelations;
	}

	public void setStudentCountrySportRelations(
			Set<StudentCountrySportRelation> studentCountrySportRelations) {
		this.studentCountrySportRelations = studentCountrySportRelations;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
	
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	
}