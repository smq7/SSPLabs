package sspLab2;

public class Admin extends AbstractUser {

	public Admin(String name, String password) {
		this.name = name;
		this.password = password;
	}
	public boolean isAdmin() {
		return true;
	}
	public String getLogin() {
		return name;
	}
}
