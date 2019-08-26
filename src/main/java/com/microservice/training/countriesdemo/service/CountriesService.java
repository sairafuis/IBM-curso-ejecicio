package com.microservice.training.countriesdemo.service;

import com.microservice.training.countriesdemo.errorhandling.InvalidContinentException;
import com.microservice.training.countriesdemo.model.Continent;
import com.microservice.training.countriesdemo.model.Country;
import com.microservice.training.countriesdemo.model.entity.CountryDocument;
import com.microservice.training.countriesdemo.repository.api.CountriesMongoRepository;
import com.microservice.training.countriesdemo.repository.api.ICountriesRepository;
import com.microservice.training.countriesdemo.service.api.ICountriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CountriesService implements ICountriesService {

  @Autowired
  CountriesMongoRepository countriesRepository;
  
  public List<CountryDocument> findCountriesByContinentName(String continentName) {
    Continent continent = Continent.continentByName(continentName);
    if(continent.getContinentName().equals(continentName.toLowerCase()))
    {
    return countriesRepository.findCountriesByContinent(continentName);
    }
    throw new InvalidContinentException("Continent: " + continentName + " does not exist.");
  }
  
  public Optional<CountryDocument> findCountryById(String countryId) {
	Optional<CountryDocument> countryOpt =  countriesRepository.findById(countryId);
	if (countryOpt.isPresent()) {
		return countryOpt;
	}
	throw new RuntimeException("Country with ID: " + countryId + " does not exist.");
	}
	

  public CountryDocument createCountry(CountryDocument country) {
	    return countriesRepository.save(country);
	  }
}
