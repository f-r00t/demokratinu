package com.example.pocketpolitics.net;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

import com.example.pocketpolitics.model.Article;

public class ArticlesAsyncTask extends AsyncTask<QueryParam, Integer, String>{
	private static final String QUERY = "http://data.riksdagen.se/sok/?doktyp=bet&avd=dokument&sort=datum&utformat=&a=s&datum=2012-11-11&tom=2013-01-01&p=1&sz=3";
	
	private static int ARTICLES_PER_PAGE = 15;
	// URL = QUERY + "&datum=" + afterthis + "&tom=" + beforethis"
	
	private ArtActivityInterface acti;
	
	@Override
	protected String doInBackground(QueryParam... arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	protected void onPreExecute(){

	}

	@Override
	protected void onProgressUpdate(Integer... progress){

	}

	@Override
	protected void onPostExecute(String result){
	}

	@Override
	protected void onCancelled(String result){

	}
	
	private List<Article> createArticles(QueryParam qpar){
		
		String url = QUERY + "&datum=";
		InputStream instr = retrieveStream(url);
		
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			request.abort();
			Log.w(this.getClass().getSimpleName(), "Error for URL "+url, e);
			e.printStackTrace();
		}

		return null;
	}
}
