package org.group13.pocketpolitics.control;

import java.util.ArrayList;
import java.util.List;

import org.group13.pocketpolitics.model.Article;
import org.group13.pocketpolitics.net.Filter;
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
		INSTANCE.articles.addAll(result.getArts());
		if(result.getThisPage() != ++INSTANCE.lastPage){
			Log.w(ArticleMemoryController.class.getSimpleName(), "PocketDebug: wrong papge count! internally: "+INSTANCE.lastPage+", riksdag count: "+result.getThisPage());
			return -1;
		}
		return result.getThisPage();
	}
	
	public static int totalPages(){
		checkInstance();
		return INSTANCE.totalPages;
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
	
	private ArticleMemoryController(){
		articles = new ArrayList<Article>();
		lastPage=0;
		totalPages = -1;
		filter = null;
	}
	
	private static void checkInstance(){
		if(INSTANCE==null){
			INSTANCE=new ArticleMemoryController();
		}
	}
}
