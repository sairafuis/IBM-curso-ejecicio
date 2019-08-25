package com.microservice.training.countriesdemo.service.api;


import com.microservice.training.countriesdemo.model.entity.CountryEntity;
import java.util.List;
import java.util.Optional;



public interface ICountriesService {

  List<CountryEntity> findCountriesByContinentName(String continentName);
  
  Optional<CountryEntity> findCountriesById(Integer countryId);

  CountryEntity createCountry(CountryEntity country);


  
}
