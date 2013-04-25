package com.server.pocketpolitics.sql;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class LocalConnect{

	private static final String URL = "jdbc:mysql://localhost/pocketpolitics_test";
	private static final String path = "../sensitive/sensitive_sql.txt";
	
	public static void runTest() throws ClassNotFoundException, SQLException {
		String query = "SELECT * FROM votes";
		Connection con = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			
			System.out.println("hej");
			String[] sens = readFromFile(path);
			//con = DriverManager.getConnection(URL, read("Username: "), read("Password: "));
			con = DriverManager.getConnection(URL, sens[0], sens[1]);
			
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
	
	private static String readFromConsole(String ask){
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
	
	private static String[] readFromFile(String path){
		try {
			String[] str = new String[2];
			
			FileInputStream fs = new FileInputStream(path);
			BufferedReader bfr = new BufferedReader(new InputStreamReader(fs, "UTF-8"));
			str[0] = bfr.readLine();
			str[1] = bfr.readLine();
			return str;
			
		} catch (FileNotFoundException e) {
			System.out.println("in LocalConnector: Error: File not found Exception");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			System.out.println("in LocalConnector: Error: unsupported encoding Exception");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("in LocalConnector: Error: IOException");
			e.printStackTrace();
		}
		
		return null;
	}

	
}
