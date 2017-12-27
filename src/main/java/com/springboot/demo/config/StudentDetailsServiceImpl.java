package com.springboot.demo.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.demo.enums.Role;
import com.springboot.demo.model.Student;
import com.springboot.demo.repositry.StudentRepositry;

@Service("StudentDetailsService")
public class StudentDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private StudentRepositry studentRepositry;


	@Transactional(readOnly=true)
	@Override
	public UserDetails loadUserByUsername(final String username)
		throws UsernameNotFoundException {

		Student student=studentRepositry.findByEmail(username);
		
		if(student == null)
			throw new UsernameNotFoundException("You are not registered");
		
		List<GrantedAuthority> authorities = buildUserAuthority(student.getRole());

		return buildUserForAuthentication(student, authorities);
	}

	
	private User buildUserForAuthentication(Student student, List<GrantedAuthority> authorities) {

		return new org.springframework.security.core.userdetails.User(student.getEmail(), student.getPassword(),
				true, true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Role role) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		setAuths.add(new SimpleGrantedAuthority(role.toString()));

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}

}