package org.group13.pocketpolitics.net.server;

import java.util.List;
import java.util.Random;

import org.group13.pocketpolitics.model.user.Account;
import org.group13.pocketpolitics.model.user.ArticleData;
import org.group13.pocketpolitics.model.user.Comment;

import android.test.AndroidTestCase;
import android.util.Log;

public class PostAsyncTaskTester extends AndroidTestCase implements ServerInterface{

	private final int sleep = 200;
	
	private boolean finished;
	
	private int testsOnThisObject;
	
	public PostAsyncTaskTester(){
		testsOnThisObject=0;
		
	}
	
	//////////////////////////////////////////////////////////
	
	public void testGetArticleData(){
		testsOnThisObject++;
		Account.set("debug@chalmers.se", "debug", "debug");
		
		Syncer.getArticleData(this, "H001UbU5_Debug");
		
		this.waitTillReturn();
	}
	
	public void atestPostComment(){
		testsOnThisObject++;
		Account.set("debug@chalmers.se", "debug", "debug");
		
		Syncer.postComment(this, "H001UbU5_Debug/150", "Commenting H001UbU5_Debug, writing "+generate(3)+" "+generate(8));
		
		this.waitTillReturn();
	}
	
	public void atestPostOpinion(){
		testsOnThisObject++;
		
		Account.set("debug@chalmers.se", "debug", "debug");
		
		Syncer.postOpinion(this, "H001UbU5_Debug/1/30", 1);
		
		waitTillReturn();
	}
	
	public void atestRegister(){
		testsOnThisObject++;
		
		String email = generate(5)+"@chalmers.se";
		String uname = generate(8);
		String passwd = generate(12);
		
		Account.set(email, uname, passwd);
		//Account.set("debug@chalmers.se", "debug", "debug");
		
		Syncer.register(this);
		
		waitTillReturn();
	}
	
	/////////////////////////////////////////////////////
	
	private void waitTillReturn(){
		this.finished=false;

		try {
			while(!finished && testsOnThisObject<2){
				Log.i(this.getClass().getSimpleName(), "PocketDebug: Thread waited another "+this.sleep+" ms");
				Thread.sleep(this.sleep);
			}

		} catch (InterruptedException e) {
			Log.e(this.getClass().getSimpleName(), "PocketDebug: Interruption error!");
			e.printStackTrace();
		}

		if(this.testsOnThisObject>1){
			Log.e(this.getClass().getSimpleName(), "PocketDebug: several tests runnning on this object simulatneously! "+this.testsOnThisObject+" Test aborted.");
		}
	}

	@Override
	public void registrationReturned(boolean succeded, boolean unameExists,
			boolean emailExists) {
		this.finished=true;
		
		if(succeded){
			Log.w(this.getClass().getSimpleName(), "PocketDebug: registration succeded!");
		} else {
			Log.e(this.getClass().getSimpleName(), "PocketDebug: registration failed!");
			if(unameExists){
				Log.w(this.getClass().getSimpleName(), "PocketDebug: username exists: "+Account.getUsername());
			}
			if(emailExists){
				Log.w(this.getClass().getSimpleName(), "PocketDebug: email exists: "+Account.getEmail());
			}
			if(!(emailExists || unameExists)){
				Log.e(this.getClass().getSimpleName(), "PocketDebug: registration failed without reason!");
				fail();
			}
		}
		
	}
	
	@Override
	public void authenticateReturned(boolean succeded, String username) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void postOpinionReturned(boolean succeded) {
		this.finished = true;
		if(succeded){
			Log.w(this.getClass().getSimpleName(), "PocketDebug: postOpinion succeded!");
		} else {
			Log.e(this.getClass().getSimpleName(), "PocketDebug: postOpinion failed!");
			fail();
		}
	}

	@Override
	public void postCommentReturned(boolean succeded) {
		this.finished = true;
		if(succeded){
			Log.w(this.getClass().getSimpleName(), "PocketDebug: postComment succeded!");
		} else {
			Log.e(this.getClass().getSimpleName(), "PocketDebug: postComment failed!");
			fail();
		}
	}

	@Override
	public void getArticleDataReturned(ArticleData data) {
		this.finished = true;
		printComments(data.getReplies());
	}

	@Override
	public void operationFailed(ServerOperation oper) {
		Log.e(this.getClass().getSimpleName(), "PocketDebug: Operation failed! " +oper.name());
		fail();
	}
	
	/////////////////////////////////////////////////////

	public static void printComments(List<Comment> clist){
		for(Comment c:clist){
			Log.i(PostAsyncTaskTester.class.getSimpleName(), "PocketDebug: "+c.getContent());
			printComments(c.getReplies());
		}
	}
	
	public static String generate(int W){
		String chars = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm0123456789";
		Random r = new Random();
		String gen = "";
		for(int jx = 0; jx<W; jx++){
			gen+=chars.charAt(r.nextInt(chars.length()));
		}
		return gen;
	}
}
