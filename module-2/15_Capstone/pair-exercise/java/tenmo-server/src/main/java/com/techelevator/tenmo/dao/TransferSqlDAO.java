package com.techelevator.tenmo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.TransferS;
import com.techelevator.tenmo.model.User;

@Component
public class TransferSqlDAO implements TransferDAO
{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private AccountDAO accountDAO;
	@Autowired
	private UserDAO userDAO;
	
	
	public TransferSqlDAO(JdbcTemplate jdbcTemplate) 
	{
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
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
	
	@Override
	public void createTransfer(int accountFrom, int accountTo, double amount)
	{
		TransferS transfers = new TransferS();
				
		String sql = "INSERT INTO transfers\r\n" + 
							"(\r\n" + 
							"        transfer_type_id\r\n" + 
							"        , transfer_status_id\r\n" + 
							"        , account_from\r\n" + 
							"        , account_to\r\n" + 
							"        , amount\r\n" + 
							")\r\n" + 
							"VALUES\r\n" + 
							"(\r\n" + 
							"        2\r\n" + 
							"        , 2\r\n" + 
							"        , ?\r\n" + 
							"        , ?\r\n" + 
							"        , ?\r\n" + 
							");";
		
		jdbcTemplate.update(sql, accountFrom, accountTo, amount);
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
	
	private TransferS mapRowToTransfer(SqlRowSet rs) {
        TransferS transfer = new TransferS();
        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setTransferTypeId(rs.getInt("transfer_type_id"));
        transfer.setTransferStatusId(rs.getInt("transfer_status_id"));
        transfer.setAccountFrom(rs.getInt("account_from"));
        transfer.setAccountTo(rs.getInt("account_to"));
        transfer.setAmount(rs.getDouble("amount"));

        return transfer;
    }
	
}
