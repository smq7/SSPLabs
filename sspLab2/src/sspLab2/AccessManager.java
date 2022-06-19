package sspLab2;

import java.util.Objects;
import  java.io.*;
public class AccessManager {
	//read create execute delete createUser admin = [0] user [1] =  
	private final int[][] accessMatrix = {{1,1,1,1,1},
							{1,0,0,0,0}};
	private AbstractUser user;
	private File secretFolder;
	
	public AccessManager(AbstractUser userName,File secretFolder) {
		user = userName; 
		this.secretFolder = secretFolder;
	}
	public boolean cheackAccess(int i) {
		if(user.isAdmin()) {
			if(accessMatrix[0][i] != 0) {
				return true;
			} else {
				System.out.print("access denied" +"\n");
				return false;
			}
		} 
		if(!user.isAdmin()) {
			if(accessMatrix[1][i] != 0) {
				return true;
			} else {
				System.out.print("access denied" +"\n");
				return false;
			}
		}
		System.out.print("access denied tut " + "\n");
		return false;
	}
	public boolean cheackSecretFolder(File folder) {
		if(Objects.equals(folder,secretFolder)) {
			if(user.isAdmin()) {
				return true;
			} else {
				System.out.print("access denied" +"\n");
				return false;
			}
		}
		return true;
	}
}
