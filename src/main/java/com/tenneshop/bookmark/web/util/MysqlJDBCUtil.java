package com.tenneshop.bookmark.web.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MysqlJDBCUtil {
	private static final MysqlJDBCUtil INSTANCE = new MysqlJDBCUtil();
	private static final String JDBCURLSTRING = "jdbc:mysql://localhost/bookmarks?user=dongmeiliang&password=dongmeiliang";

	private final Properties p = new Properties();
	
	private Connection conn;
	
	public static MysqlJDBCUtil getInstance() 
	{
		return INSTANCE;
	}
	
	private MysqlJDBCUtil() 
	{
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			p.setProperty("autoReconnect", "true");
			
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
	
	public boolean changePassword(String username, String newPassword, String oldPassword)
	{
		if (!login(username, oldPassword)) {
			return false;
		}
		
		return resetPassword(username, newPassword);
	}
	
	public boolean resetPassword(String username, String password)
	{
		PreparedStatement stmt = null;
		
		Connection c = getMysqlDatabaseConnection();
		if (c != null) {
			String sql = "update user set passwd = ? where username = ?;";
			try {
				stmt = c.prepareStatement(sql);
				stmt.setString(1, password);
				stmt.setString(2, username);
				
				int rowAffected = stmt.executeUpdate();
				if (rowAffected != 1) {
					return false;
				}
				
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
	
	public String getEmailOfUser(String username)
	{
		String email = null;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		//extract from the database all the URLs this user has stored
		Connection c = getMysqlDatabaseConnection();
		if (c != null) {
			try {
				stmt = c.prepareStatement("select email from user where username=?;");
				stmt.setString(1, username);
				
				rs = stmt.executeQuery();
				rs.next();
				email = rs.getString("email");
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
		return email;
	}
	
	public boolean addBM(String username, String bookmarkUrlString) {
		// Add new bookmark to the database
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		Connection c = getMysqlDatabaseConnection();
		if (c != null) {
			try {
				stmt = c.prepareStatement("insert into bookmark value (?, ?);");
				stmt.setString(1, username);
				stmt.setString(2, bookmarkUrlString);
				
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
	
	public boolean deleteBMs(String username, String [] urls) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		Connection c = getMysqlDatabaseConnection();
		if (c != null) {
			try {
				stmt = c.prepareStatement("delete from bookmark where username = ? and bm_URL = ?;");
				stmt.setString(1, username);
				
				for (String url : urls) {
					stmt.setString(2, url);
					stmt.executeUpdate();
				}
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
		
		return true;
	}
	
	public List<String> getRecommendUrlsOfUser(String username) {
		List<String> urls = new ArrayList<String>();
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		//extract from the database all the URLs this user has stored
		Connection c = getMysqlDatabaseConnection();
		if (c != null) {
			try {
				stmt = c.prepareStatement("select bm_URL from bookmark where username in (select distinct(b2.username) from bookmark b1, bookmark b2 where b1.username= ? and b1.username != b2.username and b1.bm_URL = b2.bm_URL) and bm_URL not in (select bm_URL from bookmark where username=?) group by bm_URL;");
				stmt.setString(1, username);
				stmt.setString(2, username);

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
	
	private Connection getMysqlDatabaseConnection()
	{
		if (conn != null) {
			return conn;
		}
		
		try {
			// As a key/value pair in the java.util.Properties instance passed to DriverManager.getConnection() or Driver.connect()
			// using the Connector/J connection property 'autoReconnect=true' to avoid this problem.
			
			conn = DriverManager.getConnection(JDBCURLSTRING, p);
			return conn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return conn;
		}
	}
}
