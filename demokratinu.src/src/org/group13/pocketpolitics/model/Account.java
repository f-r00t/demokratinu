package org.group13.pocketpolitics.model;

public class Account {
	
	private String email;
	private String username;
	private String password;
	
	public Account(String email, String username, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * @return The current users email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @param The current users email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return The current users username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * @param username The current users username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * @return The current users password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @param password The current users password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
