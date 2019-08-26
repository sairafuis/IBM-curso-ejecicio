package com.microservice.training.countriesdemo.repository;

import com.microservice.training.countriesdemo.model.entity.CountryDocument;
import com.microservice.training.countriesdemo.repository.api.CountriesMongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public abstract class CountriesRepository implements CountriesMongoRepository {

	CountriesMongoRepository servicio;
  public List<CountryDocument> findCountriesByContinent(String continentName){
    return servicio.findCountriesByContinent(continentName);
  }

  public Optional<CountryDocument> findByName(String name){
	    return servicio.findByName(name);
	  }
  
}
