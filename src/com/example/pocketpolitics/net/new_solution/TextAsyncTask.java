package com.example.pocketpolitics.net.new_solution;

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

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

/**
 * AsyncTask that retrieves short text of an Article from the Riksdag in the background. 
 * 
 * This class should be extended in an activity:
 * private class ATask extends TextAsyncTask ...
 * 
 * Use like this:
 * new ATask().execute(String year, String artikelId)
 * 
 * Update UI using the abstract methods: onPreExecute, onCancelled and onPostExecute
 * 
 * @author Leif
 *
 */
class TextAsyncTask extends AsyncTask<String, Integer, String> {

	//http://data.riksdagen.se/dokumentlista/?rm=2013&d=&ts=&sn=&parti=&iid=&bet=Sku21&org=&kat=&sz=10&sort=c&utformat=json&termlista=
	private static final String QUERY_STR_1 = "http://data.riksdagen.se/dokumentlista/?rm=";
	private static final String QUERY_STR_2 = "&d=&ts=&sn=&parti=&iid=&bet=";
	private static final String QUERY_STR_3 = "&org=&kat=&sz=10&sort=c&utformat=json&termlista=";
	//private static final String QUERY_STR_3 = "Sku21&org=&kat=&sz=10&sort=c&utformat=xml&termlista=";

	private static final String TEXT_XPATH = "/dokumentstatus/dokuppgift/uppgift[namn='Beslut i korthet']/text";

	private TextViewInterface view;
	private String year;
	private String dokid;

	TextAsyncTask(TextViewInterface tview, String year, String dokid){
		this.view = tview;
		this.year = year;
		this.dokid = dokid;
	}

	@Override
	protected String doInBackground(String... arg0) {
		return getText();
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

	/**
	 * 
	 * @param year 2013
	 * @param articleid Sku21
	 * @return
	 */
	private String getText(/*String year, String articleid*/){

		String docUrl = getDocUrl(year, dokid);
		//Log.i(this.getClass().getSimpleName(), "Leif: parsed this URL from json: "+docUrl);

		if(docUrl!=null){
			String text = getTextFromXml(docUrl);
			//Log.i(this.getClass().getSimpleName(), "Leif: parsed this Text form XML: "+text);

			return text;
		}
		
		return null;
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

		return null;
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
