package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

public interface AccountDAO {
	
    User getUser();
    
//    double getAccountBalance(String username);
    
    double getAccountBalance(int id);
    
}
