package com.techelevator.tenmo.services;

import java.math.BigDecimal;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Accounts;
import com.techelevator.tenmo.models.AuthenticatedUser;

public class AccountService {
    private String BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;
    
    
    public AccountService(String url, AuthenticatedUser currentUser)
    {
    	this.BASE_URL = url + "accounts";
    	this.currentUser = currentUser;
    	
    }
//
//    	public Accounts getBalanceById(int id) {
//    	String url = BASE_URL + "/" + id + "balance";
//    	Accounts balance = restTemplate.getForObject(url, Accounts.class);
//
//    	return balance;

//    public AccountService(String url) {
//        this.BASE_URL = url;
//    }
    public BigDecimal getAccountBalanceRequest() {
    	
    	BigDecimal balance = new BigDecimal(0);
    	
    	try {
    	
    		balance = restTemplate.exchange(BASE_URL + "/" + currentUser.getUser().getId() + "/balance", HttpMethod.GET, makeAuthEntity(), BigDecimal.class).getBody();
    		System.out.println("Here is your balance: $" + balance);
    		
    	} catch(RestClientResponseException ex) {
    	
        }
    	return balance;
    	
//    	Accounts account = restTemplate.getForObject(BASE_URL + "accounts/" + id + "/balance", Accounts.class);
//    	
//        return account.getBalance();
 
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
