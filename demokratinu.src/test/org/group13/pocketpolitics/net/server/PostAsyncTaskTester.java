package org.group13.pocketpolitics.net.server;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.group13.pocketpolitics.model.user.Account;
import org.group13.pocketpolitics.model.user.ArticleData;
import org.group13.pocketpolitics.model.user.Comment;
import org.group13.pocketpolitics.model.user.UserOpinion;

import android.test.AndroidTestCase;
import android.util.Log;

import com.google.gson.Gson;

public class PostAsyncTaskTester extends AndroidTestCase implements ServerInterface{

	private final int sleep = 200;
	private static final String ISSUE = "H001UbU5_Debug";
	
	private boolean finished;
	
	private int testsOnThisObject;
	
	public PostAsyncTaskTester(){
		testsOnThisObject=0;
		
	}
	
	//////////////////////////////////////////////////////////
	
	public void testGetOpinions(){
		testsOnThisObject++;
		Account.set("debug@chalmers.se", "debug", "debug");
		
		Syncer.getOpinions(this, ISSUE);
	}
	
	public void atestArticleDataGson(){
		ArticleData dd = new ArticleData(null);
		dd.setCpmap(new HashMap<String, UserOpinion>());
		
		dd.getCpmap().put(ISSUE, new UserOpinion(1, 100, 87));
		dd.getCpmap().put(ISSUE+"/1", new UserOpinion(1, 122, 56));
		
		Gson g = new Gson();
		
		Log.w(this.getClass().getSimpleName(), "PocketDebug: AData map json: "+g.toJson(dd));
	}
	
	public void atestGetArticleData(){
		testsOnThisObject++;
		Account.set("debug@chalmers.se", "debug", "debug");
		
		Syncer.getArticleData(this, ISSUE);
		
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
		this.finished = true;
		if(succeded){
			Log.w(this.getClass().getSimpleName(), "PocketDebug: authenticate succeded!");
		} else {
			Log.e(this.getClass().getSimpleName(), "PocketDebug: authenticate failed!");
			fail();
		}
		
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
		if(!data.getCpmap().containsKey(ISSUE)){
			Log.e(this.getClass().getSimpleName(), "PocketDebug: opinion for "+ISSUE+" was not found!");
			fail();
		}
		Log.w(this.getClass().getSimpleName(), "PocketDebug: opinion for "+ISSUE+": "+data.getCpmap().get(ISSUE));
		printComments(data.getReplies());
	}

	@Override
	public void operationFailed(String oper) {
		Log.e(this.getClass().getSimpleName(), "PocketDebug: Operation failed! " +oper);
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

	@Override
	public void getOpinionsReturned(boolean succeded, int myOpinion,
			int totalLike, int totalDislike) {
		Log.w(this.getClass().getSimpleName(), "PocketDebug: getOptions returned.");
		Log.i(this.getClass().getSimpleName(), "PocketDebug: myOpinion: "+myOpinion);
		Log.i(this.getClass().getSimpleName(), "PocketDebug: mytotalLike: "+totalLike);
		Log.i(this.getClass().getSimpleName(), "PocketDebug: totalDislike: "+totalDislike);
	}
}
