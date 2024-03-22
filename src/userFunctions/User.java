package userFunctions;

public class User {
	private int user_id;
	private String username;
	private String password;
	private int age;
	
	public User(int id, String name, String pass, int age) {
		this.user_id = id;
		this.username = name;
		this.password = pass;
		this.age = age;
	}
	public User() {};
	
	public int getUser_id() {
		return user_id;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int a) {
		this.age = a;
	}
	
	public String toString() {
		return "UserName: " + this.getUsername() + " Age: " + this.getAge();
	}
}
