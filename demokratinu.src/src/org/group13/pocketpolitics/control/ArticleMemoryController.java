package org.group13.pocketpolitics.control;

import java.util.ArrayList;
import java.util.List;

import org.group13.pocketpolitics.model.Article;
import org.group13.pocketpolitics.model.Committee;
import org.group13.pocketpolitics.net.Filter;
import org.group13.pocketpolitics.net.QueryParam;
import org.group13.pocketpolitics.net.QueryResult;

import android.util.Log;

public class ArticleMemoryController {
	private static ArticleMemoryController INSTANCE;
	
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
		INSTANCE.articles.addAll(result.getArts());
		return result.getThisPage();
	}
	
	public static int nextPage(){
		checkInstance();
		if(INSTANCE.lastPage==INSTANCE.totalPages){
			return -1;
		}
		return INSTANCE.lastPage+1;
	}
	
	public static QueryParam nextQuery(){
		checkInstance();
		int np = nextPage();
		if(np==-1){
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
		INSTANCE.filter = f;
		INSTANCE.articles.clear();
		INSTANCE.lastPage = 0;
		INSTANCE.totalPages = -1;
	}
	
	public static Article article(int ix){
		checkInstance();
		if(ix < INSTANCE.articles.size() && ix>-1){
			return INSTANCE.articles.get(ix);
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////
	
	private ArticleMemoryController(){
		articles = new ArrayList<Article>();
		lastPage=0;
		totalPages = -1;
		filter = new Filter("", "", -1, Committee.NULL);
	}
	
	private static void checkInstance(){
		if(INSTANCE==null){
			INSTANCE=new ArticleMemoryController();
		}
	}
}
