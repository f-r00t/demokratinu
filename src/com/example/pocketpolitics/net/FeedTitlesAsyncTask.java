package com.example.pocketpolitics.net;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.os.AsyncTask;

import com.example.pocketpolitics.model.Article;

public abstract class FeedTitlesAsyncTask extends AsyncTask<String, Integer, List<Article>> {
	@Override
	protected List<Article> doInBackground(String... strings){
		
		List<String> titles = Retriever.getInstance().getImportantArticleTitles(false);
		List<Article> arts = new ArrayList<Article>();
		
		Iterator<String> iter = titles.listIterator();
		while(iter.hasNext() && !this.isCancelled()){
			Article a = new Article();
			a.setTitle(iter.next());
			arts.add(a);
		}
		
		return arts;
	}
	
	@Override
	protected abstract void onPreExecute();
	
	@Override
	protected abstract void onPostExecute(List<Article> result);
	
	@Override
	protected abstract void onCancelled(List<Article> result);
	
}
