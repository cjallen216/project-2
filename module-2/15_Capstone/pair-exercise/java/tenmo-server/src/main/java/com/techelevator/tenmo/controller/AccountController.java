package com.techelevator.tenmo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {
	
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/accounts/{id}/balance", method = RequestMethod.GET)
    public double getAccountBalance() {
    	return 0;
    }

}
