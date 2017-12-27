package com.springboot.demo.repositry;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.springboot.demo.model.Country;
import com.springboot.demo.model.CountrySportRelation;

public interface CountrySportRelationRepositry extends CrudRepository<CountrySportRelation, Integer>{

	public List<CountrySportRelation> findByCountry(Country country);

}
