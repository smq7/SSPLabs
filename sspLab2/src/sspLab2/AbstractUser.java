package sspLab2;

public abstract class  AbstractUser {
	protected String name;
	protected String password;
	public abstract boolean isAdmin();
	public abstract String getLogin();
}
