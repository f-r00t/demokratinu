package com.example.pocketpolitics.net;

public class Retriever {
	private static Retriever INSTANCE;
	
	private ArticleRetriever artRet;
	private VoteRetriever voteRet;
	
	public static Retriever getInstance(){
		if(INSTANCE==null){
			INSTANCE = new Retriever();
		}
		
		return INSTANCE;
	}
	
	private Retriever(){
		artRet = new ArticleRetriever();
		voteRet = new VoteRetriever();
	}

}
