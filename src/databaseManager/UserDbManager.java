package databaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import userFunctions.User;

public class UserDbManager {
	private static final String url = "jdbc:mysql://localhost:3306/userdbms";
	private static final String user = "root";
	private static final String pass = "Chinpat@2002";
	
	private static Connection conn;
	private static UserDbManager manager;
	
	private UserDbManager() {
		try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pass);
            
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            
        } catch (SQLException e) {
            System.out.println("Connection to MySQL database failed.");
            e.printStackTrace();
        }
	}
	
	public static synchronized UserDbManager getManager() {
		if (manager == null) {
            manager = new UserDbManager();
        }
        return manager;
	}
	
	public void closeConnection() {
		 if(conn != null) {
	        try {
	            conn.close();
	            System.out.println("Database connection closed.");
	            
	        } catch (SQLException e) {
	            System.out.println("Failed to close database connection.");
	            e.printStackTrace();
	        }  
		 }
	}
	
	public void insertUser(User user) { //C
		String INSERT_STATEMENT = "insert into user(username, password, age) values(?, ?, ?)";
		try {
			PreparedStatement ps = conn.prepareStatement(INSERT_STATEMENT);
			
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setInt(3, user.getAge());
			
			ps.executeUpdate();
			ps.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<User> getAllUsers(){ //R
		ArrayList<User> ans = new ArrayList<>();
		String READ_STATEMENT = "select * from user";
		
		try {
			PreparedStatement ps = conn.prepareStatement(READ_STATEMENT);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				User temp = new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"), rs.getInt("age"));
				ans.add(temp);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ans;
	}
	
	public void updateUser(User user) { //U
		String UPDATE_STATEMENT = "update user set username = ?, password = ?, age = ? where user_id = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(UPDATE_STATEMENT);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setInt(3, user.getAge());
			ps.setInt(4, user.getUser_id());
			
			ps.executeUpdate();
			ps.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteUser(User user) { //D
String DELETE_STATEMENT = "delete from user where user_id = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(DELETE_STATEMENT);
			ps.setInt(1, user.getUser_id());
			
			ps.executeUpdate();
			ps.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public User getUser(String username, String pass) {
		String READ_STATEMENT = "select * from user where username = ? AND password = ?";
		User temp = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(READ_STATEMENT);
			ps.setString(1, username);
			ps.setString(2, pass);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				temp = new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"), rs.getInt("age"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return temp;
	}
}
