package com.techelevator.tenmo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.User;

@Component
public class TransferSqlDAO implements TransferDAO
{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private AccountDAO accountDAO;
	private UserDAO userDAO;
	
	
	public TransferSqlDAO(JdbcTemplate jdbcTemplate) 
	{
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<User> getUsers() 
	{
		List<User> users = new ArrayList<User>();
		String sql = "SELECT user_id\r\n" + 
				"        ,username\r\n" + 
				"FROM users;";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);
		
		while (rows.next()) 
		{
			 User user = mapRowToUser(rows);
	            users.add(user);
		}
		
		return users;
	}
	
	
	
	
	
	
	private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getLong("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setActivated(true);
        user.setAuthorities("ROLE_USER");
        return user;
    }
	
}
