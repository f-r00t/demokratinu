package com.server.pocketpolitics;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.server.pocketpolitics.sql.LocalConnect;

public class ServerMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String query = "SELECT * FROM votes";
		
		try {
			ResultSet r =  LocalConnect.run(query);
			if(r!=null){
				while(r.next()){
					System.out.println(r.getString("voter")+" "+r.getString("issue")+" "+r.getString("opinion"));
				}
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("Error in ServerMain: Class not found");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error in ServerMain: SQL Exception");
			e.printStackTrace();
		}
	}

}
