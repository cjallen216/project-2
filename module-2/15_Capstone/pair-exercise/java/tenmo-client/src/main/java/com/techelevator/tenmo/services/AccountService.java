package com.techelevator.tenmo.services;

import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.AuthenticatedUser;

public class AccountService {
    private String BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();

    public AccountService(String url) {
        this.BASE_URL = url;
    }
    public double getAccountBalanceRequest(int userId) throws AuthenticationServiceException {
    	try {
    		double balance = 0;
    		balance = restTemplate.getForObject(BASE_URL + "accounts/" + userId + "/balance", double.class);
    		return balance;
    	} catch(RestClientResponseException ex) {
    		return 0;
        }
    }

}
