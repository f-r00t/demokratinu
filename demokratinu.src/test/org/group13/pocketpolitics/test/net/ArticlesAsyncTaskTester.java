package org.group13.pocketpolitics.test.net;

import java.util.ListIterator;

import org.group13.pocketpolitics.model.riksdag.Agenda;
import org.group13.pocketpolitics.model.riksdag.Committee;
import org.group13.pocketpolitics.net.data.QueryResult;
import org.group13.pocketpolitics.net.riksdag.ActivityNetInterface;
import org.group13.pocketpolitics.net.riksdag.Retriever;

import android.test.AndroidTestCase;
import android.util.Log;


public class ArticlesAsyncTaskTester extends AndroidTestCase implements ActivityNetInterface<QueryResult>  {

	private final int npages = 3;
	private final int totalwait = 20;	//secs
	private final int show = 4;
	
	private int threads;
	private int finished;

	public ArticlesAsyncTaskTester(){
		threads = 0;
		finished = 0;
	}

	public void testArticlesAsyncTask(){
		for(int i=0; i<npages; i++){
			Retriever.retrieveArticles(this, "", "2013-04-26", i+1, -1, Committee.NULL);
			threads++;
		}
		
		try {
			int i = 0;
			for( ; i<totalwait*2 && finished!=npages; i++){
				Thread.sleep(500);
				Log.i(this.getClass().getSimpleName(), "PocketDebug: Slept another 500 ms. Number extra threads: "+threads);
			}
			Log.i(this.getClass().getSimpleName(), "PocketDebug: Total time to retrieve "+npages+ " pages: ca "+((double)i)/2+" secs"); 
		} catch (InterruptedException e) {
			Log.e(this.getClass().getSimpleName(), "PocketDebug: error: interruption");
			e.printStackTrace();
		}
		Log.i(this.getClass().getSimpleName(), "PocketDebug: Thread finished sleeping");
	}
	@Override
	public void onSuccess(QueryResult qres) {
		threads--;
		finished++;
		
		if(qres == null){
			Log.e(this.getClass().getSimpleName(), "PocketDebug: Something went wrong! QueryResult is null!");
			fail();
			return;
		}
		
		Log.w(this.getClass().getSimpleName(), "PocketDebug: testing ArticlesAsyncTask. Page no "+qres.getThisPage()+ " just finished.");
		
		Log.i(this.getClass().getSimpleName(), "PocketDebug: QueryResults sidor="+qres.getTotalPages()+". Artiklar på denna sida: "+qres.getArts().size());
		
		ListIterator<Agenda> it = qres.getArts().listIterator();
		int is=0;
		while(it.hasNext() && is++<show){
			Agenda a = it.next();
			
			Log.i(this.getClass().getSimpleName(), "PocketDebug: testing ArticlesAsyncTask["+qres.getThisPage() + "] Id: "+a.getId()+" Beslutsdatum "+a.getBeslutsdag());
		}
		
	}
	@Override
	public void onFailure(String message) {
		threads--;
		finished++;
		Log.w(this.getClass().getSimpleName(), "PocketDebug: testing ArticlesAsyncTask: Failed "+message);
	}
	@Override
	public void onPreExecute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProgressUpdate(Integer procent) {
		// TODO Auto-generated method stub
		
	}
}
