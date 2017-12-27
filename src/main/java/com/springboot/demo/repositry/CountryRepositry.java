package com.springboot.demo.repositry;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springboot.demo.model.Country;

@Transactional
@Repository
public interface CountryRepositry extends CrudRepository<Country, Integer>{

}
