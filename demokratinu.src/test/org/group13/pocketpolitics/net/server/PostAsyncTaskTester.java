package org.group13.pocketpolitics.net.server;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import org.group13.pocketpolitics.model.user.Account;
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
	}
	
	public void testGson(){
		//Log.w(this.getClass().getSimpleName(), "PocketDebug: gson: "+ PostAsyncTask.testGson());
	}
	
	public void testRegister(){
		testsOnThisObject++;
		
		email = generate(5)+"@chalmers.se";
		uname = generate(8);
		passwd = generate(12);
		
		Account.set(email, uname, passwd);
		Syncer.register(this);
		
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
	public void messageReturned(List<String> msg) {
		this.finished=true;
		Log.w(this.getClass().getSimpleName(), "PocketDebug: Message returned: ");
		ListIterator<String> iter = msg.listIterator();
		while(iter.hasNext()){
			Log.i(this.getClass().getSimpleName(), "PocketDebug: "+iter.next());
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
	
	private String generate(int W){
		String chars = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm0123456789";
		Random r = new Random();
		String gen = "";
		for(int jx = 0; jx<W; jx++){
			gen+=chars.charAt(r.nextInt(chars.length()));
		}
		return gen;
	}

}
