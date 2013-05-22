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
	
	public static void postOpinion(ServerInterface activity, String issue, int opinion){
		checkInstance();
		PostAsyncTask task = new PostAsyncTask(activity, ServerOperation.PostOpinion);
		task.execute();
	}
	
	public static void postComment(ServerInterface activity, String parentId, String content){
		checkInstance();
		PostAsyncTask task = new PostAsyncTask(activity, ServerOperation.PostComment);
		task.execute();
	}
	
	public static void getArticleData(ServerInterface activity, String articleId){
		checkInstance();
		PostAsyncTask task = new PostAsyncTask(activity, ServerOperation.GetArticleData);
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
