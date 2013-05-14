package org.group13.pocketpolitics.control;

import java.util.ArrayList;
import java.util.List;

import org.group13.pocketpolitics.model.Article;
import org.group13.pocketpolitics.net.Filter;
import org.group13.pocketpolitics.net.QueryResult;

public class ArticleMemoryController {
	private static ArticleMemoryController INSTANCE;
	
	private List<Article> articles;
	private Filter filter;
	private int lastPage;
	
	public static int retrievedPage(QueryResult result){
		checkInstance();
		return ++INSTANCE.lastPage;
	}
	
	public static List<Article> articles(){
		checkInstance();
		return INSTANCE.articles;
	}
	
	public static void newSearch(Filter f){
		checkInstance();
		INSTANCE.filter = f;
		INSTANCE.articles.clear();
		INSTANCE.lastPage = 0;
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
		filter = null;
	}
	
	private static void checkInstance(){
		if(INSTANCE==null){
			INSTANCE=new ArticleMemoryController();
		}
	}
}
