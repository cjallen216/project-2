package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.User;

public interface TransferDAO
{
	List<User> getUsers();
	
	void createTransfer(int accountFrom, int accountTo, double amount);
}
