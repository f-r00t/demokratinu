package org.group13.pocketpolitics.net;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.group13.pocketpolitics.model.Article;
import org.group13.pocketpolitics.model.Utskott;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;


public class Retriever {

	private static int threads=0;
	private static List<AsyncTask> tasks = new ArrayList<AsyncTask>();
	
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
	public static void retrieveArticles(ActivityNetInterface<QueryResult> act, String dateFrom, String dateTo, int page, int sort, Utskott utskott){
		threads++;
		ArticlesAsyncTask task =new ArticlesAsyncTask(act);
		tasks.add(task);
		task.execute(new QueryParam(dateFrom, dateTo, page, sort, utskott));
	}
	
	public static void retrieveVotes(ActivityNetInterface<String> act, Article article){
		threads++;
		VotesAsyncTask task =new VotesAsyncTask(act, article);
		tasks.add(task);
		task.execute();
	}
	
	public static void cancelAllTasks(){
		ListIterator<AsyncTask> iter = tasks.listIterator();
		while(iter.hasNext()){
			if(iter!=null && !iter.next().isCancelled()){
				iter.next().cancel(true);
				Log.w(Retriever.class.getSimpleName(), "Leif: Thread cancelled!");
			}
		}
		
		tasks.clear();
	}

	protected static void threadFinished(){
		threads--;
	}
	
	public static int threadsRunning(){
		return threads;
	}
	
	
	/**
	 * @deprecated
	 * Retrieves the latest articles in the RSS "Beslut i korthet" from riksdagen.se
	 * @param update True if want to update the RSS (access the server anew)
	 * @return A list of titles for articles
	 */
	public static void retrieveRssArticleTitles(ArtActivityInterface act){
		//threads++;
		new FeedTitlesAsyncTask(act).execute("");
	}
	
	/**
	 * @deprecated
	 * Retrieves the short text of an article.
	 * @param year ex 2013
	 * @param articleid ex Sku21
	 * @return
	 */
	public static void retrieveText(TextViewInterface tview , String year, String articleid){
		//threads++;
		new TextAsyncTask(tview, year, articleid).execute("");
	}
	
	/**
	 * 
	 * @param year	"2012" or "2012/13"
	 * @param docNum
	 * @return
	 */
	public static String translate(String year, String docNum){
		String ret = "";
		
		int slash = year.indexOf("/");
		int iyear;
		if(slash<0){
			iyear = Integer.parseInt(year);
		} else {
			String[] temp =year.split("/",2);
			iyear = Integer.parseInt(temp[0]);
			int pyear = Integer.parseInt(temp[1]);
			
			if( pyear != (iyear+1)%100){
				Log.e(MotionAsyncTask.class.getSimpleName(), "Leif: Years entered don't match! "+year);
				return null;
			}
		}
		
		
		int sec = (iyear-1976)%36;
		// ska bli 0 för år 2012
		if(sec<10){
			ret += sec;
		} else {
			ret += (char) ('A' + sec-10);
		}
		
		Log.i(MotionAsyncTask.class.getSimpleName(), "Leif: Returning "+ret+" for "+year+docNum);
		
		return ret;
	}

}
