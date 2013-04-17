package com.example.pocketpolitics.control;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.sun.syndication.io.XmlReader;

public class VoteRetriever {
	
	public static final String QUERY_START = "http://data.riksdagen.se/voteringlista/?"; // rm=2012%2F13&bet=Sku21
	public static final String QUERY_END = "&punkt=&valkrets=&rost=&iid=&sz=500&utformat=xml&gruppering=";
	
	private static VoteRetriever INSTANCE;
	
	public static VoteRetriever getInstance(){
		if(INSTANCE==null){
			INSTANCE = new VoteRetriever();
		}
		
		return INSTANCE;
	}
	
	public static void getVotes(String year, String articleCode){
		try {
			URL xmlSource = new URL(QUERY_START+"rm="+year+"&bet="+"articleCode"+QUERY_END);
			
			XmlReader reader = new XmlReader(xmlSource);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private VoteRetriever(){
		
	}

}
