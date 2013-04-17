package com.test.pocketpolitics.control;

import android.test.AndroidTestCase;

import com.example.pocketpolitics.control.ArticleRetriever;


public class ArticleRetrieverTester extends AndroidTestCase {
	
	public void testAnything() throws Throwable {
		System.out.println("I'm running, can't you see?");
		assertTrue(1+1==2);
	}
	
	public void testSingleton() throws Throwable {
		ArticleRetriever art = ArticleRetriever.getInstance();
		assertTrue(art!=null);
	}
	
	public void testPrint() throws Throwable {
		assertTrue(ArticleRetriever.getInstance().printFeed());
	}
	
	
}
