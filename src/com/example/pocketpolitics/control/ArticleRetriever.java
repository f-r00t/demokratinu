package com.example.pocketpolitics.control;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;



public class ArticleRetriever {
	
	public static final String FEED_URL = "http://www.riksdagen.se/sv/Debatter--beslut/?rss=true&type=biksmall";
	
	private static ArticleRetriever INSTANCE;
	private static SyndFeed feed;
	
	private ArticleRetriever(){
		try {
			
			URL feedSource = new URL(FEED_URL);
			SyndFeedInput input = new SyndFeedInput();
			if(feedSource!=null)
				feed = input.build(new XmlReader(feedSource));
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FeedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
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
