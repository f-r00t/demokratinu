package org.group13.pocketpolitics.net.server;

import org.group13.pocketpolitics.model.user.Account;

public class Syncer {

	private static Syncer INSTANCE;
	
	public static void register(ServerInterface activity, Account newUser){
		checkInstance();
		PostAsyncTask task = new PostAsyncTask(activity, newUser, ServerOperation.Register);
		task.execute();
	}
	
	public static void authenticate(ServerInterface activity, Account submitted){
		checkInstance();
		PostAsyncTask task = new PostAsyncTask(activity, submitted, ServerOperation.Authenticate);
		task.execute();
	}
	
	
	////////////////////////////////////////////
	private Syncer(){
		
	}
	
	/**
	 * Unessecary? Doesn't need to be singleton, only static...
	 */
	private static void checkInstance(){
		if(INSTANCE==null){
			INSTANCE=new Syncer();
		}
	}
}
