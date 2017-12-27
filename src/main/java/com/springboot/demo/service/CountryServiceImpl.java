package com.springboot.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.demo.model.Country;
import com.springboot.demo.repositry.CountryRepositry;

@Service
public class CountryServiceImpl implements CountryService{

	@Autowired
	private CountryRepositry countryRepositry;

	@Override
	public List<Country> getCountry() {
		return (List<Country>) countryRepositry.findAll();
	}
}
