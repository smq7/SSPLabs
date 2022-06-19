package sspLab2;

import java.util.Objects;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import  java.io.*;
public class Main {
	public static void main(String[] args) throws IOException {
		
		File secretFolder = new File("E:\\Study2.0\\SSP\\fileSystem\\secretFolder");
		createSecretFolder(secretFolder);
	
		
			
		while(true) {
			AbstractUser user = entry();
			AccessManager manager = new AccessManager(user ,secretFolder);
        	FileSystem fileSystem = new FileSystem(manager,user);
        	
        	fileSystem.start();
		}
	}
	
	public static String writeStr() {
		Scanner in = new Scanner(System.in);
		String str = in.nextLine();
		return str;
}
	
	
	public static void createSecretFolder(File secretFolder) throws IOException {
		File registerBook = new File(secretFolder.getAbsoluteFile() + "//" + "registerBook.txt");
		if(!secretFolder.exists()) {
			secretFolder.mkdir();
		}
		if(!registerBook.exists()) {
			registerBook.createNewFile();
		}
	}
	
	public static boolean cheakPassForLogin(String login, String password) throws IOException {
		BufferedReader fin = new BufferedReader(new FileReader(new File("E:\\Study2.0\\SSP\\fileSystem\\secretFolder\\registerBook.txt")));
		String line;
		String logins = "";
		String passwords = "";
		while (((line = fin.readLine()) != null)) {
			
			String[] words = line.split(" ");
			if(words.length>1) {
				logins += words[0] + " ";
				passwords += words[1] + " ";
			}
		};
		fin.close();
		String[] wordlog = logins.split(" ");
		String[] wordpass = passwords.split(" ");
		
		for(int i = 0; i < wordlog.length; i++) {
			if((Objects.equals(login, "admin") && Objects.equals(password, "admin")) || (Objects.equals(login, wordlog[i]) && Objects.equals(password, wordpass[i]))) {
				return false;
			}
		}
		return true;
	}
		
	public static AbstractUser entry() throws IOException {
		boolean flag = true;
		AbstractUser user = null;
		do {
			System.out.print("Input a login: ");
	        String userName = writeStr();
	        while(FileSystem.cheakLogin(userName)) {
	        	System.out.println("System don't have user " + userName);
	        	System.out.print("Input a login: ");
	        	userName = writeStr();
	        }
	        
	        System.out.print("Input a password: ");
	        String password = writeStr();
	        while(cheakPassForLogin(userName,password)) {
	        	System.out.println("Wrong password " );
	        	System.out.print("Input a password: ");
	        	userName = writeStr();
	        }
	        
	        if(Objects.equals(userName, "admin")) {
	        	if(Objects.equals(password, "admin")) {
	        		user= new Admin(userName,password);
	        		flag = false;
	        		
	        	} else {
	        		System.out.println("not correct passwrod for admin");
	        	}
	        }else {
	        	user = new User(userName,password);
	        	flag = false;
	        }
	        
		} while(flag);
		return user;
	}
}


