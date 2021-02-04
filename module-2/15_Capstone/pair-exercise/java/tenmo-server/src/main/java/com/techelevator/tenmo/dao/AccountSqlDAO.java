package com.techelevator.tenmo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.User;

@Component
public class AccountSqlDAO implements AccountDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private User user;
	private double balance;
	
	public AccountSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public User getUser() {
		return user;
	}

	public double getBalance() {
		return balance;
	}
	
    public double getAccountBalance(String username) {
    	return jdbcTemplate.queryForObject("SELECT a.balance\r\n" + 
						    			"FROM accounts AS a\r\n" + 
						    			"INNER JOIN users AS u\r\n" + 
						    			"        ON a.user_id = u.user_id\r\n" + 
						    			"WHERE u.username = ?;", double.class, username);
    }
	
}
