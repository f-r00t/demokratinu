package org.group13.pocketpolitics.test.net;

import java.util.ListIterator;

import org.group13.pocketpolitics.model.Article;
import org.group13.pocketpolitics.model.Utskott;
import org.group13.pocketpolitics.net.ActivityNetInterface;
import org.group13.pocketpolitics.net.QueryResult;
import org.group13.pocketpolitics.net.Retriever;

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
			Retriever.retrieveArticles(this, "", "2013-04-26", i+1, -1, Utskott.NULL);
			threads++;
		}
		
		try {
			int i = 0;
			for( ; i<totalwait*2 && finished!=npages; i++){
				Thread.sleep(500);
				Log.i(this.getClass().getSimpleName(), "Leif: Slept another 500 ms. Number extra threads: "+threads);
			}
			Log.i(this.getClass().getSimpleName(), "Leif: Total time to retrieve "+npages+ " pages: ca "+((double)i)/2+" secs"); 
		} catch (InterruptedException e) {
			Log.e(this.getClass().getSimpleName(), "Leif: error: interruption");
			e.printStackTrace();
		}
		Log.i(this.getClass().getSimpleName(), "Leif: Thread finished sleeping");
	}
	@Override
	public void onSuccess(QueryResult qres) {
		threads--;
		finished++;
		
		if(qres == null){
			Log.e(this.getClass().getSimpleName(), "Leif: Something went wrong! QueryResult is null!");
			fail();
			return;
		}
		
		Log.w(this.getClass().getSimpleName(), "Leif testing ArticlesAsyncTask. Page no "+qres.thisPage+ " just finished.");
		
		Log.i(this.getClass().getSimpleName(), "Leif: QueryResults sidor="+qres.totalPages+". Artiklar på denna sida: "+qres.arts.size());
		
		ListIterator<Article> it = qres.arts.listIterator();
		int is=0;
		while(it.hasNext() && is++<show){
			Article a = it.next();
			
			Log.i(this.getClass().getSimpleName(), "Leif testing ArticlesAsyncTask["+qres.thisPage + "] Id: "+a.getId()+" Beslutsdatum "+a.getBeslutsdag());
		}
		
	}
	@Override
	public void onFailure(String message) {
		threads--;
		finished++;
		Log.w(this.getClass().getSimpleName(), "Leif testing ArticlesAsyncTask: Failed "+message);
	}
	@Override
	public void onPreExecute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProgressUpdate(QueryResult halfFinished) {
		// TODO Auto-generated method stub
		
	}
}
