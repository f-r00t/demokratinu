package org.group13.pocketpolitics.test.net;

import java.util.ListIterator;

import org.group13.pocketpolitics.model.riksdag.Agenda;
import org.group13.pocketpolitics.model.riksdag.CommitteeProposal;
import org.group13.pocketpolitics.model.riksdag.PartyVote;
import org.group13.pocketpolitics.net.ActivityNetInterface;
import org.group13.pocketpolitics.net.Retriever;

import android.test.AndroidTestCase;
import android.util.Log;

public class VotesAsyncTaskTester extends AndroidTestCase implements ActivityNetInterface<String>{

	private final int totalWait = 20;	//secs
	private Agenda testArt;
	
	public void testVotes(){
		
		this.testArt = new Agenda(null, null, null, 0, null, "H001UbU5", null, null, -1, null, null, -1, null);
		//testArt.setId("H001UbU5");
		
		Retriever.retrieveVotes(this, testArt);
		int threads;
		int i = 0;
		boolean cancelled = false;
		
		try{
			do{
				threads = Retriever.threadsRunning();
				Thread.sleep(500);
				i++;
				Log.i(this.getClass().getSimpleName(), "PocketDebug: Thread slept another 500 ms. Number extra threads: "+threads);
				
				if(i>totalWait*2){
					cancelled = true;
					Retriever.cancelAllTasks();
				}
				
			}while(threads>0);
			
			if(cancelled){
				Log.w(this.getClass().getSimpleName(), "PocketDebug: Cancelled after ca "+((double)i)/2+" secs");
			} else {
				Log.w(this.getClass().getSimpleName(), "PocketDebug: Total time to retrieve votes for 1 article: ca "+((double)i)/2+" secs");
			}
			
			
		} catch(InterruptedException e) {
			Log.w(this.getClass().getSimpleName(), "PocketDebug: Interruption occured!");
			fail();
		}
		
	}

	@Override
	public void onSuccess(String id) {
		if(id==null || !testArt.getId().equals(id)){
			Log.e(this.getClass().getSimpleName(), "PocketDebug: in @.onSuccess(): id null, something went wrong!");
			fail();
		}
		
		if(testArt.getFors()==null){
			Log.e(this.getClass().getSimpleName(), "PocketDebug: in @.onSuccess(): .getFors() null, something went wrong!");
			fail();
		}
		else {
			Log.w(this.getClass().getSimpleName(), "PocketDebug: Votes retrieved.");
			
			CommitteeProposal fors = testArt.getFors().get(0);
			
			Log.i(this.getClass().getSimpleName(), "PocketDebug: " +fors.getVinnare() + " vann omröstningen");
			ListIterator<PartyVote> iter = fors.getVoteItems().listIterator();
			while(iter.hasNext()){
				this.printPartyVote(iter.next());
			}
		}
	}
	
	@Override
	public void onFailure(String message) {
		Log.e(this.getClass().getSimpleName(), "PocketDebug: Votes failed: "+message);
		fail();
	}
	
	private void printPartyVote(PartyVote vit){
		Log.i(this.getClass().getSimpleName(), "PocketDebug:   "+vit.getParty()+": " + vit.getYes() + " ja, " + vit.getNo() + " nej, "+ vit.getNeutral() + " avstår, " + vit.getAbsent() + " frånvarande");
	}

	@Override
	public void onPreExecute() {
		Log.i(this.getClass().getSimpleName(), "PocketDebug: Votes test commencing...");
		
	}

	@Override
	public void onProgressUpdate(Integer procent) {
		// TODO Auto-generated method stub
		
	}
}
