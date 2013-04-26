package com.test.pocketpolitics.net;

import java.util.List;
import java.util.ListIterator;

import android.test.AndroidTestCase;
import android.util.Log;

import com.example.pocketpolitics.model.Article;
import com.example.pocketpolitics.net.ArtActivityInterface;
import com.example.pocketpolitics.net.QueryResult;
import com.example.pocketpolitics.net.Retriever;
import com.example.pocketpolitics.net.TextViewInterface;

public class NewRetrieverTester extends AndroidTestCase implements TextViewInterface, ArtActivityInterface {

	private final int ntexts = 1;
	private final int totalwait = 20;	//secs
	
	private int threads;
	private int finished;
	private Retriever retr;

	public NewRetrieverTester(){
		threads = 0;
		finished = 0;
		retr = Retriever.getInstance();
	}
	
	public void testArticlesAsyncTask(){
		for(int i=0; i<ntexts; i++){
			retr.retrieveArticles(this, "", "2013-04-26", i, -1);
			threads++;
		}
		
		try {
			int i = 0;
			for( ; i<totalwait*2 && finished!=ntexts; i++){
				Thread.sleep(500);
				Log.i(this.getClass().getSimpleName(), "Leif: Slept another 500 ms. Number extra threads: "+threads);
			}
			Log.i(this.getClass().getSimpleName(), "Leif: Total time to retrieve "+ntexts+ " texts: "+(i/2)+" secs"); 
		} catch (InterruptedException e) {
			Log.e(this.getClass().getSimpleName(), "Leif: error: interruption");
			e.printStackTrace();
		}
		Log.i(this.getClass().getSimpleName(), "Leif: Thread finished sleeping");
	}

	public void testTextAsyncTask(){
		retr.retrieveRssArticleTitles(this);
		threads++;
		
		try {
			int i = 0;
			for( ; i<totalwait*2 && finished!=ntexts; i++){
				Thread.sleep(500);
				Log.i(this.getClass().getSimpleName(), "Leif: Slept another 500 ms. Number extra threads: "+threads);
			}
			Log.i(this.getClass().getSimpleName(), "Leif: Total time to retrieve "+ntexts+ " texts: "+(i/2)+" secs"); 
		} catch (InterruptedException e) {
			Log.e(this.getClass().getSimpleName(), "Leif: error: interruption");
			e.printStackTrace();
		}
		Log.i(this.getClass().getSimpleName(), "Leif: Thread finished sleeping");
	}

	@Override
	public void addArticles(List<Article> arts) {
		threads--;
		
		ListIterator<Article> it = arts.listIterator();
		int is = 0;
		while(it.hasNext() && is++<ntexts){
			Article a = it.next();
			retr.retrieveText(this, "2013", a.getDokid());
			threads++;
			
			Log.w(this.getClass().getSimpleName(), "Leif testing TextAsyncTask. " + "Dokid: "+a.getDokid()+" Title: "+a.getTitle());
		}
		if(is<ntexts){
			fail();
		}
	}
	

	@Override
	public void addArticles(QueryResult qres) {
		threads--;
		finished++;
		
		Log.w(this.getClass().getSimpleName(), "Leif testing ArticlesAsyncTask. Page no "+qres.thisPage+ " just finished.");
		
		ListIterator<Article> it = qres.arts.listIterator();
		int is = 0;
		while(it.hasNext() ){
			Article a = it.next();
			
			Log.i(this.getClass().getSimpleName(), "Leif testing ArticlesAsyncTask["+qres.thisPage + "] Dokid: "+a.getDokid()+" Title: "+a.getTitle() + " Id: "+a.getId());
		}
		
		
	}

	@Override
	public void wasCancelled(QueryResult qres) {
		threads--;
		Log.w(this.getClass().getSimpleName(), "Leif testing ArticlesAsyncTask: Thread Cancelled!");
	}

	@Override
	public void setText(String text) {
		threads--;
		finished++;
		Log.w(this.getClass().getSimpleName(), "Leif testing TextAsyncTask: "+text);
		
		if(threads==0 && finished != ntexts){
			Log.e(this.getClass().getSimpleName(),"Leif: thread error, did not retrieve texts properly");
			fail();
		}
	}

	@Override
	public void onPreExecute() {
		// TODO Auto-generated method stub
		
	}




}
