package com.example.pocketpolitics.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import com.google.gson.Gson;

class TextRetriever {
	
	private static final String QUERY_STR_1 = "http://data.riksdagen.se/dokumentlista/?rm=";
	private static final String QUERY_STR_2 = "&d=&ts=&sn=&parti=&iid=&bet=";
	private static final String QUERY_STR_3 = "&org=&kat=&sz=10&sort=c&utformat=json&termlista=";
	//private static final String QUERY_STR_3 = "Sku21&org=&kat=&sz=10&sort=c&utformat=xml&termlista=";

	protected TextRetriever(){
		
	}
	
	/**
	 * 
	 * @param year 2013
	 * @param articleid Sku21
	 * @return
	 */
	public String getText(String year, String articleid){
		
		DocumentList docl = getDocList(year, articleid);
		
		return "";
	}
	
	private DocumentList getDocList(String year, String artid){
		String query = QUERY_STR_1 + year + QUERY_STR_2 + artid + QUERY_STR_3;
		InputStream source = retrieveStream(query);
		if(source == null)
			return null;
		
		Gson gson = new Gson();
		Reader reader = new InputStreamReader(source);
		
		DocumentList response = gson.fromJson(reader, DocumentList.class);
		
		return response;
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
