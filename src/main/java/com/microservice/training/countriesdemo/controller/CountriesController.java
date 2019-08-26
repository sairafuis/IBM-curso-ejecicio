package com.microservice.training.countriesdemo.controller;

import com.microservice.training.countriesdemo.model.entity.CountryDocument;
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

@RestController
public class CountriesController {

  @Autowired
  ICountriesService countriesService;

  @GetMapping(path = "/api/countries/continent/name/{continentName}")
  public ResponseEntity<List<CountryDocument>> findCountriesByContinentName(@PathVariable String continentName) {
    return new ResponseEntity<List<CountryDocument>>(
        countriesService.findCountriesByContinentName(continentName), HttpStatus.OK);
  }
  
  @GetMapping(path = "/api/countries/continent/id/{countryId}")
  public ResponseEntity<Optional<CountryDocument>> findCountryById(@PathVariable String countryId) {
    return new ResponseEntity<Optional<CountryDocument>>(
        countriesService.findCountryById(countryId), HttpStatus.OK);
  }

  @PostMapping("/api/countries/continent/id")
	public ResponseEntity<Object> createCountry(@RequestBody CountryDocument country) {
	  CountryDocument savedcountry = countriesService.createCountry(country);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedcountry.getId()).toUri();
		System.out.print(location);
		return ResponseEntity.created(location).build();
	}
  
}
