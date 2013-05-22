package org.group13.pocketpolitics.net;

import java.util.Iterator;

import org.group13.pocketpolitics.model.riksdag.Moprosition;
import org.group13.pocketpolitics.model.riksdag.Motion;
import org.group13.pocketpolitics.model.riksdag.Proposer;
import org.group13.pocketpolitics.model.riksdag.Proposition;
import org.group13.pocketpolitics.net.riksdag.ActivityNetInterface;
import org.group13.pocketpolitics.net.riksdag.Retriever;

import android.test.AndroidTestCase;
import android.util.Log;

public class MotionAsyncTaskTester extends AndroidTestCase implements ActivityNetInterface<Moprosition> {

	private final int totalWait = 20;
	
	public void testStarter(){
		atestMotion();
		atestProposition();
		
		int i = 0;
		try {
			
			while(Retriever.threadsRunning()>0 && i<2*totalWait){
				Thread.sleep(500);
				i++;
				Log.i(this.getClass().getSimpleName(), "PocketDebug: Thread slept another 500 ms. Number extra threads: "+Retriever.threadsRunning());
			}
			
			if(i>=totalWait*2){
				Retriever.cancelAllTasks();
				Log.w(this.getClass().getSimpleName(), "PocketDebug: Cancelled after ca "+((double)i)/2+" secs");
			} else {
				Log.w(this.getClass().getSimpleName(), "PocketDebug: Total time to retruieve 1 motion and 1 proposition: ca "+((double)i)/2+" secs");
			}
			
		} catch (InterruptedException e) {
			Log.w(this.getClass().getSimpleName(), "PocketDebug: Interruption occured!");
			e.printStackTrace();
			fail();
		}
	}
	
	private void atestMotion(){
		Retriever.retrieveMoprosition(this, "2012/13", "A1");
	}
	
	private void atestProposition(){
		Retriever.retrieveMoprosition(this, "2012/13", "73");
	}

	@Override
	public void onPreExecute() {
	}

	@Override
	public void onSuccess(Moprosition result) {
		
		if(result==null){
			Log.e(this.getClass().getSimpleName(), "PocketDebug: null result! Not success!");
			fail();
		}

		Log.w(this.getClass().getSimpleName(), "PocketDebug: Thread returned: "+result.getBeteckning());
		Log.w(this.getClass().getSimpleName(), "PocketDebug: Text URL: "+result.getTextURL());
		
		if(result.isMotion()){
			Motion mot = (Motion) result;
			if(mot.getSubtype()!=null){
				Log.i(this.getClass().getSimpleName(), "PocketDebug: subtype "+ mot.getSubtype());
			} else {
				Log.e(this.getClass().getSimpleName(), "PocketDebug: subtype Null!");
			}
			
			if(mot.getIntressenter()==null){
				Log.e(this.getClass().getSimpleName(), "PocketDebug: Intressenter Null!");
				fail();
			} else {
				Iterator<Proposer> it = mot.getIntressenter().iterator();
				while(it.hasNext()){
					Log.i(this.getClass().getSimpleName(), "PocketDebug: Intressent "+ it.next().getName());
				}
			}
			
			String text = mot.getText();
			if(text!=null){
				
				int print = 25;
				String appe = "...";
				if(print>text.length()){
					print = text.length();
					appe="";
				}
				Log.i(this.getClass().getSimpleName(), "PocketDebug: Text: "+text.substring(0, print)+appe);
			} else {
				Log.e(this.getClass().getSimpleName(), "PocketDebug: Text null!");
			}
			
		} else {
			Proposition prop = (Proposition) result;
			if(prop!=null){
				Log.i(this.getClass().getSimpleName(), "PocketDebug: Successful Proposition conversion.");
			}
		}
	}

	@Override
	public void onFailure(String message) {
		Log.e(this.getClass().getSimpleName(), "PocketDebug: Failure: "+message);
		fail();
	}

	@Override
	public void onProgressUpdate(Integer procent) {
		
	}
	
}
