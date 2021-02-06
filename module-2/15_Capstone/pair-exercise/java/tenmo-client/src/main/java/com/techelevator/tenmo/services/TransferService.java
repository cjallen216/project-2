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

public class TransferService {
	private String BASE_URL;
	private RestTemplate restTemplate = new RestTemplate();
	private AuthenticatedUser currentUser;
	private int selectedId;
	public Scanner scanner = new Scanner(System.in);
	public User[] users;
	public double amountToSend;
	private AccountService accountService = new AccountService(BASE_URL, currentUser);

	public TransferService(String url, AuthenticatedUser currentUser) {
		this.BASE_URL = url;
		this.currentUser = currentUser;
	}

	public void sendMoney() {
		TransferC transfer = new TransferC();

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
			System.out.println("Please choose from the list shown.");
		} else {
			System.out.print("How much would you like to send them? ");
			String inputMoney = scanner.nextLine();
			amountToSend = Double.parseDouble(inputMoney);

			transfer.setAccountFrom(currentUser.getUser().getId());
			transfer.setAccountTo(selectedId);

			if (accountService.getAccountBalanceRequest() - amountToSend > 0) {
				System.out.println("Good job you got moneys");
			} else {
				System.out.println("Sorry you don't have enough funds.");
			}
		}
	}

	private HttpEntity makeAuthEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(currentUser.getToken());
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}
}
