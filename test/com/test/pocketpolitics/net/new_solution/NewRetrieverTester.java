package com.test.pocketpolitics.net.new_solution;

import java.util.List;
import java.util.ListIterator;

import android.util.Log;

import com.example.pocketpolitics.model.Article;
import com.example.pocketpolitics.net.new_solution.ArtActivityInterface;
import com.example.pocketpolitics.net.new_solution.Retriever;
import com.example.pocketpolitics.net.new_solution.TextViewInterface;

public class NewRetrieverTester implements TextViewInterface, ArtActivityInterface {
	
	private Retriever retr;
	
	public NewRetrieverTester(){
		retr = Retriever.getInstance();
	}

	public void testTextAsyncTask(){
		retr.retrieveRssArticleTitles(this);
	}

	@Override
	public void addArticles(List<Article> arts) {
		ListIterator<Article> it = arts.listIterator();
		int is = 0;
		while(it.hasNext() && is++<5){
			Article a = it.next();
			retr.retrieveText(this, "2013", a.getDokid());
			Log.i(this.getClass().getSimpleName(), "Leif testing TextAsyncTask.\ntitle: "+a.getTitle()+"\ndokid:"+a.getDokid());
		}
		
	}
	
	@Override
	public void setText(String text) {
		Log.i(this.getClass().getSimpleName(), "Leif testing TextAsyncTask:\n"+text);
		
	}

	
}
