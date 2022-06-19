package sspLab2;

public class User extends AbstractUser {
	
	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}
	public boolean isAdmin() {
		return false;
	}
	public String getLogin() {
		return name;
	}
}
