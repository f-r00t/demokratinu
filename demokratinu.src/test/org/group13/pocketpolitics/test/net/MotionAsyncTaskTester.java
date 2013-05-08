package org.group13.pocketpolitics.test.net;

import org.group13.pocketpolitics.model.Moprosition;
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
			
			if(i>totalWait*2){
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
		//TODO
	}
	
	private void atestProposition(){
		//TODO
	}

	@Override
	public void onPreExecute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(Moprosition result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFailure(String message) {
		// TODO Auto-generated method stub
		
	}
	
}
