package org.group13.pocketpolitics.net.server;

import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import org.group13.pocketpolitics.model.user.Account;
import org.group13.pocketpolitics.model.user.ArticleData;
import org.group13.pocketpolitics.net.server.ServerInterface;
import org.group13.pocketpolitics.net.server.Syncer;

import android.test.AndroidTestCase;
import android.util.Log;

public class PostAsyncTaskTester extends AndroidTestCase implements ServerInterface{

	private boolean finished;
	
	private String email;
	private String uname;
	private String passwd;
	
	private int testsOnThisObject;
	
	public PostAsyncTaskTester(){
		testsOnThisObject=0;
		
		email = generate(5)+"@chalmers.se";
		uname = generate(8);
		passwd = generate(12);
		
		Account.set(email, uname, passwd);
	}
	
	//////////////////////////////////////////////////////////
	
	public void testPostOpinion(){
		testsOnThisObject++;
		
		Syncer.postOpinion(this, "H001UbU5_Debug", 1);
		
		waitTillReturn();
	}
	
	public void atestRegister(){
		testsOnThisObject++;
		
		Syncer.register(this);
		
		waitTillReturn();
	}
	
	/////////////////////////////////////////////////////
	
	private void waitTillReturn(){
		this.finished=false;

		try {
			while(!finished && testsOnThisObject<2){
				Log.i(this.getClass().getSimpleName(), "PocketDebug: Thread waited another 500 ms");
				Thread.sleep(500);
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
				Log.w(this.getClass().getSimpleName(), "PocketDebug: username exists: "+uname);
			}
			if(emailExists){
				Log.w(this.getClass().getSimpleName(), "PocketDebug: email exists: "+email);
			}
			if(!(emailExists || unameExists)){
				Log.e(this.getClass().getSimpleName(), "PocketDebug: registration failed without reason!");
				fail();
			}
		}
		
	}
	
	@Override
	public void authenticateReturned(boolean succeded) {
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getArticleDataReturned(boolean succeded, ArticleData data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void operationFailed(ServerOperation oper) {
		Log.e(this.getClass().getSimpleName(), "PocketDebug: Operation failed! " +oper.name());
		fail();
	}
	
	/////////////////////////////////////////////////////

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
