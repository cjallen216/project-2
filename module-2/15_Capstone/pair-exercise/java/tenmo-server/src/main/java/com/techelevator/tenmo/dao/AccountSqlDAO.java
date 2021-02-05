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
	
	public AccountSqlDAO(JdbcTemplate jdbcTemplate) 
	{
		this.jdbcTemplate = jdbcTemplate;
	}

	public User getUser() {
		return user;
	}

	public double getBalance() {
		return balance;
	}
	
    public double getAccountBalance(int id) {
    	return jdbcTemplate.queryForObject("SELECT balance\r\n" + 
								    			"FROM accounts\r\n" + 
								    			"WHERE user_id = ?;", double.class, id);
    }
	
}
