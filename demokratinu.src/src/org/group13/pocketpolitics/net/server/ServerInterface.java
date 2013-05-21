package org.group13.pocketpolitics.net.server;

import java.util.List;

public interface ServerInterface {

	public void messageReturned(List<String> msg);
	
	public void registrationReturned(boolean succeded, boolean unameExists, boolean emailExists);
}
