package userFunctions;
import java.util.ArrayList;
import java.util.Scanner;

import databaseManager.UserDbManager;

public class UserController {
	
	public static Scanner in = new Scanner(System.in);
	public static UserDbManager manager = UserDbManager.getManager();
	
	public void insertUser(User user) { //Create
		manager.insertUser(user);
		System.out.println("User Inserted Successfully");
	}
	public ArrayList<User> getAllUsers(){ //Read
		ArrayList<User> ans = manager.getAllUsers();
		
		System.out.println("These are all available users: ");
		for(User user: ans) {
			System.out.println(user);
		}
		return ans;
	}
	
	public void updateUser(User user) { //update
		manager.updateUser(user);

		System.out.println("User Updated Successfully");
	}
	
	public void deleteUser(User user) { //delete
		manager.deleteUser(user);
		System.out.println("User Deleted Successfully");
	}
	
	public User getUser(String username, String pass) {
		User user = manager.getUser(username, pass);
		
		if(user == null) {
			System.out.println("No User Found");
			return null;
		}
		else {
			System.out.println("User Found! Details: ");
			System.out.println(user);
			return user;
		}
	}
	
	public boolean login(String username, String pass) {
		User temp = manager.getUser(username, pass);
		return temp == null ? false : true;
	}
	public void register() {
		User temp;
		System.out.println("Enter your username: ");
		String tempUserName = in.next();
		System.out.println("Enter your password: ");
		String tempPass = in.next();
		System.out.println("Enter your age: ");
		int tempAge = in.nextInt();
		
		temp = new User(0, tempUserName, tempPass, tempAge);
		manager.insertUser(temp);
		
		System.out.println("Registered Successfully!");
	}
	public User authenticateUser(){
		User temp = null;

		System.out.println("1) Login");
		System.out.println("2) Register");
		System.out.println("3) Exit");
		System.out.println("\n\nMake a choice...");
		
		try {
			int choice = in.nextInt();
			
			if(choice == 1) {
				return attemptLogin();
			}
			else if(choice == 2) {
				register();
				return attemptLogin();
			}
			else if(choice == 3) {
				System.exit(0);
			}
			else {
				throw new WrongInputException("Please Give Proper Input");
			}
		} 
		
		catch (WrongInputException e) {
			e.printStackTrace();
			System.exit(0);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		return temp;
	}
	
	public User attemptLogin() {
		User temp = null;
		
		System.out.println("ENTER LOGIN CREDENTIALS");
		
		boolean flag = false;
		for(int i = 0; i<3; i++) {
			System.out.println("Enter username:");
			String temp1 = in.next();
			System.out.println("Enter password:");
			String temp2 = in.next();
			
			flag = login(temp1, temp2);
			if(flag) {
				temp = manager.getUser(temp1, temp2);
				return temp;
			}
			System.out.println("RETRY");
		}
		return temp;
	}
	
	public void userOptions(User user) throws Exception{
		
		System.out.println("1) Update your profile");
		System.out.println("2) See all users");
		System.out.println("3) Delete your account");
		System.out.println("4) Exit");
		int choice = in.nextInt();
		switch(choice) {
		case 1:
			System.out.println("Note: You cannot change the primary key");
			System.out.println(user);
			System.out.println("Enter the age you want to update: ");
			try{
				user.setAge(in.nextInt());
				manager.updateUser(user);
			}
			catch(Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
			System.out.println("Your new Profile: ");
			System.out.println(user);
			userOptions(user);
			break;
		case 2:
			ArrayList<User> temp = manager.getAllUsers();
			System.out.println("PRINTING ALL USERS: ");
			for(User u : temp) {
				System.out.println(u);
			}
			userOptions(user);
			break;
		
		case 3:
			manager.deleteUser(user);
			System.out.println("Deleted Successfully!");
			System.out.println("Logging you Out Now!");
			startUserOperation();
			break;
			
		case 4:
			System.exit(0);
			break;
			
		default:
			throw new WrongInputException("Please Give a proper input...");
		}
	}
	
	public void shutDownOps() {
		manager.closeConnection();
	}
	
	public void startUserOperation() {
		User temp = authenticateUser();
		if(temp == null) {
			System.out.println("Could Not Proceed with Login. Restart the program and Login with proper credentials");
			System.exit(0);
		}
		System.out.println("WELCOME!");
		try {
			userOptions(temp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}