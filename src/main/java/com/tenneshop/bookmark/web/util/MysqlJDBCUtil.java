package com.tenneshop.bookmark.web.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlJDBCUtil {
	final private static MysqlJDBCUtil INSTANCE = new MysqlJDBCUtil();
	final private static String JDBCURLSTRING = "jdbc:mysql://localhost/bookmarks?user=dongmeiliang&password=dongmeiliang";
	
	private Connection conn;
	
	public static MysqlJDBCUtil getInstance() 
	{
		return INSTANCE;
	}
	
	private MysqlJDBCUtil() 
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
	
	public List<String> getUserUrls(String user)
	{
		List<String> urls = new ArrayList<String>();
		
		//extract from the database all the URLs this user has stored
		Connection c = getMysqlDatabaseConnection();
		if (c != null) {
			PreparedStatement stmt;
			try {
				stmt = c.prepareStatement("select bm_URL from bookmark where username = ?;");
				stmt.setString(1, user);
				
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					String url = rs.getString("bm_URL");
					urls.add(url);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return urls;
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
