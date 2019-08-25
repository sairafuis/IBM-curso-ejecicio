package com.microservice.training.countriesdemo.controller;


import com.microservice.training.countriesdemo.service.api.ICountriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.microservice.training.countriesdemo.model.entity.CountryEntity;

@RestController
public class CountriesController {

  @Autowired
  ICountriesService countriesService;

  @GetMapping(path = "/api/countries/continent/name/{continentName}")
  public ResponseEntity<List<CountryEntity>> findCountryByContinent(@PathVariable String continentName) {
    return new ResponseEntity<List<CountryEntity>>(
        countriesService.findCountriesByContinentName(continentName), HttpStatus.OK);
  }
  
  @GetMapping(path = "/api/countries/continent/id/{countryId}")
  public ResponseEntity<Optional<CountryEntity>> findCountryById(@PathVariable Integer countryId) {
    return new ResponseEntity<Optional<CountryEntity>>(
        countriesService.findCountriesById(countryId), HttpStatus.OK);
  }
  
  @PostMapping(path = "/api/countries/continent")
  public ResponseEntity<Object> createCountry(@RequestBody CountryEntity country) {
	CountryEntity savedCountry = countriesService.createCountry(country);
	URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/id/{id}").buildAndExpand(savedCountry.getCountryId()).toUri();
	System.out.print(location);
	return ResponseEntity.created(location).build(); 
	
  }

}
