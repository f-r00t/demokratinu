package com.server.pocketpolitics.sql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class LocalConnect{

	private static final String URL = "jdbc:mysql://localhost/pocketpolitics_test";
	
	public static void runTest() throws ClassNotFoundException, SQLException {
		String query = "SELECT * FROM votes";
		Connection con = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(URL, read("Username: "), read("Password: "));
			
			Statement statem = con.createStatement();
			
			
			ResultSet r = statem.executeQuery(query);
			
			if(r!=null){
				while(r.next()){
					System.out.println(r.getString("voter")+" "+r.getString("issue")+" "+r.getString("opinion"));
				}
			}
		} 
		finally {
			System.out.println("in LocalConnect: Warning: entered finally");
			if (con!=null && !con.isClosed()){
				System.out.println("Closing connection to SQL");
				con.close();
			}
			
		}
		
	}
	
	private static String read(String ask){
		System.out.print(ask);
		try{
			BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
			String pwd = bfr.readLine();
			return pwd;
		}
		
		catch (IOException e) {
			System.out.println("Error in LocalConnector: IOException");
			e.printStackTrace();
			return "";
		}
	}

	
}
