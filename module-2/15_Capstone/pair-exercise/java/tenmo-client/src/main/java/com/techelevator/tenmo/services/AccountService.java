package com.techelevator.tenmo.services;

import java.math.BigDecimal;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.AuthenticatedUser;

public class AccountService {
    private String BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;

    public AccountService(String url) {
        this.BASE_URL = url;
    }
    public BigDecimal getAccountBalanceRequest(int id) {
    	
    	BigDecimal balance = new BigDecimal(0);
    	
    	try {
    	
    		balance = restTemplate.exchange(BASE_URL + "accounts/" + id + "/balance", HttpMethod.GET, makeAuthEntity(), BigDecimal.class).getBody();
    	
    	} catch(RestClientResponseException ex) {
    	
        }
    	return balance;
    	
//    	ResponseEntity responseEntity = restTemplate.getForEntity(BASE_URL + "accounts/" + id + "/balance", BigDecimal.class);
//    	    	
//    	return responseEntity;
    }
    
    /**
	 * Returns an {HttpEntity} with the `Authorization: Bearer:` header
	 *
	 * @return {HttpEntity}
	 */
	private HttpEntity makeAuthEntity()
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(currentUser.getToken());
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}

    

}
