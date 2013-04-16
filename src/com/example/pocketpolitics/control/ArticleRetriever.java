package com.example.pocketpolitics.control;

import java.net.MalformedURLException;
import java.net.URL;

import com.sun.syndication.feed.synd.SyndFeed;



public class ArticleRetriever {
	
	public static final String FEED_URL = "http://www.riksdagen.se/sv/Debatter--beslut/?rss=true&type=biksmall";
	
	private static ArticleRetriever INSTANCE;
	private static SyndFeed feed;
	
	private ArticleRetriever(){
		URL feedSource;
		try {
			feedSource = new URL(FEED_URL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static ArticleRetriever getInstance(){
		if(INSTANCE==null){
			INSTANCE = new ArticleRetriever();
		}
		
		return INSTANCE;
	}
	
}
