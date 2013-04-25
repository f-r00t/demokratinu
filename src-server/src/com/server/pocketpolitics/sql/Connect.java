package com.server.pocketpolitics.sql;

import com.mysql.jdbc.Connection;

public class Connect  implements Connectable {

	protected static final String username = "xxx";
	protected static final String password = "###";
	
	
	@Override
	public Connection getConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		// TODO Auto-generated method stub
		return null;
	}

}
