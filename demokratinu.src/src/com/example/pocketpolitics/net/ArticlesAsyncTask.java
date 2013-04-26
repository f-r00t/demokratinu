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
			List parsedXml = parseXml(instr);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Article> arts = new ArrayList();

		return null;
	}
	
	private List parseXml(InputStream instr) throws XmlPullParserException, IOException{
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
	
	private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException{
		List traffar = new ArrayList();
		
		parser.require(XmlPullParser.START_TAG, XmlPullParser.NO_NAMESPACE, "sok");
		while(parser.next() != XmlPullParser.END_TAG){
			if(parser.getEventType() != XmlPullParser.START_TAG){
				continue;
			}
			String name = parser.getName();
			if (name.equals("traff")){
				traffar.add(readEntry(parser));
			}
			else{
				skip(parser);
			}
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

}
