package com.example.pocketpolitics.control;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import org.junit.Test;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;



public class ArticleRetriever {
	
	public static final String FEED_URL = "http://www.riksdagen.se/sv/Debatter--beslut/?rss=true&type=biksmall";
	
	private static ArticleRetriever INSTANCE;
	private static SyndFeed feed;
	
	public static ArticleRetriever getInstance(){
		if(INSTANCE==null){
			INSTANCE = new ArticleRetriever();
		}
		
		if(feed!=null)
			return INSTANCE;
		return null;
	}
	
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
	
	private void printFeed(){
		if(feed==null){
			System.out.println("Error in printFeed! null feed!");
			return;
		}
		
		System.out.println("Feed name: "+feed.getAuthor()+"\n");
		
		for (Iterator i = feed.getEntries().iterator(); i.hasNext();){
			SyndEntry entry = (SyndEntry) i.next();
			System.out.println("Inlägg:"+ entry.getTitle());
			System.out.println(entry.getLink()+"\n");
		}
	}
	
	@Test
	public static void test(){
		
	}
	
	
}
