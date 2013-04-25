package com.server.pocketpolitics.sql;

import com.mysql.jdbc.Connection;

public interface Connectable {

	public Connection getConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException ;
}
