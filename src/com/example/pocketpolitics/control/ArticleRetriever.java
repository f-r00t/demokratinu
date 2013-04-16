package com.example.pocketpolitics.control;

public class ArticleRetriever {
	
	public static final String FEED_URL = "http://www.riksdagen.se/sv/Debatter--beslut/?rss=true&type=biksmall";
	
	private static ArticleRetriever INSTANCE;
	
	private ArticleRetriever(){
	}
	public static ArticleRetriever getInstance(){
		if(INSTANCE==null){
			INSTANCE = new ArticleRetriever();
		}
		
		return INSTANCE;
	}
	
}
