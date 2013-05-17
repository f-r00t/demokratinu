package org.group13.pocketpolitics.net;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.group13.pocketpolitics.model.riksdag.Agenda;

import android.os.AsyncTask;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * @deprecated
 * @author Leif
 *
 */
class FeedTitlesAsyncTask extends AsyncTask<String, Integer, List<Agenda>> {

	private static final String FEED_URL = "http://www.riksdagen.se/sv/Debatter--beslut/?rss=true&type=biksmall";

	private ArtActivityInterface act;

	FeedTitlesAsyncTask(ArtActivityInterface act){
		super();

		this.act = act;
	}

	@Override
	protected List<Agenda> doInBackground(String... strings){

		List<String> titles = getArticleTitles();
		List<Agenda> arts = new ArrayList<Agenda>();

		Iterator<String> iter = titles.listIterator();
		while(iter.hasNext() && !this.isCancelled()){
			Agenda a = new Agenda();
			String total= iter.next();
			
			String[] title = total.split("\\(");
			a.setTitle(title[0]);
			a.setBeteckning((title[1].split("\\)")[0]));
			arts.add(a);
		}

		return arts;
	}

	@Override
	protected void onPreExecute(){

	}

	@Override
	protected void onPostExecute(List<Agenda> result){
		// eller bara returnera string? Vill gärna lägga in länkar i Article från Dokumentlista.xml...
		act.addArticles(result);
	}

	@Override
	protected void onCancelled(List<Agenda> result){

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
