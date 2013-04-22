package com.example.pocketpolitics.net;

import java.util.List;

import android.os.AsyncTask;

import com.example.pocketpolitics.model.Article;

public abstract class FeedTitlesAsyncTask extends AsyncTask<String, Integer, Article> {
	@Override
	protected Article doInBackground(String... strings){
		Article r = new Article();
		List<String> titles = Retriever.getInstance().getImportantArticleTitles(false);
		
		r.setTitle(titles.get(0));
		
		return r;
	}
	
	@Override
	protected abstract void onPreExecute();
	
	@Override
	protected abstract void onPostExecute(Article result);
	
	@Override
	protected abstract void onCancelled(Article result);
	
}
