package com.techelevator.tenmo.services;

import java.awt.desktop.UserSessionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.TransferC;
import com.techelevator.tenmo.models.User;

public class TransferService
{
	private String BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;
    
    public TransferService(String url, AuthenticatedUser currentUser) 
    {
    	this.BASE_URL = url;
    	this.currentUser = currentUser;
    }
    
    public void sendMoney() 
    {
    	User[] users = null;
    	
    	TransferC transfer = new TransferC();
    	
    	Scanner scanner = new Scanner(System.in);
    	
    	users = restTemplate.exchange(BASE_URL + "/users", HttpMethod.GET, makeAuthEntity(), User[].class).getBody();
    	
    	for (User user : users)
		{
			if(user.getId() == currentUser.getUser().getId()) 
			{
				
			}
		}
    	
    	System.out.println("Hey look at these users! " + users);
    	
    }
    
    
//    public User[] getUsersList() 
//    {
//    	User[] users = null;
//    	
//    	try
//		{
//			users = restTemplate.getForObject(BASE_URL + "/users", User[].class);
//			System.out.println("Which user would you like to send money to? "); 
//			System.out.println("Users: " + users);
//			
//		} 
//    	catch (Exception e)
//		{
//			// TODO: handle exception
//		}
//    	
//    	return users;
//    }
    
    
    
    
	private HttpEntity makeAuthEntity()
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(currentUser.getToken());
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}

}
