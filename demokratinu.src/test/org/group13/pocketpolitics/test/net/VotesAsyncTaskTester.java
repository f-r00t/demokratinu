package org.group13.pocketpolitics.test.net;

import java.util.ListIterator;

import org.group13.pocketpolitics.model.Article;
import org.group13.pocketpolitics.model.PartyVote;
import org.group13.pocketpolitics.model.CommitteeProposal;
import org.group13.pocketpolitics.net.ActivityNetInterface;
import org.group13.pocketpolitics.net.Retriever;

import android.test.AndroidTestCase;
import android.util.Log;

public class VotesAsyncTaskTester extends AndroidTestCase implements ActivityNetInterface<String>{

	private final int totalWait = 20;	//secs
	private Article testArt;
	
	public void testVotes(){
		
		this.testArt = new Article();
		testArt.setId("H001UbU5");
		
		Retriever.retrieveVotes(this, testArt);
		int threads;
		int i = 0;
		boolean cancelled = false;
		
		try{
			do{
				threads = Retriever.threadsRunning();
				Thread.sleep(500);
				i++;
				Log.i(this.getClass().getSimpleName(), "Leif: Thread slept another 500 ms. Number extra threads: "+threads);
				
				if(i>totalWait*2){
					cancelled = true;
					Retriever.cancelAllTasks();
				}
				
			}while(threads>0);
			
			if(cancelled){
				Log.w(this.getClass().getSimpleName(), "Leif: Cancelled after ca "+((double)i)/2+" secs");
			} else {
				Log.w(this.getClass().getSimpleName(), "Leif: Total time to retrieve votes for 1 article: ca "+((double)i)/2+" secs");
			}
			
			
		} catch(InterruptedException e) {
			Log.w(this.getClass().getSimpleName(), "Leif: Interruption occured!");
			fail();
		}
		
	}

	@Override
	public void onSuccess(String id) {
		if(id==null || !testArt.getId().equals(id)){
			Log.e(this.getClass().getSimpleName(), "Leif: in @.onSuccess(): id null, something went wrong!");
			fail();
		}
		
		if(testArt.getFors()==null){
			Log.e(this.getClass().getSimpleName(), "Leif: in @.onSuccess(): .getFors() null, something went wrong!");
			fail();
		}
		else {
			Log.w(this.getClass().getSimpleName(), "Leif: Votes retrieved.");
			
			CommitteeProposal fors = testArt.getFors().get(0);
			
			Log.i(this.getClass().getSimpleName(), "Leif: " +fors.vinnare + " vann omröstningen");
			ListIterator<PartyVote> iter = fors.voteItems.listIterator();
			while(iter.hasNext()){
				this.printPartyVote(iter.next());
			}
		}
	}
	
	@Override
	public void onFailure(String message) {
		Log.e(this.getClass().getSimpleName(), "Leif: Votes failed: "+message);
		fail();
	}
	
	private void printPartyVote(PartyVote vit){
		Log.i(this.getClass().getSimpleName(), "Leif:   "+vit.party+": " + vit.yes + " ja, " + vit.no + " nej, "+ vit.neutral + " avstår, " + vit.absent + " frånvarande");
	}

	@Override
	public void onPreExecute() {
		Log.i(this.getClass().getSimpleName(), "Leif: Votes test commencing...");
		
	}

	@Override
	public void onProgressUpdate(Integer procent) {
		// TODO Auto-generated method stub
		
	}
}
