package com.microservice.training.countriesdemo.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CountryNotFoundException extends RuntimeException {


	public CountryNotFoundException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
}
