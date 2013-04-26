package com.example.pocketpolitics.net;

import java.util.List;

import com.example.pocketpolitics.model.Article;

public class QueryResult {
	public final int thisPage;
	public final int totalPages;
	public final int totalTraffar;
	public final List<Article> arts;
	
	/**
	 * 
	 * @param arts
	 * @param totalPages
	 * @param thisPage
	 * @param totalTraffar 
	 */
	QueryResult(List<Article> arts, int totalPages, int thisPage, int totalTraffar){
		this.arts = arts;
		this.totalPages = totalPages;
		this.thisPage = thisPage;
		this.totalTraffar = totalTraffar;
	}

}
