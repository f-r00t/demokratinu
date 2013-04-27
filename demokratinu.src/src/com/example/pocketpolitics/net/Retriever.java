package com.example.pocketpolitics.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class Retriever {
	private static Retriever INSTANCE;
	
	public static Retriever getInstance(){
		if(INSTANCE==null){
			INSTANCE = new Retriever();
		}
		
		return INSTANCE;
	}
	
	private Retriever(){
	}
	
	public static boolean isConnected(Context ctx){
		if(ctx == null){
			Log.e(Retriever.class.getSimpleName(),"Leif: in Connected: Context null error");
			return false;
		}
		
		ConnectivityManager conMgr = (ConnectivityManager)
				ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netwInfo = conMgr.getActiveNetworkInfo();
		
		return netwInfo != null && netwInfo.isConnected();
	}
	
	/**
	 * 
	 * Retrieves articles to the GUI. These articles will contain title, id, date, text etc from data.Riksdagen.se but not votations or comments. Allows sorting and filtering.
	 * Date format: "yyyy-mm-dd"
	 * 
	 * @param act		Interface for updating GUI
	 * @param dateFrom  Search for articles after this date, leave empty "" for no restriction
	 * @param dateTo	Search for articles up to this date, leave empty "" for no restriction
	 * @param page		Get result page, default 1
	 * @param sort		Sort results: default = sort after date (only closed issues);
	 * 0 = sort after date (all issues); 
	 * 1 = sort after relevance (all issues). Relevance is determined by data.Riksdagen.se 
	 */
	public void retrieveArticles(ArtActivityInterface act, String dateFrom, String dateTo, int page, int sort){
		new ArticlesAsyncTask(act).execute(new QueryParam(dateFrom, dateTo, page, sort));
	}
	
	/**
	 * @deprecated
	 * Retrieves the latest articles in the RSS "Beslut i korthet" from riksdagen.se
	 * @param update True if want to update the RSS (access the server anew)
	 * @return A list of titles for articles
	 */
	public void retrieveRssArticleTitles(ArtActivityInterface act){
		new FeedTitlesAsyncTask(act).execute("");
	}
	
	/**
	 * @deprecated
	 * Retrieves the short text of an article.
	 * @param year ex 2013
	 * @param articleid ex Sku21
	 * @return
	 */
	public void retrieveText(TextViewInterface tview , String year, String articleid){
		new TextAsyncTask(tview, year, articleid).execute("");
	}
	
	

}
