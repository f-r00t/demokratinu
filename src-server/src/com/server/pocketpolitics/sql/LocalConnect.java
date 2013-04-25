package com.server.pocketpolitics.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class LocalConnect{

	private static final String URL = "jdbc:mysql://localhost/";
	private static final String uname = "xxx";
	private static final String pwd = "###";
	
	public void getConnection() throws ClassNotFoundException, SQLException {
		Connection con = null;
		try{
			Class.forName("com.mysql.jdb.Driver");
			con = DriverManager.getConnection(URL, uname, pwd);
			
			//...
		}
		finally {
			if (con!=null){
				con.close();
			}
			
		}
		
	}

	
}
