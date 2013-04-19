package com.example.pocketpolitics.net;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.DataFormatException;

import com.sun.syndication.io.XmlReader;

class VoteRetriever {
	
	public static final String QUERY_START = "http://data.riksdagen.se/voteringlista/?"; // rm=2012%2F13&bet=Sku21
	public static final String QUERY_END = "&punkt=&valkrets=&rost=&iid=&sz=500&utformat=xml&gruppering=";
	
	//private static VoteRetriever INSTANCE;
	
	/*public static VoteRetriever getInstance(){
		if(INSTANCE==null){
			INSTANCE = new VoteRetriever();
		}
		
		return INSTANCE;
	}*/
	
	/**
	 * @param year
	 * Ex "2012" motsvarar mandatperioden "2012/2013"
	 * 
	 * @param articleCode
	 * Ex Sku21
	 */
	public void getVotes(String year, String articleCode) throws DataFormatException{
		if(year.length()!=4)
			throw new DataFormatException("parameter year: not length 4!");
		
		int last = Integer.parseInt(year.substring(2));
		String qyear =  year + "%2F" + Integer.toString(++last);
		
		if("2012".equals(year) && ! "2012%2F13".equals(qyear))
			throw new DataFormatException("format error in getVotes()");
		
		
		try {
			URL xmlSource = new URL(QUERY_START+"rm="+qyear+"&bet="+"articleCode"+QUERY_END);
			
			XmlReader reader = new XmlReader(xmlSource);
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected VoteRetriever(){
		
	}

}
