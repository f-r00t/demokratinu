package org.group13.pocketpolitics.test.net;

import java.util.List;
import java.util.ListIterator;

import org.group13.pocketpolitics.model.user.Account;
import org.group13.pocketpolitics.net.server.ServerInterface;
import org.group13.pocketpolitics.net.server.Syncer;

import android.test.AndroidTestCase;
import android.util.Log;

public class PostAsyncTaskTester extends AndroidTestCase implements ServerInterface{

	private boolean finished;
	
	public void testRegister(){
		Syncer.register(this, new Account("leif@testpolitics.se", "The HalfLeif untested", "12345"));
		
		this.finished=false;
		
		try {
			while(!finished){
				Log.i(this.getClass().getSimpleName(), "PocketDebug: Thread waited another 500 ms");
				Thread.sleep(500);
			}
			
		} catch (InterruptedException e) {
			Log.e(this.getClass().getSimpleName(), "PocketDebug: Interruption error!");
			e.printStackTrace();
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
}
