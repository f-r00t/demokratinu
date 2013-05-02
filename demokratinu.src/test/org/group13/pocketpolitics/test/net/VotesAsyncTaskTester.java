package org.group13.pocketpolitics.test.net;

import java.util.ListIterator;

import org.group13.pocketpolitics.net.ActivityNetInterface;
import org.group13.pocketpolitics.net.PartyVote;
import org.group13.pocketpolitics.net.Retriever;
import org.group13.pocketpolitics.net.VotesResult;

import android.test.AndroidTestCase;
import android.util.Log;

public class VotesAsyncTaskTester extends AndroidTestCase implements ActivityNetInterface<VotesResult>{

	private final int totalWait = 20;	//secs
	
	public void testVotes(){
		Retriever.retrieveVotes(this, "H001UbU5", "Ub354");
		int threads;
		int i = 0;
		boolean cancelled = false;
		
		try{
			do{
				threads = Retriever.threadsRunning();
				Thread.sleep(500);
				i++;
				Log.i(this.getClass().getSimpleName(), "Leif: Slept another 500 ms. Number extra threads: "+threads);
				
				if(i>totalWait*2){
					Retriever.cancelAllTasks();
					cancelled = true;
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
	public void onSuccess(VotesResult votes) {
		if(votes==null){
			Log.e(this.getClass().getSimpleName(), "Leif: Votes null, something went wrong!");
			fail();
		} else {
			Log.w(this.getClass().getSimpleName(), "Leif: Votes retrieved.");
			
			Log.i(this.getClass().getSimpleName(), "Leif: " +votes.vinnare + " vann omröstningen");
			ListIterator<PartyVote> iter = votes.voteItems.listIterator();
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
}
