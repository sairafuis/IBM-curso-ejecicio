package com.microservice.training.countriesdemo.repository;



import com.microservice.training.countriesdemo.model.entity.CountryEntity;

import com.microservice.training.countriesdemo.repository.api.CountryJpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public abstract class CountriesRepository implements CountryJpaRepository {

	CountryJpaRepository service;
	public List<CountryEntity> findByContinent(String continentName){
    return service.findByContinent(continentName);
  }

}
