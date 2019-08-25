package com.microservice.training.countriesdemo.service;

import com.microservice.training.countriesdemo.errorhandling.CountryNotCreatedException;
import com.microservice.training.countriesdemo.errorhandling.CountryNotFoundException;
import com.microservice.training.countriesdemo.errorhandling.InvalidContinentException;
import com.microservice.training.countriesdemo.model.Continent;
import com.microservice.training.countriesdemo.model.entity.CountryEntity;
import com.microservice.training.countriesdemo.repository.api.CountryJpaRepository;
import com.microservice.training.countriesdemo.service.api.ICountriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CountriesService implements ICountriesService {

  @Autowired
  CountryJpaRepository countriesRepository;
  
  public List<CountryEntity> findCountriesByContinentName(String continentName) {
    Continent continent = Continent.continentByName(continentName);
    if (continent.getContinentName().contentEquals(continentName.toLowerCase())){
    	return countriesRepository.findByContinent(continentName);
    }
    throw new InvalidContinentException("Continent: " + continentName + " does not exist.");

  }


  public Optional<CountryEntity> findCountriesById(Integer countryId) {
	  Optional<CountryEntity> countryOpt = countriesRepository.findById(countryId);
	  if (countryOpt.isPresent()) {
		  return countryOpt;
	  }
       throw new CountryNotFoundException("Country id: " + countryId + " does not exist.");

  }

	
	public CountryEntity createCountry(CountryEntity country) {
		CountryEntity retsave = countriesRepository.save(country);
		if(retsave.equals(null)) {
			throw new CountryNotCreatedException("Country not created.");	
		}
		return countriesRepository.save(country);
	}
	  
}
