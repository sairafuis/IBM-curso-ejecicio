package com.microservice.training.countriesdemo.repository.api;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.microservice.training.countriesdemo.model.entity.CountryDocument;

@Repository
public interface CountriesMongoRepository extends CrudRepository<CountryDocument, String> {

	Optional<CountryDocument> findByName(String name);
	
}
