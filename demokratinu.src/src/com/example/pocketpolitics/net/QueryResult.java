package com.example.pocketpolitics.net;

import java.util.List;

import com.example.pocketpolitics.model.Article;

public class QueryResult {
	public final int thisPage;
	public final int totalPages;
	public final List<Article> arts;
	
	
	QueryResult(List<Article> arts, int totalPages, int thisPage){
		this.arts = arts;
		this.totalPages = totalPages;
		this.thisPage = thisPage;
	}

}
