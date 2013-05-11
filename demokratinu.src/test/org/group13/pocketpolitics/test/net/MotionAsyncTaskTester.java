package org.group13.pocketpolitics.test.net;

import java.util.Iterator;

import org.group13.pocketpolitics.model.Proposer;
import org.group13.pocketpolitics.model.Moprosition;
import org.group13.pocketpolitics.model.Motion;
import org.group13.pocketpolitics.model.Proposition;
import org.group13.pocketpolitics.net.ActivityNetInterface;
import org.group13.pocketpolitics.net.Retriever;

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
				Log.i(this.getClass().getSimpleName(), "Leif: Thread slept another 500 ms. Number extra threads: "+Retriever.threadsRunning());
			}
			
			if(i>=totalWait*2){
				Retriever.cancelAllTasks();
				Log.w(this.getClass().getSimpleName(), "Leif: Cancelled after ca "+((double)i)/2+" secs");
			} else {
				Log.w(this.getClass().getSimpleName(), "Leif: Total time to retruieve 1 motion and 1 proposition: ca "+((double)i)/2+" secs");
			}
			
		} catch (InterruptedException e) {
			Log.w(this.getClass().getSimpleName(), "Leif: Interruption occured!");
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
			Log.e(this.getClass().getSimpleName(), "Leif: null result! Not success!");
			fail();
		}

		Log.w(this.getClass().getSimpleName(), "Leif: Thread returned: "+result.getBeteckning());
		Log.w(this.getClass().getSimpleName(), "Leif: Text URL: "+result.getTextURL());
		
		if(result.isMotion()){
			Motion mot = (Motion) result;
			if(mot.getSubtype()!=null){
				Log.i(this.getClass().getSimpleName(), "Leif: subtype "+ mot.getSubtype());
			} else {
				Log.e(this.getClass().getSimpleName(), "Leif: subtype Null!");
			}
			
			if(mot.getIntressenter()==null){
				Log.e(this.getClass().getSimpleName(), "Leif: Intressenter Null!");
				fail();
			} else {
				Iterator<Proposer> it = mot.getIntressenter().iterator();
				while(it.hasNext()){
					Log.i(this.getClass().getSimpleName(), "Leif: Intressent "+ it.next().getName());
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
				Log.i(this.getClass().getSimpleName(), "Leif: Text: "+text.substring(0, print)+appe);
			} else {
				Log.e(this.getClass().getSimpleName(), "Leif: Text null!");
			}
			
		} else {
			Proposition prop = (Proposition) result;
			if(prop!=null){
				Log.i(this.getClass().getSimpleName(), "Leif: Successful Proposition conversion.");
			}
		}
	}

	@Override
	public void onFailure(String message) {
		Log.e(this.getClass().getSimpleName(), "Leif: Failure: "+message);
		fail();
	}

	@Override
	public void onProgressUpdate(Integer procent) {
		// TODO Auto-generated method stub
		
	}
	
}
