package com.springboot.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
 
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Autowired
	private StudentDetailsServiceImpl studentDetailServiceImpl;
	
    @Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(studentDetailServiceImpl);
	}
    
    @Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
   
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       
      http.authorizeRequests()
      	.antMatchers("/**","/home","/getSport/**","/getImage/**","/emailFinder/**","/search/**","/dropdownList/**","/registration").permitAll();
//      	.antMatchers("/admin/**").access("hasRole('ADMIN')")
//        .antMatchers("/user/**").access("hasRole('USER')")
//        .and().formLogin().loginPage("/login").successHandler(new MySuccessHandler())
//        //.failureUrl("/login-error")
//        .usernameParameter("email").passwordParameter("password")
//        .and().csrf()
//        .and().exceptionHandling().accessDeniedPage("/accessDenied");
    }
}