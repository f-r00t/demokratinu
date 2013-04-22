package com.example.pocketpolitics.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.InputSource;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

class TextRetriever /*extends AsyncTask<String, Integer, String>*/{
	
	//http://data.riksdagen.se/dokumentlista/?rm=2013&d=&ts=&sn=&parti=&iid=&bet=Sku21&org=&kat=&sz=10&sort=c&utformat=json&termlista=
	private static final String QUERY_STR_1 = "http://data.riksdagen.se/dokumentlista/?rm=";
	private static final String QUERY_STR_2 = "&d=&ts=&sn=&parti=&iid=&bet=";
	private static final String QUERY_STR_3 = "&org=&kat=&sz=10&sort=c&utformat=json&termlista=";
	//private static final String QUERY_STR_3 = "Sku21&org=&kat=&sz=10&sort=c&utformat=xml&termlista=";
	
	private static final String TEXT_XPATH = "/dokumentstatus/dokuppgift/uppgift[namn='Beslut i korthet']/text";
	
	
	private static String result;

	protected TextRetriever(){
		
	}
	
	
	public static String getResult(){
		return result;
	}
	
	/**
	 * 
	 * @param year 2013
	 * @param articleid Sku21
	 * @return
	 */
	public String getText(String year, String articleid){
		
		String docUrl = getDocUrl(year, articleid);
		//Log.i(this.getClass().getSimpleName(), "Leif: parsed this URL from json: "+docUrl);
		
		String text = getTextFromXml(docUrl); 
		//Log.i(this.getClass().getSimpleName(), "Leif: parsed this Text form XML: "+text);
		
		return text;
	}
	
	@SuppressLint("NewApi")
	private String getTextFromXml(String xmlUrl){
		InputStream instream = retrieveStream(xmlUrl);
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		InputSource isource = new InputSource(instream);
		
		try {
			String result = xpath.evaluate(TEXT_XPATH, isource);
			result = result.replaceAll("&nbsp;", " ");
			
			//Log.i(this.getClass().getSimpleName(), "Leif: Xpath evaluated: "+result);
			return result;
		} catch (XPathExpressionException e) {
			Log.e(this.getClass().getSimpleName(),"Leif: Error: XPath failure in getTextFromXml");
			e.printStackTrace();
		}
		
		return "";
	}
	
	private String getDocUrl(String year, String artid){
		String query = QUERY_STR_1 + year + QUERY_STR_2 + artid + QUERY_STR_3;
		InputStream source = retrieveStream(query);
		if(source == null){
			Log.e(this.getClass().getSimpleName(), "Leif: Error, InputStream failed!");
			return null;
		}
		
		InputStreamReader reader = new InputStreamReader(source);
		
		JsonParser jpars = new JsonParser();
		JsonElement result = jpars.parse(reader)
				.getAsJsonObject().get("dokumentlista")
				.getAsJsonObject().get("dokument")
				.getAsJsonObject().get("dokumentstatus_url_xml");
		
		//Log.i(this.getClass().getSimpleName(), "Leif: jsonElement toString: "+result.toString());
		
		return result.getAsString();
		
		/*
		JsonReader jread = new JsonReader(reader);
		try {
			
			String ret = null;
			
			jread.beginObject();
			while(jread.hasNext()){
				String instr = jread.nextName();
				if(instr.equals(KEY_DOK)){
					ret = jread.nextString();
				}
				else{
					jread.skipValue();
				}
			}
			jread.endObject();
			
			if(ret == null){
				Log.w(this.getClass().getSimpleName(), "Leif: Warning: Key "+KEY_DOK+" not found in json");
			}
			else{
				return ret;
			}
			
		} catch (IOException e1) {
			Log.e(this.getClass().getSimpleName(), "Leif: Error: io exception reading json: "+e1.getMessage());
			e1.printStackTrace();
		}*/
		
		/*
		Gson gson = new Gson();
		DocumentList response = gson.fromJson(reader, DocumentList.class);
		*/
		
		/*
		String key = "dokument";
		
		try {
			JSONObject json = new JSONObject(source.toString());
			if(json.isNull(key)){
				Log.w(this.getClass().getSimpleName(), "Leif: Key " +key+ " not found in json!");
				return null;
			}
			else{
				Log.i(this.getClass().getSimpleName(), "Leif: Key " +key+ " is found in json");
				return json.getString(key);
			}
			
		} catch (JSONException e) {
			
			Log.e(this.getClass().getSimpleName(), "Leif: Error: json exception: "+e.getMessage());
			e.printStackTrace();
		}*/
		
		//return null;
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
