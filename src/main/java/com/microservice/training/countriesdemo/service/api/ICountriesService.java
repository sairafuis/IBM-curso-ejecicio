package com.microservice.training.countriesdemo.service.api;

import com.microservice.training.countriesdemo.model.Country;
import com.microservice.training.countriesdemo.model.entity.CountryDocument;

import java.util.List;
import java.util.Optional;

public interface ICountriesService {

  List<CountryDocument> findCountriesByContinentName(String continentName);
  
  CountryDocument createCountry(CountryDocument country);

  Optional<CountryDocument> findCountryById(String countryId);
  
  
}
