package com.techelevator.tenmo.services;

import java.awt.desktop.UserSessionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Accounts;
import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.TransferC;
import com.techelevator.tenmo.models.User;

public class TransferService {
	private String BASE_URL;
	private RestTemplate restTemplate = new RestTemplate();
	private AuthenticatedUser currentUser;
	private int selectedId;
	public Scanner scanner = new Scanner(System.in);
	public User[] users;
	public double amountToSend;

	public TransferService(String url, AuthenticatedUser currentUser) {
		this.BASE_URL = url;
		this.currentUser = currentUser;
	}

	public void sendMoney() {
		TransferC transfer = new TransferC();
		Accounts accounts = new Accounts();
		AccountService accountService = new AccountService(BASE_URL, currentUser);

		users = restTemplate.exchange(BASE_URL + "/users", HttpMethod.GET, makeAuthEntity(), User[].class).getBody();
		System.out.println("Here are the users you can send money to! ");

		for (User user : users) {
			if (user.getId() != currentUser.getUser().getId()) {
				System.out.println(user.getUsername() + ", " + user.getId());
			}
		}
		System.out.print("Please select a user id to send money to: ");
		String selectedUser = scanner.nextLine();
		selectedId = Integer.parseInt(selectedUser);
		if (currentUser.getUser().getId() == selectedId) {
			System.out.println("You selected yourself, please try again.");
			System.out.println();
		} else {
			System.out.print("How much would you like to send them? ");
			String inputMoney = scanner.nextLine();
			amountToSend = Double.parseDouble(inputMoney);

			transfer.setAccountFrom(currentUser.getUser().getId());
			transfer.setAccountTo(selectedId);
			
			double balance = accountService.getAccountBalanceRequest();
			accounts.setBalance(balance);
			
			System.out.println("Current Balance is: $" + accounts.getBalance());

			if (accounts.getBalance() - amountToSend > 0) {
				
				createTransfer(currentUser.getUser().getId(), selectedId, amountToSend);
				
				System.out.println("Transfer complete");
				System.out.println();
			} else {
				System.out.println("Sorry you don't have enough funds.");
				System.out.println();
			}
		}
	}
	
	public TransferC createTransfer(int accountFrom, int accountTo, double amount)
	{
		TransferC transfer = new TransferC(accountFrom, accountTo, amount);
		
//		String postString = "Transfers{" + "transfer_type_id=" + 2 + 
//				", transfer_status_id=" + 2 + ", account_from =" + accountFrom + ", account_to=" + accountTo 
//				+ ", amount=" + amount + '}' ;

//		HttpEntity entity = new HttpEntity<>(transfer, headers);

		
		transfer = restTemplate.postForObject(BASE_URL + "/transfers", makeTransferEntity(transfer), TransferC.class);
		
		return transfer;
	}

	private HttpEntity makeAuthEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(currentUser.getToken());
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}
	
	private HttpEntity<TransferC> makeTransferEntity(TransferC transfer) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(currentUser.getToken());
		HttpEntity<TransferC> entity = new HttpEntity<>(transfer, headers);
	    return entity;
	  }
}
