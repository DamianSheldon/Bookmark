package com.tenneshop.bookmark.web.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MysqlDriverUtil {
	final private static MysqlDriverUtil INSTANCE = new MysqlDriverUtil();
	final private static String JDBCURLSTRING = "jdbc:mysql://localhost/bookmarks?user=dongmeiliang&password=dongmeiliang";
	
	private Connection conn;
	
	public static MysqlDriverUtil getInstance() 
	{
		return INSTANCE;
	}
	
	private MysqlDriverUtil() 
	{
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean registerUser(String username, String password, String email) 
	{
		Connection c = getMysqlDatabaseConnection();
		if (c != null) {
			try {
				PreparedStatement stmt = c.prepareStatement("insert into user value (?, ?, ?);");
				stmt.setString(1, username);
				stmt.setString(2, password);
				stmt.setString(3, email);
				
				stmt.executeUpdate();
				
				return true;	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;	
			}
		}
		else {
			return false;
		}
	}
	
	private Connection getMysqlDatabaseConnection()
	{
		if (conn != null) {
			return conn;
		}
		
		try {
			conn = DriverManager.getConnection(JDBCURLSTRING);
			return conn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return conn;
		}
	}
}
