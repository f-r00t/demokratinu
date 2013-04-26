package com.example.pocketpolitics.net;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.AsyncTask;
import android.util.Log;

import com.example.pocketpolitics.model.Article;

public class ArticlesAsyncTask extends AsyncTask<QueryParam, Integer, QueryResult>{
	//private static final String QUERY = "http://data.riksdagen.se/sok/?doktyp=bet&avd=dokument&sort=datum&utformat=&a=s&datum=2012-11-11&tom=2013-01-01&p=1&sz=3";
	private static final String QUERY = "http://data.riksdagen.se/sok/?doktyp=bet&avd=dokument&sort=datum&utformat=&a=s"; //"&datum=2012-11-11&tom=2013-01-01&p=1&sz=3";
	private static final String xmlns = null;
	

	private static int ARTICLES_PER_PAGE = 15;

	private ArtActivityInterface acti;


	@Override
	protected QueryResult doInBackground(QueryParam... params) {
		return createArticles(params[0]);
	}

	@Override
	protected void onPreExecute(){

	}

	@Override
	protected void onPostExecute(QueryResult qres){

	}

	@Override
	protected void onCancelled(QueryResult qres){

	}

	private QueryResult createArticles(QueryParam qpar){

		String url = QUERY + "&datum=" + qpar.dateFrom + "&tom=" + qpar.dateTo + "&p=" + qpar.page + "&sz=" + ARTICLES_PER_PAGE;
		InputStream instr = retrieveStream(url);

		try {
			QueryResult parsedXml = parseXml(instr);
			
			return parsedXml;
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private InputStream retrieveStream(String url){

		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);

		HttpResponse response;
		try {
			response = client.execute(request);
			final int statusCode = response.getStatusLine().getStatusCode();

			if(statusCode != HttpStatus.SC_OK){
				Log.w(this.getClass().getSimpleName(), "Error "+statusCode+" for URL "+url);
				return null;
			}
			HttpEntity responseEntity = response.getEntity();
			return responseEntity.getContent();
		} catch (ClientProtocolException e) {
			Log.e(this.getClass().getSimpleName(), "Error ClientProtocolException for URL "+url, e);
			e.printStackTrace();
		} catch (IOException e) {
			request.abort();
			Log.e(this.getClass().getSimpleName(), "Error IOException for URL "+url, e);
			e.printStackTrace();
		}

		return null;
	}

	private QueryResult parseXml(InputStream instr) throws XmlPullParserException, IOException{
		try{
			XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(instr, null);
			parser.nextTag();
			return readFeed(parser);
		}
		finally{
			instr.close();
		}

	}

	private QueryResult readFeed(XmlPullParser parser) throws XmlPullParserException, IOException{
		List<Article> traffarList = new ArrayList();
		
		parser.require(XmlPullParser.START_TAG, xmlns, "sok");
		int thisPage = Integer.parseInt(parser.getAttributeValue(xmlns, "sida"));
		int totalPages = Integer.parseInt(parser.getAttributeValue(xmlns, "sidor"));
		int totalTraffar = Integer.parseInt(parser.getAttributeValue(xmlns, "traffar"));
		
		while(parser.next() != XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			if (name.equals("traff")){
				traffarList.add(readTraff(parser));
			}
			else{
				skip(parser);
			}
		}
		
		QueryResult qres = new QueryResult(traffarList, totalPages, thisPage, totalTraffar);

		return null;
	}
	
	private Article readTraff(XmlPullParser parser) throws XmlPullParserException, IOException{
		parser.require(XmlPullParser.START_TAG, xmlns, "Traff");
		
		
		Article art = new Article();
		
		while(parser.next()!= XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			
			if(name.equals("traffnummer")){ 
				
			} else if(name.equals("datum")){ 
				art.setDatum(readString(parser, "datum"));
			} else if(name.equals("id")){ 
				art.setId(readString(parser, "id"));
			} else if(name.equals("titel")){ 
				art.setTitle(readString(parser, "titel"));
			} else if(name.equals("rm")){ 
				art.setRm(readString(parser, "rm"));
			} else if(name.equals("relaterat_id")){ 
				art.setRelaterat_id(readString(parser, "relaterat_id"));
			} else if(name.equals("beteckning")){ 
				art.setDokid(readString(parser, "beteckning"));
			} else if(name.equals("score")){ 
				
			} else if(name.equals("notisrubrik")){ 
				art.setNotisrubrik(readString(parser, "notisrubrik"));
			} else if(name.equals("notis")){ 
				art.setNotis(readString(parser, "notis"));
			} else if(name.equals("beslutsdag")){ 
				art.setBeslutsdag(readString(parser, "beslutsdag"));
			} else if(name.equals("beslutad")){
				
			} else {
				
			}
		}
		
		return art;
	}
	
	private String readString(XmlPullParser parser, String tag) throws XmlPullParserException, IOException{
		parser.require(XmlPullParser.START_TAG, xmlns, tag);
		String result="";
		if(parser.next() == XmlPullParser.TEXT){
			result = parser.getText();
			parser.nextTag();
		}
		else{
			throw new IllegalStateException();
		}
		parser.require(XmlPullParser.END_TAG, xmlns, tag);
		return result;
	}
	private int readInt(XmlPullParser parser, String tag) throws XmlPullParserException, IOException{
		parser.require(XmlPullParser.START_TAG, xmlns, tag);
		return -1;
	}
	private double readDouble(XmlPullParser parser, String tag) throws XmlPullParserException, IOException{
		parser.require(XmlPullParser.START_TAG, xmlns, tag);
		return -1;
	}
	
	private void skip(XmlPullParser parser) throws XmlPullParserException, IOException{
		if(parser.getEventType() != XmlPullParser.START_TAG){
			throw new IllegalStateException();
		}
		int depth = 1;
		while(depth != 0){
			switch(parser.next()){
			case XmlPullParser.END_TAG:
				depth--;
				break;
			case XmlPullParser.START_TAG:
				depth++;
				break;
			}
		}
	}


}
