package com.server.pocketpolitics.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class LocalConnect{

	private static final String URL = "jdbc:mysql://localhost/pocketpolitics_test";
	private static final String uname = "xxx";
	private static final String pwd = "###";
	
	public static ResultSet run(String query) throws ClassNotFoundException, SQLException {
		Connection con = null;
		try{
			Class.forName("com.mysql.jdb.Driver");
			con = DriverManager.getConnection(URL, uname, pwd);
			
			Statement statem = con.createStatement();
			
			
			ResultSet res = statem.executeQuery(query);
			
			return res;
		}
		finally {
			if (con!=null && !con.isClosed()){
				System.out.println("Closing connection to SQL");
				con.close();
			}
			
		}
		
	}

	
}
