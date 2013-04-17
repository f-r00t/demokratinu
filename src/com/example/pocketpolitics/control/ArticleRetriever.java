package com.example.pocketpolitics.control;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;


public class ArticleRetriever {
	
	public static final String FEED_URL = "http://www.riksdagen.se/sv/Debatter--beslut/?rss=true&type=biksmall";
	
	private static ArticleRetriever INSTANCE = null;
	private static SyndFeed feed = null;
	private static List<SyndEntry> entries = null;
	
	public static ArticleRetriever getInstance(){
		if(INSTANCE==null){
			INSTANCE = new ArticleRetriever();
		}
		
		if(feed!=null)
			return INSTANCE;
		return null;
	}
	
	private ArticleRetriever(){
		update();
	}
	
	public void update(){
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
		
		if(feed!=null){
			entries = feed.getEntries();
		}
		
	}
	
	public List<String> getArticleTitles(){
		if(entries == null)
			return null;
		
		List<String> ls = new ArrayList<String>();
		for (Iterator<SyndEntry> i = feed.getEntries().iterator(); i.hasNext();){
			SyndEntry entry = i.next();
			ls.add( entry.getTitle());
		}
		
		return ls;
	}
	
	public boolean printFeed(){

		if(feed==null){
			System.out.println("Error in printFeed! null feed!");
			return false;
		}
		
		System.out.println("Feed name: "+feed.getAuthor()+"\n");
		
		for (Iterator<SyndEntry> i = feed.getEntries().iterator(); i.hasNext();){
			SyndEntry entry = i.next();
			System.out.println("Inlägg:"+ entry.getTitle());
			System.out.println(entry.getLink()+"\n");
		}
		
		return true;
	}
	
	
}
