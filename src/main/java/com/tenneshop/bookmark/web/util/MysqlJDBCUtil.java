package com.tenneshop.bookmark.web.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		Connection c = getMysqlDatabaseConnection();
		if (c != null) {
			try {
				stmt = c.prepareStatement("insert into user value (?, ?, ?);");
				stmt.setString(1, username);
				stmt.setString(2, password);
				stmt.setString(3, email);
				
				stmt.executeUpdate();
				
				return true;	
			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());
			}
			finally {
			    // it is a good idea to release
			    // resources in a finally{} block
			    // in reverse-order of their creation
			    // if they are no-longer needed

			    if (rs != null) {
			        try {
			            rs.close();
			        } catch (SQLException sqlEx) { } // ignore

			        rs = null;
			    }

			    if (stmt != null) {
			        try {
			            stmt.close();
			        } catch (SQLException sqlEx) { } // ignore

			        stmt = null;
			    }
			}
		}
		return false;
	}
	
	public List<String> getUserUrls(String user)
	{
		List<String> urls = new ArrayList<String>();
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		//extract from the database all the URLs this user has stored
		Connection c = getMysqlDatabaseConnection();
		if (c != null) {
			try {
				stmt = c.prepareStatement("select bm_URL from bookmark where username = ?;");
				stmt.setString(1, user);
				
				rs = stmt.executeQuery();
				while (rs.next()) {
					String url = rs.getString("bm_URL");
					urls.add(url);
				}
			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());
			}
			finally {
			    // it is a good idea to release
			    // resources in a finally{} block
			    // in reverse-order of their creation
			    // if they are no-longer needed

			    if (rs != null) {
			        try {
			            rs.close();
			        } catch (SQLException sqlEx) { } // ignore

			        rs = null;
			    }

			    if (stmt != null) {
			        try {
			            stmt.close();
			        } catch (SQLException sqlEx) { } // ignore

			        stmt = null;
			    }
			}
		}
		
		return urls;
	}
	
	public boolean login(String username, String password)
	{
		// check if username is unique
		Statement stmt = null;
		ResultSet rs = null;
		
		Connection c = getMysqlDatabaseConnection();
		if (c != null) {
			String sql = "select COUNT(*) AS rowcount from user where username = \"" + username + "\" and passwd = \"" + password + "\";";
			try {
				stmt = c.createStatement();
				rs = stmt.executeQuery(sql);
				rs.next();
				int count = rs.getInt("rowcount");
				
				if (count > 0) {
					return true;
				}
			}
			catch (SQLException ex){
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());
			}
			finally {
			    // it is a good idea to release
			    // resources in a finally{} block
			    // in reverse-order of their creation
			    // if they are no-longer needed

			    if (rs != null) {
			        try {
			            rs.close();
			        } catch (SQLException sqlEx) { } // ignore

			        rs = null;
			    }

			    if (stmt != null) {
			        try {
			            stmt.close();
			        } catch (SQLException sqlEx) { } // ignore

			        stmt = null;
			    }
			}
		}
		
		return false;
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
