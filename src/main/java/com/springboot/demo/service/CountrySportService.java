package com.springboot.demo.service;

import java.util.List;

import com.springboot.demo.model.CountrySportRelation;

public interface CountrySportService {

	List<CountrySportRelation> getSport(int countryId);
}
