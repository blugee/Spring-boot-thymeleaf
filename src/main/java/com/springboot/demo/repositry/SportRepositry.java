package com.springboot.demo.repositry;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springboot.demo.model.Sport;

@Transactional
@Repository
public interface SportRepositry extends CrudRepository<Sport, Integer>{

}
