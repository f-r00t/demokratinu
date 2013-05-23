package org.group13.pocketpolitics.control;

import java.util.ArrayList;
import java.util.List;

import org.group13.pocketpolitics.model.riksdag.Agenda;
import org.group13.pocketpolitics.model.riksdag.Committee;
import org.group13.pocketpolitics.model.user.Article;
import org.group13.pocketpolitics.net.riksdag.data.Filter;
import org.group13.pocketpolitics.net.riksdag.data.QueryParam;
import org.group13.pocketpolitics.net.riksdag.data.QueryResult;

import android.util.Log;

/**
 * Singleton class which remembers the retrieved Articles and what page to retrieve next.
 * @author Leif
 *
 */
class ArticleMemoryController {
	private static ArticleMemoryController INSTANCE;
	
	// TODO Make ArticleMemCtrl should handle Articles, not Articles
	private List<Article> articles;
	private Filter filter;
	private int lastPage;
	private int totalPages;
	
	public static int retrievedPage(QueryResult result){
		checkInstance();
		
		if(result.getThisPage() != 1+INSTANCE.lastPage){
			Log.w(ArticleMemoryController.class.getSimpleName(), "PocketDebug: wrong papge count! internally: "+INSTANCE.lastPage+", retrieved: "+result.getThisPage());
			return -1;
		}
		
		
		INSTANCE.lastPage++;
		List<Agenda> resulta = result.getArts();
		for(Agenda ag : resulta){
			INSTANCE.articles.add(new Article(ag, null));
		}
		//INSTANCE.articles.addAll(result.getArts());
		return result.getThisPage();
	}
	
	public static int nextPage(){
		checkInstance();
		if(INSTANCE.lastPage==INSTANCE.totalPages){
			Log.w(ArticleMemoryController.class.getSimpleName(), "PocketDebug: Reached last page: "+INSTANCE.totalPages);
			return -1;
		}
		return INSTANCE.lastPage+1;
	}
	
	public static QueryParam nextQuery(){
		checkInstance();
		int np = nextPage();
		if(np==-1){
			//Last page
			return null;
		}
		return new QueryParam(INSTANCE.filter, np);
	}
	
	public static int totalPages(){
		checkInstance();
		return INSTANCE.totalPages;
	}
	
	public static Filter filter(){
		checkInstance();
		return INSTANCE.filter;
	}
	
	public static List<Article> articles(){
		checkInstance();
		return INSTANCE.articles;
	}
	
	public static void newSearch(Filter f){
		checkInstance();
		if(f.equals(INSTANCE.filter)){
			return;
		}
		reset(INSTANCE, f);
	}
	
	public static Article article(int ix){
		checkInstance();
		if(ix < INSTANCE.articles.size() && ix>-1){
			return INSTANCE.articles.get(ix);
		}
		return null;
	}
	
	public static void flush(){
		INSTANCE=null;
	}
	
	///////////////////////////////////////////////////////////
	

	private ArticleMemoryController(){
		articles = new ArrayList<Article>();
		filter = new Filter("", "", -1, Committee.NULL);
		reset(this, filter);
	}
	
	private static void reset(ArticleMemoryController inst, Filter f){
		inst.filter = f;
		inst.articles.clear();
		inst.lastPage = 0;
		inst.totalPages = -1;
	}
	
	private static void checkInstance(){
		if(INSTANCE==null){
			INSTANCE=new ArticleMemoryController();
		}
	}
}
