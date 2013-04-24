package com.test.pocketpolitics.net.new_solution;

import java.util.List;

import android.util.Log;

import com.example.pocketpolitics.model.Article;
import com.example.pocketpolitics.net.new_solution.ArtActivityInterface;
import com.example.pocketpolitics.net.new_solution.Retriever;
import com.example.pocketpolitics.net.new_solution.TextViewInterface;

public class NewRetrieverTester implements TextViewInterface, ArtActivityInterface {

	public void testTextAsyncTask(){
		Retriever.getInstance().retrieveRssArticleTitles(this);
	}

	@Override
	public void addArticles(List<Article> arts) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setText(String text) {
		Log.i(this.getClass().getSimpleName(), "Leif testing TextAsyncTask:\n"+text);
		
	}

	
}
