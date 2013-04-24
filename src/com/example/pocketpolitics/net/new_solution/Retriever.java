package com.example.pocketpolitics.net.new_solution;

import java.util.List;

public class Retriever {
	private static Retriever INSTANCE;
	
	/*private ArticleRetriever artRet;
	private VoteRetriever voteRet;
	private TextRetriever texRet;*/
	
	public static Retriever getInstance(){
		if(INSTANCE==null){
			INSTANCE = new Retriever();
		}
		
		return INSTANCE;
	}
	
	private Retriever(){
	}
	
	/**
	 * Retrieves the latest articles in the RSS "Beslut i korthet" from riksdagen.se
	 * @param update True if want to update the RSS (access the server anew)
	 * @return A list of titles for articles
	 */
	public void retrieveRssArticleTitles(ArtActivityInterface act){
		new FeedTitlesAsyncTask(act).execute("");
	}
	
	/**
	 * Retrieves the short text of an article.
	 * @param year ex 2013
	 * @param articleid ex Sku21
	 * @return
	 */
	public void retrieveText(TextViewInterface tview , String year, String articleid){
		new TextAsyncTask(tview, year, articleid).execute("");
	}
	
	

}
