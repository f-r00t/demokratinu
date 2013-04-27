package com.example.pocketpolitics.net;

import java.util.List;

import com.example.pocketpolitics.model.Article;

public interface ArtActivityInterface {

	public void onPreExecute();
	
	/**
	 * @deprecated
	 * @param arts
	 */
	public void addArticles(List<Article> arts);
	public void addArticles(QueryResult qres);
	
	public void wasCancelled(QueryResult qres);
}
