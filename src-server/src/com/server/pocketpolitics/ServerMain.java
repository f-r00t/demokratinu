package com.server.pocketpolitics;

import java.sql.SQLException;

import com.server.pocketpolitics.sql.LocalConnect;

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
