package com.example.pocketpolitics.net.new_solution;

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
