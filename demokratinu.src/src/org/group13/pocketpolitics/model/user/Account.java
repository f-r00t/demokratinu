package org.group13.pocketpolitics.model.user;

public class Account {
	
	private static Account INSTANCE;
	
	private String email;
	private String username;
	private String password;
	
	private Account(String email, String username, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
	}
	
	private static void checkInstance(){
		if(INSTANCE==null){
			// TODO load from shared prefs
			INSTANCE=new Account(null, null, null);
		}
	}
	
	public static void set(String email, String username, String password){
		INSTANCE = new Account(email, username, password);
	}
	
	/**
	 * @return The current users email
	 */
	public static String getEmail() {
		checkInstance();
		return INSTANCE.email;
	}
	
	/**
	 * @return The current users username
	 */
	public static String getUsername() {
		checkInstance();
		return INSTANCE.username;
	}
	
	/**
	 * @return The current users password
	 */
	public static String getPassword() {
		checkInstance();
		return INSTANCE.password;
	}
}
