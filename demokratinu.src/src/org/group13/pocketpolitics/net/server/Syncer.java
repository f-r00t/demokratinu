package org.group13.pocketpolitics.net.server;

public class Syncer {

	private static Syncer INSTANCE;
	
	public static void register(ServerInterface activity){
		checkInstance();
		PostAsyncTask task = new PostAsyncTask(activity, ServerOperation.Register);
		task.execute();
	}
	
	public static void authenticate(ServerInterface activity){
		checkInstance();
		PostAsyncTask task = new PostAsyncTask(activity, ServerOperation.Authenticate);
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
