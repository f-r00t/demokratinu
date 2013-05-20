package org.group13.pocketpolitics.net.server;

import org.group13.pocketpolitics.model.user.Account;

public class Syncer {

	private static Syncer INSTANCE;
	
	public static void register(Account newUser){
		checkInstance();
		PostAsyncTask task = new PostAsyncTask(newUser, ServerUrl.Register);
		task.execute();
	}
	
	
	////////////////////////////////////////////
	private Syncer(){
		
	}
	
	private static void checkInstance(){
		if(INSTANCE==null){
			INSTANCE=new Syncer();
		}
	}
}
