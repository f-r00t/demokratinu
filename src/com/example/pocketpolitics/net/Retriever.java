package com.example.pocketpolitics.net;

import java.util.List;

public class Retriever {
	private static Retriever INSTANCE;
	
	private ArticleRetriever artRet;
	private VoteRetriever voteRet;
	private TextRetriever texRet;
	
	public static Retriever getInstance(){
		if(INSTANCE==null){
			INSTANCE = new Retriever();
		}
		
		return INSTANCE;
	}
	
	/**
	 * Retrieves the latest articles in the RSS "Beslut i korthet" from riksdagen.se
	 * @param update True if want to update the RSS (access the server anew)
	 * @return A list of titles for articles
	 */
	public List<String> getImportantArticleTitles(boolean update){
		if(update)
			artRet.update();
		return artRet.getArticleTitles();
	}
	
	/**
	 * Retrieves the short text of an article.
	 * @param year ex 2013
	 * @param articleid ex Sku21
	 * @return
	 */
	public String getText(String year, String articleid){
		return texRet.getText(year, articleid);
		//new TextRetriever().execute(year, articleid);
		//return TextRetriever.getResult();
	}
	
	private Retriever(){
		artRet = new ArticleRetriever();
		voteRet = new VoteRetriever();
		texRet = new TextRetriever();
	}

}
