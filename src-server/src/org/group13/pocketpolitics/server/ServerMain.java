package org.group13.pocketpolitics.server;

import java.sql.SQLException;

import org.group13.pocketpolitics.server.sql.LocalConnect;


public class ServerMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		try {
			LocalConnect.runTest();
			System.out.println("Returning to main");
			
		} catch (ClassNotFoundException e) {
			System.out.println("Error in ServerMain: Class not found");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error in ServerMain: SQL Exception");
			e.printStackTrace();
		}
	}

}
