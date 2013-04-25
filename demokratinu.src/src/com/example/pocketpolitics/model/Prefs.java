package com.example.pocketpolitics.model;

/**
 * The users settings.
 * 
 * This data will be stored on the device together with the corresponding
 * model data. Only saving data for the *current* user
 * 
 * @author Hassel
 *
 */
public class Prefs {
	private boolean isSavingPass;
	private boolean isStayingLoggedIn;
	
	public Prefs(boolean isSavingPass, boolean isStayingLoggedIn) {
		this.isSavingPass = isSavingPass;
		this.isStayingLoggedIn = isStayingLoggedIn;
	}
	
	/**
	 * @param isSavingPass True if the users password should be saved, false otherwise
	 */
	public void isSavingPass(boolean isSavingPass) {
		this.isSavingPass = isSavingPass;
	}
	
	/**
	 * @return True if the users password is going to be saved, false otherwise
	 */
	public boolean isSavingPass() {
		return isSavingPass;
	}
	
	/**
	 * @param isStayingLoggedIn True if the user should be logged in automatically when
	 * the application starts, false otherwise
	 */
	public void isStayingLoggedIn(boolean isStayingLoggedIn) {
		this.isStayingLoggedIn = isStayingLoggedIn;
	}
	
	/**
	 * @return True if the user should get logged in automatically when
	 * the application starts, false otherwise
	 */
	public boolean isStayingLoggedIn() {
		return isStayingLoggedIn;
	}
}
