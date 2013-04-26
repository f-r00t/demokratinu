package com.test.pocketpolitics.net;

import java.util.List;
import java.util.ListIterator;

import android.test.AndroidTestCase;
import android.util.Log;

import com.example.pocketpolitics.model.Article;
import com.example.pocketpolitics.net.ArtActivityInterface;
import com.example.pocketpolitics.net.QueryResult;
import com.example.pocketpolitics.net.Retriever;

public class ArticlesAsyncTaskTester extends AndroidTestCase implements ArtActivityInterface  {

	private final int npages = 1;
	private final int totalwait = 20;	//secs
	
	private int threads;
	private int finished;
	private Retriever retr;

	public ArticlesAsyncTaskTester(){
		threads = 0;
		finished = 0;
		retr = Retriever.getInstance();
	}

	public void testArticlesAsyncTask(){
		for(int i=0; i<npages; i++){
			retr.retrieveArticles(this, "", "2013-04-26", i+1, -1);
			threads++;
		}
		
		try {
			int i = 0;
			for( ; i<totalwait*2 && finished!=npages; i++){
				Thread.sleep(500);
				Log.i(this.getClass().getSimpleName(), "Leif: Slept another 500 ms. Number extra threads: "+threads);
			}
			Log.i(this.getClass().getSimpleName(), "Leif: Total time to retrieve "+npages+ " pages: "+(i/2)+" secs"); 
		} catch (InterruptedException e) {
			Log.e(this.getClass().getSimpleName(), "Leif: error: interruption");
			e.printStackTrace();
		}
		Log.i(this.getClass().getSimpleName(), "Leif: Thread finished sleeping");
	}
	@Override
	public void addArticles(QueryResult qres) {
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
		while(it.hasNext() ){
			Article a = it.next();
			
			Log.i(this.getClass().getSimpleName(), "Leif testing ArticlesAsyncTask["+qres.thisPage + "] Dokid: "+a.getDokid()+" Title: "+a.getTitle() + " Id: "+a.getId());
		}
		
	}
	@Override
	public void wasCancelled(QueryResult qres) {
		threads--;
		finished++;
		Log.w(this.getClass().getSimpleName(), "Leif testing ArticlesAsyncTask: Thread Cancelled!");
	}
	@Override
	public void onPreExecute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addArticles(List<Article> arts) {
		// TODO Auto-generated method stub
		
	}
}
