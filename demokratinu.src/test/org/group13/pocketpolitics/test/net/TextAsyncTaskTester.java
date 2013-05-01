package org.group13.pocketpolitics.test.net;

import java.util.List;
import java.util.ListIterator;

import org.group13.pocketpolitics.model.Article;
import org.group13.pocketpolitics.net.ArtActivityInterface;
import org.group13.pocketpolitics.net.QueryResult;
import org.group13.pocketpolitics.net.Retriever;
import org.group13.pocketpolitics.net.TextViewInterface;

import android.test.AndroidTestCase;
import android.util.Log;


public class TextAsyncTaskTester extends AndroidTestCase implements TextViewInterface, ArtActivityInterface {

	private final int ntexts = 5;
	private final int totalwait = 20;	//secs
	
	private int threads;
	private int finished;

	public TextAsyncTaskTester(){
		threads = 0;
		finished = 0;
	}
	
	public void testTextAsyncTask(){
		Retriever.retrieveRssArticleTitles(this);
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
			Retriever.retrieveText(this, "2013", a.getDokid());
			threads++;
			
			Log.w(this.getClass().getSimpleName(), "Leif testing TextAsyncTask. " + "Dokid: "+a.getDokid()+" Title: "+a.getTitle());
		}
		if(is<ntexts){
			fail();
		}
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
	public void onArticlesPreExecute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addArticles(QueryResult qres) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void articlesCancelled(QueryResult qres) {
		// TODO Auto-generated method stub
		
	}
}
