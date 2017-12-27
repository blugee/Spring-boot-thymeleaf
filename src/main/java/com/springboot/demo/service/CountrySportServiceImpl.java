package com.springboot.demo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.demo.model.Country;
import com.springboot.demo.model.CountrySportRelation;
import com.springboot.demo.repositry.CountryRepositry;
import com.springboot.demo.repositry.CountrySportRelationRepositry;

@Service
public class CountrySportServiceImpl implements CountrySportService {

	@Autowired
	private CountrySportRelationRepositry countrySportRepositry;
	
	@Autowired
	private CountryRepositry countryRepositry;
	
	@Override
	public List<CountrySportRelation> getSport(int countryId) {
		Country country1=countryRepositry.findOne(countryId);
		return countrySportRepositry.findByCountry(country1);
	}
}
