package com.example.pocketpolitics.control;

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
	
	public static void getVotes(){
		
	}
	
	private VoteRetriever(){
		
	}

}
