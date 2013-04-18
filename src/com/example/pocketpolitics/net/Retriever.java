package com.example.pocketpolitics.net;

import java.util.List;

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
	
	public List<String> getImportantArticleTitles(boolean update){
		if(update)
			artRet.update();
		return artRet.getArticleTitles();
	}
	
	private Retriever(){
		artRet = new ArticleRetriever();
		voteRet = new VoteRetriever();
	}

}
