package com.example.pocketpolitics.net.new_solution;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.os.AsyncTask;

import com.example.pocketpolitics.model.Article;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

class FeedTitlesAsyncTask extends AsyncTask<String, Integer, List<Article>> {

	private static final String FEED_URL = "http://www.riksdagen.se/sv/Debatter--beslut/?rss=true&type=biksmall";

	private ArtActivityInterface act;

	FeedTitlesAsyncTask(ArtActivityInterface act){
		super();

		this.act = act;
	}

	@Override
	protected List<Article> doInBackground(String... strings){

		List<String> titles = getArticleTitles();
		List<Article> arts = new ArrayList<Article>();

		Iterator<String> iter = titles.listIterator();
		while(iter.hasNext() && !this.isCancelled()){
			Article a = new Article();
			a.setTitle(iter.next());
			arts.add(a);
		}

		return arts;
	}

	@Override
	protected void onPreExecute(){

	}

	@Override
	protected void onPostExecute(List<Article> result){
		// eller bara returnera string? Vill g�rna l�gga in l�nkar i Article fr�n Dokumentlista.xml...
		act.addArticles(result);
	}

	@Override
	protected void onCancelled(List<Article> result){

	}

	/**
	 * 
	 * @return list of feed titles
	 */
	private List<String> getArticleTitles(){
		SyndFeed feed = null;
		List<SyndEntry> entries = null;

		try {

			URL feedSource = new URL(FEED_URL);
			SyndFeedInput input = new SyndFeedInput();
			if(feedSource!=null)
				feed = input.build(new XmlReader(feedSource));

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FeedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(feed!=null){
			entries = feed.getEntries();
		}
		else{
			return null;
		}

		List<String> ls = new ArrayList<String>();
		for (Iterator<SyndEntry> i = feed.getEntries().iterator(); i.hasNext();){
			SyndEntry entry = i.next();
			ls.add( entry.getTitle());
		}

		return ls;
	}

}
